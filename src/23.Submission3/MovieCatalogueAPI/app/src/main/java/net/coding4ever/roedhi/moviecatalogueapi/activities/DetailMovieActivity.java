package net.coding4ever.roedhi.moviecatalogueapi.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.coding4ever.roedhi.moviecatalogueapi.R;
import net.coding4ever.roedhi.moviecatalogueapi.helpers.LocaleManager;
import net.coding4ever.roedhi.moviecatalogueapi.models.Movie;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    private LocaleManager localeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        localeManager = new LocaleManager(this);
        localeManager.setLocale(this);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getResources().getString(R.string.detail_movie));

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        ViewHolder viewHolder = new ViewHolder(this);
        viewHolder.Bind(movie);
    }


    private class ViewHolder {
        Context context;

        ImageView imgPoster;
        TextView txtTitle;
        TextView txtOverview;
        TextView txtReleaseDate;
        TextView txtVote;

        ViewHolder(Context context) {
            this.context = context;

            txtTitle = findViewById(R.id.txt_title);
            txtOverview = findViewById(R.id.txt_overview);
            txtReleaseDate = findViewById(R.id.txt_release_date);
            txtVote = findViewById(R.id.txt_vote);
            imgPoster = findViewById(R.id.img_poster);
        }

        void Bind(Movie movie) {
            String vote = String.format("%s: %s",
                    context.getResources().getString(R.string.vote), movie.getVoteAverage());

            String releaseDate = String.format("%s: %s",
                    context.getResources().getString(R.string.release_date), movie.getReleaseDate());

            txtTitle.setText(movie.getTitle());
            txtOverview.setText(movie.getOverview());
            txtReleaseDate.setText(releaseDate);
            txtVote.setText(vote);

            Glide.with(context)
                    .load(movie.getPosterPath())
                    .into(imgPoster);
        }
    }
}
