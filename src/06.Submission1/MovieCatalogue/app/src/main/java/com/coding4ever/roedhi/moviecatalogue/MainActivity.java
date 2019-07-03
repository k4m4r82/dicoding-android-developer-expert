/**
 * Copyright (C) 2019 Kamarudin (http://coding4ever.net/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * The latest version of this file can be found at https://github.com/k4m4r82/dicoding-android-developer-expert
 */
 
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
