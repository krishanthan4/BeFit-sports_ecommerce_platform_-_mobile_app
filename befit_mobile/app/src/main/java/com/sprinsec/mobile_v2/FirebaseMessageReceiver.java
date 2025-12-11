package com.sprinsec.mobile_v2;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sprinsec.mobile_v2.R;
import com.sprinsec.mobile_v2.ui.fragments.user.user_home_fragment;
import com.sprinsec.mobile_v2.ui.user.user_home_interface;

public class FirebaseMessageReceiver
        extends FirebaseMessagingService {

    // Override onNewToken to get new token
    @Override
    public void onNewToken(@NonNull String token)
    {
        Log.i("befit_logs", "Refreshed token: " + token);
    }
    // Override onMessageReceived() method to extract the
    // title and
    // body from the message passed in FCM
    @Override
    public void
    onMessageReceived(RemoteMessage remoteMessage)
    {
        // First case when notifications are received via
        // data event
        // Here, 'title' and 'message' are the assumed names
        // of JSON
        // attributes. Since here we do not have any data
        // payload, This section is commented out. It is
        // here only for reference purposes.
        /*if(remoteMessage.getData().size()>0){
            showNotification(remoteMessage.getData().get("title"),
                          remoteMessage.getData().get("message"));
        }*/

        // Second case when notification payload is
        // received.
        if (remoteMessage.getNotification() != null) {
            // Since the notification is received directly
            // from FCM, the title and the body can be
            // fetched directly as below.
            showNotification(
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody());
        }
    }

    // Method to display the notifications
    public void showNotification(String title,
                                 String message)
    {
        // Pass the intent to switch to the MainActivity
        Intent intent
                = new Intent(this, user_home_interface.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent
                = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);


        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(
                    "C1",
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );

            notificationManager.createNotificationChannel(notificationChannel);

        }
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new NotificationCompat.Builder(getApplicationContext(), "C1")
                    .setSmallIcon(R.drawable.new_message_icon)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_logo))
                    .setStyle(
                            new NotificationCompat.BigPictureStyle()
                                    .bigPicture(BitmapFactory.decodeResource(getResources(), getRandomImageResource()))
                    )
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .build();
        }

        notificationManager.notify(1, notification);
    }

    private int getRandomImageResource() {
        int[] images = {
                R.drawable.onboarding_image1,
                R.drawable.onboarding_image2,
                R.drawable.onboarding_image3,
                R.drawable.notification_big_image2
        };
        int randomIndex = new java.util.Random().nextInt(images.length);
        return images[randomIndex];
    }
}
