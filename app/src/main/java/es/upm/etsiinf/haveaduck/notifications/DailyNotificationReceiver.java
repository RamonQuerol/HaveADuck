package es.upm.etsiinf.haveaduck.notifications;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DailyNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHandler.createNotificationChannel(context);

        String title = "Reminder";
        String content = "Don't forget to get a duck today! 🦆";

        //Notification.Builder notification = NotificationHandler.buildNotification(context, title, content);

        NotificationManager manager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        //manager.notify(1, notification.build());
    }
}
