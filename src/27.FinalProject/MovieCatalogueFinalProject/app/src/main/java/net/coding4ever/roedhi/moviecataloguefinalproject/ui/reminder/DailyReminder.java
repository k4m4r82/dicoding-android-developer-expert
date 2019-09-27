package net.coding4ever.roedhi.moviecataloguefinalproject.ui.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import net.coding4ever.roedhi.moviecataloguefinalproject.R;
import net.coding4ever.roedhi.moviecataloguefinalproject.helpers.AlarmNotificationHelper;

import java.util.Calendar;

public class DailyReminder extends BroadcastReceiver {

    public static final String EXTRA_MESSAGE = "message";
    private final int NOTIFY_ID = 101;
    private final int HOUR_OF_DAY = 7;

    @Override
    public void onReceive(Context context, Intent intent) {

        String message = intent.getStringExtra(EXTRA_MESSAGE);
        String title = context.getString(R.string.daily_reminder);

        AlarmNotificationHelper.showAlarmNotification(context, title, message, NOTIFY_ID);
    }

    public void setReminder(Context context, String message) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, DailyReminder.class);
        intent.putExtra(EXTRA_MESSAGE, message);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, HOUR_OF_DAY);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFY_ID, intent, 0);
        if (alarmManager != null) {

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        Toast.makeText(context, context.getString(R.string.daily_reminder_setup), Toast.LENGTH_SHORT).show();
    }

    public void cancelReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminder.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFY_ID, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }

        Toast.makeText(context, context.getString(R.string.daily_reminder_cancel), Toast.LENGTH_SHORT).show();
    }

}
