package com.coding4ever.roedhi.moviecatalogue;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.coding4ever.roedhi.moviecatalogue.adapter.MovieAdapter;
import com.coding4ever.roedhi.moviecatalogue.model.Movie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String[] dataMovie;
    private String[] dataShortOverview;
    private String[] dataOverview;
    private String[] dataCrew;
    private TypedArray dataPhoto;

    private MovieAdapter adapter;
    private ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvMovies = findViewById(R.id.lv_movies);

        adapter = new MovieAdapter(this);
        lvMovies.setAdapter(adapter);

        prepare();
        addItem();

        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Movie movie = movies.get(i);

            Intent detailMovieIntent = new Intent(MainActivity.this, DetailMovieActivity.class);
            detailMovieIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);

            startActivity(detailMovieIntent);
            }
        });
    }

    private void prepare() {
        dataMovie = getResources().getStringArray(R.array.movies_name);
        dataShortOverview = getResources().getStringArray(R.array.short_overview);
        dataOverview = getResources().getStringArray(R.array.movies_overview);
        dataCrew = getResources().getStringArray(R.array.movies_crew);
        dataPhoto = getResources().obtainTypedArray(R.array.movies_photo);
    }

    private void addItem() {
        movies = new ArrayList<>();

        for (int i = 0; i < dataMovie.length; i++) {

            String[] crews = dataCrew[i].split(",");

            Movie movie = new Movie();
            movie.setPhoto(dataPhoto.getResourceId(i, -1));
            movie.setName(dataMovie[i]);
            movie.setShortOverview(dataShortOverview[i]);
            movie.setOverview(dataOverview[i]);
            movie.setCrews(crews);

            movies.add(movie);
        }

        adapter.setMovies(movies);
    }
}
