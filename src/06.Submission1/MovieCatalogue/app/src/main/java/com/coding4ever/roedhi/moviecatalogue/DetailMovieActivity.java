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
