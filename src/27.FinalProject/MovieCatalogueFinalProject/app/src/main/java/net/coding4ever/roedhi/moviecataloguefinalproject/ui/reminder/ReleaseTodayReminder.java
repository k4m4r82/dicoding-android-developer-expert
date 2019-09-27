package net.coding4ever.roedhi.moviecataloguefinalproject.ui.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import net.coding4ever.roedhi.moviecataloguefinalproject.BuildConfig;
import net.coding4ever.roedhi.moviecataloguefinalproject.R;
import net.coding4ever.roedhi.moviecataloguefinalproject.helpers.AlarmNotificationHelper;
import net.coding4ever.roedhi.moviecataloguefinalproject.listeners.LoadDataCallback;
import net.coding4ever.roedhi.moviecataloguefinalproject.models.Movie;
import net.coding4ever.roedhi.moviecataloguefinalproject.repositories.api.MovieServiceRepository;

import java.util.Calendar;
import java.util.List;

public class ReleaseTodayReminder extends BroadcastReceiver {

    private final int NOTIFY_ID = 102;
    private final int HOUR_OF_DAY = 8;

    @Override
    public void onReceive(Context context, Intent intent) {

        String title = context.getString(R.string.release_today_reminder);

        String apiKey = BuildConfig.TheMovieDBApiKey;
        String languageId = context.getResources().getString(R.string.language_id);

        String baseUrl = context.getResources().getString(R.string.the_moviedb_base_api);
        String imageUrl = context.getResources().getString(R.string.poster_image_url);

        MovieServiceRepository repository = new MovieServiceRepository(baseUrl, imageUrl, apiKey, languageId);

        repository.getReleaseToday(new LoadDataCallback<Movie>() {

            @Override
            public void onDataLoaded(List<Movie> list) {

                int no = 1;

                StringBuilder longMessage = new StringBuilder();
                for (Movie movie : list)
                {
                    longMessage.append(String.format("%s. %s\n", no, movie.getTitle()));
                    no++;
                }

                // short message
                String message = String.format("1. %s ...", list.get(0).getTitle());

                AlarmNotificationHelper.showAlarmNotification(context, title, message,
                        longMessage.toString(), NOTIFY_ID);
            }

            @Override
            public void onError(Throwable e) {

            }

        });

    }

    public void setReminder(Context context) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, ReleaseTodayReminder.class);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, HOUR_OF_DAY);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFY_ID, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        Toast.makeText(context, context.getString(R.string.release_today_reminder_setup), Toast.LENGTH_SHORT).show();
    }

    public void cancelReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseTodayReminder.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFY_ID, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }

        Toast.makeText(context, context.getString(R.string.release_today_reminder_cancel), Toast.LENGTH_SHORT).show();
    }
}
