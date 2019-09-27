package net.coding4ever.roedhi.moviecataloguelocalstorage.ui.tvshow;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.coding4ever.roedhi.moviecataloguelocalstorage.R;
import net.coding4ever.roedhi.moviecataloguelocalstorage.helpers.LocaleManager;
import net.coding4ever.roedhi.moviecataloguelocalstorage.models.TvShow;
import net.coding4ever.roedhi.moviecataloguelocalstorage.repositories.local.TvShowRepository;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailTvShowActivity extends AppCompatActivity {

    public static final String EXTRA_TV_SHOW = "extra_tv_show";
    private LocaleManager localeManager;
    private TvShow tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        localeManager = new LocaleManager(this);
        localeManager.setLocale(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.detail_tv_show));
        }

        tvShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW);

        ViewHolder viewHolder = new ViewHolder(this);
        viewHolder.Bind(tvShow);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_favorite){

            TvShowRepository repo = new TvShowRepository(this);
            tvShow.setIsFavorite(1);

            int result = repo.setFavorite(tvShow);

            if (result > 0) {
                Toast.makeText(this, getString(R.string.msg_add_favorite_tv_show), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.msg_failed_add_favorite_tv_show), Toast.LENGTH_SHORT).show();
            }

        }

        return super.onOptionsItemSelected(item);
    }

    public class ViewHolder {

        @BindView(R.id.txt_title) TextView txtTitle;
        @BindView(R.id.txt_overview) TextView txtOverview;
        @BindView(R.id.txt_first_air_date) TextView txtFirstAirDate;
        @BindView(R.id.txt_vote) TextView txtVote;
        @BindView(R.id.img_poster) ImageView imgPoster;

        Context context;

        ViewHolder(Context context) {
            this.context = context;

            ButterKnife.bind(this, DetailTvShowActivity.this);
        }

        void Bind(TvShow tvShow) {
            String vote = String.format("%s: %s",
                    context.getResources().getString(R.string.vote), tvShow.getVoteAverage());

            String firstAirDate = String.format("%s: %s",
                    context.getResources().getString(R.string.first_air_date), tvShow.getFirstAirDate());

            txtTitle.setText(tvShow.getTitle());
            txtOverview.setText(tvShow.getOverview());
            txtFirstAirDate.setText(firstAirDate);
            txtVote.setText(vote);

            Glide.with(context)
                    .load(tvShow.getPosterPath())
                    .into(imgPoster);
        }
    }
}
