package net.coding4ever.roedhi.moviecatalogueuiux.fragments;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.coding4ever.roedhi.moviecatalogueuiux.R;
import net.coding4ever.roedhi.moviecatalogueuiux.activities.DetailMovieActivity;
import net.coding4ever.roedhi.moviecatalogueuiux.adapters.MovieAdapter;
import net.coding4ever.roedhi.moviecatalogueuiux.listeners.OnItemClickCallback;
import net.coding4ever.roedhi.moviecatalogueuiux.models.Movie;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private RecyclerView rvMovie;
    private ArrayList<Movie> list = new ArrayList<>();

    private String[] dataMovie;
    private String[] dataShortOverview;
    private String[] dataOverview;
    private String[] dataCrew;
    private TypedArray dataPhoto;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMovie = view.findViewById(R.id.rv_movie);
        rvMovie.setHasFixedSize(true);

        showRvMovie();

        prepare();
        addItem();
    }

    private void prepare() {
        dataMovie = getResources().getStringArray(R.array.movies_name);
        dataShortOverview = getResources().getStringArray(R.array.short_overview);
        dataOverview = getResources().getStringArray(R.array.movies_overview);
        dataCrew = getResources().getStringArray(R.array.movies_crew);
        dataPhoto = getResources().obtainTypedArray(R.array.movies_photo);
    }

    private void addItem() {
        for (int i = 0; i < dataMovie.length; i++) {

            String[] crews = dataCrew[i].split(",");

            Movie movie = new Movie();
            movie.setPhoto(dataPhoto.getResourceId(i, -1));
            movie.setName(dataMovie[i]);
            movie.setShortOverview(dataShortOverview[i]);
            movie.setOverview(dataOverview[i]);
            movie.setCrews(crews);

            list.add(movie);
        }
    }

    private void showRvMovie(){
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));

        MovieAdapter listMovieAdapter = new MovieAdapter(getActivity());
        listMovieAdapter.setListMovie(list);

        rvMovie.setAdapter(listMovieAdapter);

        listMovieAdapter.setOnItemClickCallback(new OnItemClickCallback<Movie>() {
            @Override
            public void onItemClicked(Movie movie) {
                Intent detailMovieIntent = new Intent(getActivity(), DetailMovieActivity.class);
                detailMovieIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);

                startActivity(detailMovieIntent);
            }
        });
    }
}
