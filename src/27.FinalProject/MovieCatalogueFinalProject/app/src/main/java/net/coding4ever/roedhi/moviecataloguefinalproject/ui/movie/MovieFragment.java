package net.coding4ever.roedhi.moviecataloguefinalproject.ui.movie;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.coding4ever.roedhi.moviecataloguefinalproject.R;
import net.coding4ever.roedhi.moviecataloguefinalproject.listeners.OnItemClickCallback;
import net.coding4ever.roedhi.moviecataloguefinalproject.listeners.ProgressBarCallback;
import net.coding4ever.roedhi.moviecataloguefinalproject.listeners.SearchCallback;
import net.coding4ever.roedhi.moviecataloguefinalproject.models.Movie;
import net.coding4ever.roedhi.moviecataloguefinalproject.ui.BaseView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements BaseView<Movie>, SearchCallback {

    @BindView(R.id.rv_movie) RecyclerView recyclerView;

    private static final String STATE_RESULT = "state_result";
    private MoviePresenter presenter;
    private MovieAdapter adapter;
    private Context context;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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

        initializeView(view);

        adapter = new MovieAdapter(context);
        adapter.setItemClickListener(new OnItemClickCallback<Movie>() {
            @Override
            public void onItemClicked(Movie movie) {

                showLoading(true);

                Intent detailIntent = new Intent(context, DetailMovieActivity.class);
                detailIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);

                startActivity(detailIntent);
            }
        });

        recyclerView.setAdapter(adapter);

        if (savedInstanceState != null) {
            ArrayList<Movie> stateResultList = savedInstanceState.getParcelableArrayList(STATE_RESULT);
            adapter.setItems(stateResultList);
        } else {
            presenter = new MoviePresenter(this, context);
            presenter.loadData();

            showLoading(true);
        }
    }

    @Override
    public void searchMovie(String query) {
        if (presenter != null) {
            presenter.searchData(query);
            showLoading(true);
        }
    }

    @Override
    public void refreshMovie() {
        if (presenter != null) {
            presenter.loadData();
            showLoading(true);
        }
    }

    private void initializeView(View view) {
        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(STATE_RESULT, (ArrayList<Movie>) adapter.getItems());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();

        showLoading(false);
    }

    @Override
    public void onLoadDataSucceed(List<Movie> list) {
        adapter.setItems(list);

        showLoading(false);
    }

    @Override
    public void onLoadDataFailure() {
        Toast.makeText(context, context.getResources().getString(R.string.msg_load_data_failed), Toast.LENGTH_SHORT).show();
        showLoading(false);
    }

    private void showLoading(Boolean state) {
        ((ProgressBarCallback)context).showLoading(state);
    }
}
