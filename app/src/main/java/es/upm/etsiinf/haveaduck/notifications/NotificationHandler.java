package es.upm.etsiinf.haveaduck.notifications;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import es.upm.etsiinf.haveaduck.R;

public class NotificationHandler {
    private static final String CHANNEL_ID = "daily_notification_channel";
    private static final String CHANNEL_NAME = "Daily Notification Channel";

    public static void createNotificationChannel(Context context) {

        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
        );
        NotificationManager manager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }

    public static Notification.Builder buildNotification(Context context, String title, String content, PendingIntent pending) {
        return new Notification.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.emojiduck)
                .setContentIntent(pending)
                .setAutoCancel(true);
    }
}

