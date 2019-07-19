package net.coding4ever.roedhi.moviecatalogueapi.activities;

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

import net.coding4ever.roedhi.moviecatalogueapi.R;
import net.coding4ever.roedhi.moviecatalogueapi.adapter.SectionsPagerAdapter;
import net.coding4ever.roedhi.moviecatalogueapi.helpers.LocaleManager;
import net.coding4ever.roedhi.moviecatalogueapi.listeners.ProgressBarCallback;

public class MainActivity extends AppCompatActivity
        implements ProgressBarCallback {

    private LocaleManager localeManager;
    private static final int REQUEST_CODE = 100;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localeManager = new LocaleManager(this);
        localeManager.setLocale(this);

        progressBar = findViewById(R.id.progressBar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings){
            Intent intent = new Intent(this, LanguageSettingsActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
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