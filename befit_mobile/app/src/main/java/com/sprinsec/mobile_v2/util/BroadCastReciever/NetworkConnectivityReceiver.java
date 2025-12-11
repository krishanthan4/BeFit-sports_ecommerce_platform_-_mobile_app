package com.sprinsec.mobile_v2.util.BroadCastReciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sprinsec.mobile_v2.interfaces.ConnectivityListener;


public class NetworkConnectivityReceiver extends BroadcastReceiver {

    private ConnectivityListener connectivityListener;

    public NetworkConnectivityReceiver(ConnectivityListener listener) {
        this.connectivityListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (connectivityListener != null) {
            boolean isConnected = isNetworkConnected(context);
            connectivityListener.onNetworkConnectionChanged(isConnected);
        }
    }

    private boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}