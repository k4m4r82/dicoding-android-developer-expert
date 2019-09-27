package net.coding4ever.roedhi.moviecataloguefinalproject.ui.favorite;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.coding4ever.roedhi.moviecataloguefinalproject.R;
import net.coding4ever.roedhi.moviecataloguefinalproject.listeners.ProgressBarCallback;
import net.coding4ever.roedhi.moviecataloguefinalproject.models.TvShow;
import net.coding4ever.roedhi.moviecataloguefinalproject.ui.BaseView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvShowFragment extends Fragment implements BaseView<TvShow> {

    @BindView(R.id.rv_tv_show) RecyclerView recyclerView;

    private static final String STATE_RESULT = "state_result";
    private FavoriteTvShowPresenter presenter;
    private FavoriteTvShowAdapter adapter;
    private Context context;

    public FavoriteTvShowFragment() {
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
        return inflater.inflate(R.layout.fragment_favorite_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeView(view);

        adapter = new FavoriteTvShowAdapter(context);

        recyclerView.setAdapter(adapter);

        if (savedInstanceState != null) {
            ArrayList<TvShow> stateResultList = savedInstanceState.getParcelableArrayList(STATE_RESULT);
            adapter.setItems(stateResultList);
        } else {
            presenter = new FavoriteTvShowPresenter(this, context);
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
        outState.putParcelableArrayList(STATE_RESULT, (ArrayList<TvShow>) adapter.getItems());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLoadDataSucceed(List<TvShow> list) {
        adapter.setItems(list);
        showLoading(false);
    }

    @Override
    public void onLoadDataFailure() {

    }

    private void showLoading(Boolean state) {
        ((ProgressBarCallback)context).showLoading(state);
    }
}
