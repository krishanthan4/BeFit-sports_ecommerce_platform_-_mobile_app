package com.sprinsec.mobile_v2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sprinsec.mobile_v2.interfaces.ConnectivityListener;
import com.sprinsec.mobile_v2.util.BroadCastReciever.NetworkConnectivityReceiver;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class NetworkConnectivityReceiverTest {

    @Mock
    private Context mockContext;
    @Mock
    private ConnectivityListener mockListener;
    @Mock
    private ConnectivityManager mockConnectivityManager;
    @Mock
    private NetworkInfo mockNetworkInfo;

    private NetworkConnectivityReceiver receiver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        receiver = new NetworkConnectivityReceiver(mockListener);
        when(mockContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(mockConnectivityManager);
    }

    @Test
    public void testOnReceive_connected() {
        when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(mockNetworkInfo);
        when(mockNetworkInfo.isConnectedOrConnecting()).thenReturn(true);

        Intent intent = new Intent(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver.onReceive(mockContext, intent);

        verify(mockListener).onNetworkConnectionChanged(true);
    }

    @Test
    public void testOnReceive_disconnected() {
        when(mockConnectivityManager.getActiveNetworkInfo()).thenReturn(null);

        Intent intent = new Intent(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver.onReceive(mockContext, intent);

        verify(mockListener).onNetworkConnectionChanged(false);
    }
}