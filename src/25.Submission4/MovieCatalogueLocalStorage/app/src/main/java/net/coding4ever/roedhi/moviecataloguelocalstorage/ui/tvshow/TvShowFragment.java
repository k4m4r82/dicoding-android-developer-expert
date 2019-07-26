package net.coding4ever.roedhi.moviecataloguelocalstorage.ui.tvshow;


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

import net.coding4ever.roedhi.moviecataloguelocalstorage.R;
import net.coding4ever.roedhi.moviecataloguelocalstorage.listeners.OnItemClickCallback;
import net.coding4ever.roedhi.moviecataloguelocalstorage.listeners.ProgressBarCallback;
import net.coding4ever.roedhi.moviecataloguelocalstorage.models.TvShow;
import net.coding4ever.roedhi.moviecataloguelocalstorage.ui.BaseView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment implements BaseView<TvShow> {

    @BindView(R.id.rv_tv_show) RecyclerView recyclerView;

    private static final String STATE_RESULT = "state_result";
    private TvShowPresenter presenter;
    private TvShowAdapter adapter;
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

        adapter = new TvShowAdapter(context);
        adapter.setItemClickListener(new OnItemClickCallback<TvShow>() {
            @Override
            public void onItemClicked(TvShow tvShow) {

                showLoading(true);

                Intent detailIntent = new Intent(context, DetailTvShowActivity.class);
                detailIntent.putExtra(DetailTvShowActivity.EXTRA_TV_SHOW, tvShow);

                startActivity(detailIntent);
            }
        });

        recyclerView.setAdapter(adapter);

        if (savedInstanceState != null) {
            ArrayList<TvShow> stateResultList = savedInstanceState.getParcelableArrayList(STATE_RESULT);
            adapter.setItems(stateResultList);
        } else {
            presenter = new TvShowPresenter(this, context);
            presenter.loadData();

            showLoading(true);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(STATE_RESULT, (ArrayList<TvShow>) adapter.getItems());
        super.onSaveInstanceState(outState);
    }

    private void initializeView(View view) {
        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        showLoading(false);
    }

    @Override
    public void onLoadDataSucceed(List<TvShow> list) {
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
