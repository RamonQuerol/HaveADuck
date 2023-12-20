package es.upm.etsiinf.haveaduck;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Build;

public class Notifications {
    private static final String CHANNEL_ID = "daily_notification_channel";
    private static final String CHANNEL_NAME = "Daily Notification Channel";

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    public static Notification buildNotification(Context context, String title, String content) {
        return new Notification.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.emojiduck)
                .setAutoCancel(true)
                .build();
    }
}

