package net.coding4ever.roedhi.moviecatalogueapi.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.coding4ever.roedhi.moviecatalogueapi.R;
import net.coding4ever.roedhi.moviecatalogueapi.helpers.LocaleManager;
import net.coding4ever.roedhi.moviecatalogueapi.models.TvShow;

public class DetailTvShowActivity extends AppCompatActivity {

    public static final String EXTRA_TV_SHOW = "extra_tv_show";
    private LocaleManager localeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        localeManager = new LocaleManager(this);
        localeManager.setLocale(this);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getResources().getString(R.string.detail_tv_show));

        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW);

        ViewHolder viewHolder = new ViewHolder(this);
        viewHolder.Bind(tvShow);
    }

    private class ViewHolder {
        Context context;

        ImageView imgPoster;
        TextView txtTitle;
        TextView txtOverview;
        TextView txtFirstAirDate;
        TextView txtVote;

        ViewHolder(Context context) {
            this.context = context;

            txtTitle = findViewById(R.id.txt_title);
            txtOverview = findViewById(R.id.txt_overview);
            txtFirstAirDate = findViewById(R.id.txt_first_air_date);
            txtVote = findViewById(R.id.txt_vote);
            imgPoster = findViewById(R.id.img_poster);
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
