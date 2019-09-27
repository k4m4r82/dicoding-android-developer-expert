package net.coding4ever.roedhi.moviecataloguefinalproject.ui.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import net.coding4ever.roedhi.moviecataloguefinalproject.R;
import net.coding4ever.roedhi.moviecataloguefinalproject.helpers.LocaleManager;
import net.coding4ever.roedhi.moviecataloguefinalproject.listeners.ProgressBarCallback;
import net.coding4ever.roedhi.moviecataloguefinalproject.listeners.SearchCallback;
import net.coding4ever.roedhi.moviecataloguefinalproject.ui.favorite.FavoriteActivity;
import net.coding4ever.roedhi.moviecataloguefinalproject.ui.movie.MovieFragment;
import net.coding4ever.roedhi.moviecataloguefinalproject.ui.reminder.DailyReminder;
import net.coding4ever.roedhi.moviecataloguefinalproject.ui.reminder.ReleaseTodayReminder;
import net.coding4ever.roedhi.moviecataloguefinalproject.ui.tvshow.TvShowFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ProgressBarCallback {

    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.tabs) TabLayout tabs;

    private LocaleManager localeManager;
    private static final int REQUEST_CODE_LANGUAGE_SETTING = 100;
    private static final int REQUEST_CODE_REMAINDER_SETTING = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        localeManager = new LocaleManager(this);
        localeManager.setLocale(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        MainPagerAdapter sectionsPagerAdapter = new MainPagerAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(sectionsPagerAdapter);

        tabs.setupWithViewPager(viewPager);
    }

    private Object getCurrentFragment() {
        return viewPager
                .getAdapter()
                .instantiateItem(viewPager, viewPager.getCurrentItem());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {

            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    SearchCallback searchCallback = viewPager.getCurrentItem() == 0 ?
                            (MovieFragment)getCurrentFragment() : (TvShowFragment)getCurrentFragment();

                    searchCallback.searchMovie(query);

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    if (query.length() == 0) {
                        SearchCallback searchCallback = viewPager.getCurrentItem() == 0 ?
                                (MovieFragment)getCurrentFragment() : (TvShowFragment)getCurrentFragment();

                        searchCallback.refreshMovie();
                    }

                    return false;
                }
            });
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {
            case R.id.action_change_settings:
                intent = new Intent(this, LanguageSettingsActivity.class);
                startActivityForResult(intent, REQUEST_CODE_LANGUAGE_SETTING);
                break;

            case R.id.action_list_favorite:
                intent = new Intent(this, FavoriteActivity.class);
                startActivity(intent);
                break;

            case R.id.action_reminder_settings:
                intent = new Intent(this, ReminderSettingsActivity.class);
                startActivityForResult(intent, REQUEST_CODE_REMAINDER_SETTING);

                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_LANGUAGE_SETTING:
                if (resultCode == LanguageSettingsActivity.RESULT_CODE) { recreate(); }

                break;

            case REQUEST_CODE_REMAINDER_SETTING:
                if (resultCode == ReminderSettingsActivity.RESULT_CODE) {

                    boolean isDailyReminder = data.getBooleanExtra(
                            ReminderSettingsActivity.EXTRA_DAILY_REMINDER_VALUE, false);

                    boolean isReleaseTodayReminder = data.getBooleanExtra(
                            ReminderSettingsActivity.EXTRA_RELEASE_TODAY_REMINDER_VALUE, false);

                    DailyReminder dailyReminder = new DailyReminder();

                    if (isDailyReminder) {
                        String message = this.getString(R.string.msg_daily_reminder);

                        dailyReminder.setReminder(this, message);
                    } else {
                        dailyReminder.cancelReminder(this);
                    }

                    ReleaseTodayReminder releaseTodayReminder = new ReleaseTodayReminder();

                    if (isReleaseTodayReminder) {
                        releaseTodayReminder.setReminder(this);
                    } else {
                        releaseTodayReminder.cancelReminder(this);
                    }
                }

                break;

            default:
                break;
        }
    }

    @Override
    public void showLoading(Boolean state) {
        progressBar.setVisibility(state ? View.VISIBLE : View.GONE);
    }
}