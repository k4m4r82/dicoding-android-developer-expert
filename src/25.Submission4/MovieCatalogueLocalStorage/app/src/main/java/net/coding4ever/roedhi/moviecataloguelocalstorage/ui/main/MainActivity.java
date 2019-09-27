package net.coding4ever.roedhi.moviecataloguelocalstorage.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import net.coding4ever.roedhi.moviecataloguelocalstorage.R;
import net.coding4ever.roedhi.moviecataloguelocalstorage.helpers.LocaleManager;
import net.coding4ever.roedhi.moviecataloguelocalstorage.listeners.ProgressBarCallback;
import net.coding4ever.roedhi.moviecataloguelocalstorage.ui.favorite.FavoriteActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ProgressBarCallback {

    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.tabs) TabLayout tabs;

    private LocaleManager localeManager;
    private static final int REQUEST_CODE = 100;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        if (item.getItemId() == R.id.action_change_settings) {
            intent = new Intent(this, LanguageSettingsActivity.class);
            startActivityForResult(intent, REQUEST_CODE);

        } else if (item.getItemId() == R.id.action_list_favorite) {
            intent = new Intent(this, FavoriteActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == LanguageSettingsActivity.RESULT_CODE) {
                recreate();
            }
        }
    }

    @Override
    public void showLoading(Boolean state) {
        progressBar.setVisibility(state ? View.VISIBLE : View.GONE);
    }
}