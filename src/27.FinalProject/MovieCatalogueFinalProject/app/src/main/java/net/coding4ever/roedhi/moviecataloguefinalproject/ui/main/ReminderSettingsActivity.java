package net.coding4ever.roedhi.moviecataloguefinalproject.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import net.coding4ever.roedhi.moviecataloguefinalproject.R;
import net.coding4ever.roedhi.moviecataloguefinalproject.helpers.LocaleManager;
import net.coding4ever.roedhi.moviecataloguefinalproject.helpers.PreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReminderSettingsActivity extends AppCompatActivity {

    @BindView(R.id.chk_daily_reminder) CheckBox chkDailyReminder;
    @BindView(R.id.chk_release_today_reminder) CheckBox chkReleaseTodayReminder;
    @BindView(R.id.btn_simpan) Button btnSave;

    public static String EXTRA_DAILY_REMINDER_VALUE = "extra_daily_reminder_value";
    public static String EXTRA_RELEASE_TODAY_REMINDER_VALUE = "extra_release_today_reminder_value";
    public static int RESULT_CODE = 110;
    private LocaleManager localeManager;
    private PreferencesManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_settings);

        ButterKnife.bind(this);

        prefManager = new PreferencesManager(this);
        chkDailyReminder.setChecked(Boolean.valueOf(prefManager.getValue(EXTRA_DAILY_REMINDER_VALUE, "false")));
        chkReleaseTodayReminder.setChecked(Boolean.valueOf(prefManager.getValue(EXTRA_RELEASE_TODAY_REMINDER_VALUE, "false")));

        localeManager = new LocaleManager(this);
        localeManager.setLocale(this);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getResources().getString(R.string.remainder_settings));

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                prefManager.setValue(EXTRA_DAILY_REMINDER_VALUE, String.valueOf(chkDailyReminder.isChecked()));
                prefManager.setValue(EXTRA_RELEASE_TODAY_REMINDER_VALUE, String.valueOf(chkReleaseTodayReminder.isChecked()));

                Intent resultIntent = new Intent();
                resultIntent.putExtra(EXTRA_DAILY_REMINDER_VALUE, chkDailyReminder.isChecked());
                resultIntent.putExtra(EXTRA_RELEASE_TODAY_REMINDER_VALUE, chkReleaseTodayReminder.isChecked());

                setResult(RESULT_CODE, resultIntent);
                finish();

            }

        });
    }
}
