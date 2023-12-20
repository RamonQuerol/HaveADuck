package es.upm.etsiinf.haveaduck.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import es.upm.etsiinf.haveaduck.notifications.Notifications;

public class DailyNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Notifications.createNotificationChannel(context);

        String title = "Reminder";
        String content = "Don't forget to get a duck today! 🦆";

        Notification notification = Notifications.buildNotification(context, title, content);

        NotificationManager manager = context.getSystemService(NotificationManager.class);
        manager.notify(1, notification);
    }
}
