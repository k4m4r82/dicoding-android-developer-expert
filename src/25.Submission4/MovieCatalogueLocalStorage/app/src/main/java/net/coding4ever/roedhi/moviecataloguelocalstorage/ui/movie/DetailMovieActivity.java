package net.coding4ever.roedhi.moviecataloguelocalstorage.ui.movie;

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
import net.coding4ever.roedhi.moviecataloguelocalstorage.models.Movie;
import net.coding4ever.roedhi.moviecataloguelocalstorage.repositories.local.MovieRepository;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    private LocaleManager localeManager;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        localeManager = new LocaleManager(this);
        localeManager.setLocale(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.detail_movie));
        }

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        ViewHolder viewHolder = new ViewHolder(this);
        viewHolder.Bind(movie);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_favorite){

            MovieRepository repo = new MovieRepository(this);
            movie.setIsFavorite(1);

            int result = repo.setFavorite(movie);

            if (result > 0) {
                Toast.makeText(this, getString(R.string.msg_add_favorite_movie), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.msg_failed_add_favorite_movie), Toast.LENGTH_SHORT).show();
            }

        }

        return super.onOptionsItemSelected(item);
    }

    public class ViewHolder {

        @BindView(R.id.txt_title) TextView txtTitle;
        @BindView(R.id.txt_overview) TextView txtOverview;
        @BindView(R.id.txt_release_date) TextView txtReleaseDate;
        @BindView(R.id.txt_vote) TextView txtVote;
        @BindView(R.id.img_poster) ImageView imgPoster;

        Context context;

        ViewHolder(Context context) {
            this.context = context;

            ButterKnife.bind(this, DetailMovieActivity.this);
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
