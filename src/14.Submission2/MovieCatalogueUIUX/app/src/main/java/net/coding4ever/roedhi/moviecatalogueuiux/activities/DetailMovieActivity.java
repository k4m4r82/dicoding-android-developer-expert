package net.coding4ever.roedhi.moviecatalogueuiux.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import net.coding4ever.roedhi.moviecatalogueuiux.R;
import net.coding4ever.roedhi.moviecatalogueuiux.helpers.LocaleManager;
import net.coding4ever.roedhi.moviecatalogueuiux.models.Movie;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    LocaleManager localeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        localeManager = new LocaleManager(this);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getResources().getString(R.string.detail_movie));

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.Bind(movie);

        localeManager.setLocale(this);
    }

    private class ViewHolder {
        ImageView imgPhoto;
        TextView tvName;
        TextView tvDesc;
        TextView tvCrew;

        ViewHolder() {
            imgPhoto = findViewById(R.id.img_photo);
            tvName = findViewById(R.id.txt_name);
            tvDesc = findViewById(R.id.txt_desc);
            tvCrew = findViewById(R.id.txt_crew);
        }

        void Bind(Movie movie) {
            int photoId = movie.getPhoto();
            if (photoId > 0) imgPhoto.setImageResource(movie.getPhoto());

            tvName.setText(movie.getName());
            tvDesc.setText(movie.getOverview());

            StringBuilder crews = new StringBuilder();

            for (int i = 0; i < movie.getCrews().length; i++){
                crews.append((i + 1) + ". " + movie.getCrews()[i]).append("\n");
            }

            tvCrew.setText(crews.toString());
        }
    }
}
