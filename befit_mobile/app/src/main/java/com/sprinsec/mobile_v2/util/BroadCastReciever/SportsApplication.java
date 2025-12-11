package com.sprinsec.mobile_v2.util.BroadCastReciever;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.sprinsec.mobile_v2.interfaces.ConnectivityListener;

import java.util.ArrayList;
import java.util.List;

public class SportsApplication extends Application {

    private NetworkConnectivityReceiver connectivityReceiver;
    private List<ConnectivityListener> listeners = new ArrayList<>();
    private boolean isNetworkConnected = true;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize and register network receiver
        connectivityReceiver = new NetworkConnectivityReceiver(isConnected -> {
            isNetworkConnected = isConnected;
            notifyListeners();
        });

        registerReceiver(connectivityReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public void addConnectivityListener(ConnectivityListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
            // Immediately notify new listener of current state
            listener.onNetworkConnectionChanged(isNetworkConnected);
        }
    }

    public void removeConnectivityListener(ConnectivityListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (ConnectivityListener listener : listeners) {
            listener.onNetworkConnectionChanged(isNetworkConnected);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(connectivityReceiver);
    }
}