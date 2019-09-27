package net.coding4ever.roedhi.moviecataloguelocalstorage.ui.favorite;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import net.coding4ever.roedhi.moviecataloguelocalstorage.R;
import net.coding4ever.roedhi.moviecataloguelocalstorage.helpers.LocaleManager;
import net.coding4ever.roedhi.moviecataloguelocalstorage.listeners.ProgressBarCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends AppCompatActivity implements ProgressBarCallback {

    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tabs) TabLayout tabs;

    private LocaleManager localeManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        ButterKnife.bind(this);

        localeManager = new LocaleManager(this);
        localeManager.setLocale(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getResources().getString(R.string.favorite_menu));

        FavoritePagerAdapter pagerAdapter = new FavoritePagerAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);

        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void showLoading(Boolean state) {
        progressBar.setVisibility(state ? View.VISIBLE : View.GONE);
    }
}
