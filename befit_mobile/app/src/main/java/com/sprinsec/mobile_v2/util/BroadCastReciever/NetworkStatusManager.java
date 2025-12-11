package com.sprinsec.mobile_v2.util.BroadCastReciever;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.interfaces.ConnectivityListener;

public class NetworkStatusManager implements ConnectivityListener {

    private Activity activity;
    private FrameLayout networkStatusContainer;
    private TextView networkStatusText;
    private ImageView networkStatusIcon;
    private Handler handler;
    private Runnable hideRunnable;

    public NetworkStatusManager(Activity activity) {
        this.activity = activity;
        this.handler = new Handler(Looper.getMainLooper());
        this.hideRunnable = this::hideNetworkStatus;

        // Get the content view
        ViewGroup rootView = activity.findViewById(android.R.id.content);

        // Inflate network status overlay
        View overlay = activity.getLayoutInflater().inflate(
                R.layout.network_status_overlay, rootView, false);

        networkStatusContainer = overlay.findViewById(R.id.network_status_container);
        networkStatusText = overlay.findViewById(R.id.network_status_text);
        networkStatusIcon = overlay.findViewById(R.id.network_status_icon);

        // Add overlay to the root of the layout
        rootView.addView(overlay);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        handler.removeCallbacks(hideRunnable);

        if (isConnected) {
            hideNetworkStatus();
        } else {
            networkStatusText.setText("No internet connection. Waiting for connection...");
            networkStatusIcon.setImageResource(R.drawable.signal_disconnected);
//            networkStatusContainer.setBackgroundResource(R.color.colorDisconnected);

            // Show status until connected
            showNetworkStatus();
      new Thread(new Runnable() {
          @Override
          public void run() {
              try {
                  Thread.sleep(2000);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
              hideNetworkStatus();
          }
      }).start();
        }
    }

    private void showNetworkStatus() {
        if (networkStatusContainer.getVisibility() != View.VISIBLE) {
            Animation slideDown = AnimationUtils.loadAnimation(
                    activity, R.anim.network_slide_down);
            networkStatusContainer.startAnimation(slideDown);
            networkStatusContainer.setVisibility(View.VISIBLE);
        }
    }

    private void hideNetworkStatus() {
        if (networkStatusContainer.getVisibility() == View.VISIBLE) {
            Animation slideUp = AnimationUtils.loadAnimation(
                    activity, R.anim.slide_up);
            slideUp.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    networkStatusContainer.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            networkStatusContainer.startAnimation(slideUp);
        }
    }

    public void onDestroy() {
        handler.removeCallbacks(hideRunnable);
    }
}