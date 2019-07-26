package net.coding4ever.roedhi.moviecatalogueapi.fragments;


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

import net.coding4ever.roedhi.moviecatalogueapi.R;
import net.coding4ever.roedhi.moviecatalogueapi.activities.DetailTvShowActivity;
import net.coding4ever.roedhi.moviecatalogueapi.adapter.TvShowAdapter;
import net.coding4ever.roedhi.moviecatalogueapi.listeners.OnItemClickCallback;
import net.coding4ever.roedhi.moviecatalogueapi.listeners.ProgressBarCallback;
import net.coding4ever.roedhi.moviecatalogueapi.models.TvShow;
import net.coding4ever.roedhi.moviecatalogueapi.presenters.TvShowFragmentPresenter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment
        implements net.coding4ever.roedhi.moviecatalogueapi.views.View<TvShow> {

    private static final String STATE_RESULT = "state_result";
    private RecyclerView rvMovie;
    private TvShowFragmentPresenter presenter;
    private TvShowAdapter listTvShowAdapter;
    private Context context;

    public TvShowFragment() {
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
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeView(view);

        listTvShowAdapter = new TvShowAdapter(context);
        listTvShowAdapter.setOnItemClickCallback(new OnItemClickCallback<TvShow>() {
            @Override
            public void onItemClicked(TvShow tvShow) {
                showLoading(true);

                Intent detailMovieIntent = new Intent(context, DetailTvShowActivity.class);
                detailMovieIntent.putExtra(DetailTvShowActivity.EXTRA_TV_SHOW, tvShow);

                startActivity(detailMovieIntent);
            }
        });

        rvMovie.setAdapter(listTvShowAdapter);

        if (savedInstanceState != null) {
            ArrayList<TvShow>  stateResultList = savedInstanceState.getParcelableArrayList(STATE_RESULT);
            listTvShowAdapter.setListOfTvShow(stateResultList);
        } else {
            presenter = new TvShowFragmentPresenter(this, context);
            presenter.loadTvShow();

            showLoading(true);
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(STATE_RESULT, listTvShowAdapter.getListOfTvShow());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();

        showLoading(false);
    }

    private void initializeView(View view) {
        rvMovie = view.findViewById(R.id.rv_tv_show);
        rvMovie.setLayoutManager(new LinearLayoutManager(context));
        rvMovie.setHasFixedSize(true);
    }

    @Override
    public void onLoadDataSucceed(ArrayList<TvShow> list) {
        listTvShowAdapter.setListOfTvShow(list);
        showLoading(false);
    }

    @Override
    public void onLoadDataFailure() {
        Toast.makeText(context, context.getResources().getString(R.string.load_data_failed), Toast.LENGTH_SHORT).show();
        showLoading(false);
    }

    private void showLoading(Boolean state) {
        ((ProgressBarCallback)context).showLoading(state);
    }
}
