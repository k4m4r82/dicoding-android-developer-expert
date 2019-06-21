package com.coding4ever.roedhi.moviecatalogue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.coding4ever.roedhi.moviecatalogue.model.Movie;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        ImageView imgPhoto = findViewById(R.id.img_photo);
        TextView tvName = findViewById(R.id.txt_name);
        TextView tvDesc = findViewById(R.id.txt_desc);
        TextView tvCrew = findViewById(R.id.txt_crew);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

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
