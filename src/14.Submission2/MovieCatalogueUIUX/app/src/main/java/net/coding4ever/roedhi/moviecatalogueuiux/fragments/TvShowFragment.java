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
import net.coding4ever.roedhi.moviecatalogueuiux.activities.DetailTvShowActivity;
import net.coding4ever.roedhi.moviecatalogueuiux.adapters.TvShowAdapter;
import net.coding4ever.roedhi.moviecatalogueuiux.listeners.OnItemClickCallback;
import net.coding4ever.roedhi.moviecatalogueuiux.models.TvShow;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private RecyclerView rvTvShow;
    private ArrayList<TvShow> list = new ArrayList<>();

    private String[] dataTvShow;
    private String[] dataShortOverview;
    private String[] dataOverview;
    private String[] dataCrew;
    private TypedArray dataPhoto;

    public TvShowFragment() {
        // Required empty public constructor
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

        rvTvShow = view.findViewById(R.id.rv_tv_show);
        rvTvShow.setHasFixedSize(true);

        showRvTvShow();

        prepare();
        addItem();
    }

    private void prepare() {
        dataTvShow = getResources().getStringArray(R.array.tv_show_name);
        dataShortOverview = getResources().getStringArray(R.array.tv_show_short_overview);
        dataOverview = getResources().getStringArray(R.array.tv_show_overview);
        dataCrew = getResources().getStringArray(R.array.tv_show_crew);
        dataPhoto = getResources().obtainTypedArray(R.array.tv_show_photo);
    }

    private void addItem() {
        for (int i = 0; i < dataTvShow.length; i++) {

            String[] crews = dataCrew[i].split(",");

            TvShow tvShow = new TvShow();
            tvShow.setPhoto(dataPhoto.getResourceId(i, -1));
            tvShow.setName(dataTvShow[i]);
            tvShow.setShortOverview(dataShortOverview[i]);
            tvShow.setOverview(dataOverview[i]);
            tvShow.setCrews(crews);

            list.add(tvShow);
        }
    }

    private void showRvTvShow(){
        rvTvShow.setLayoutManager(new LinearLayoutManager(getActivity()));

        TvShowAdapter listMovieAdapter = new TvShowAdapter(getActivity());
        listMovieAdapter.setListTvShow(list);

        rvTvShow.setAdapter(listMovieAdapter);

        listMovieAdapter.setOnItemClickCallback(new OnItemClickCallback<TvShow>() {
            @Override
            public void onItemClicked(TvShow tvShow) {
                Intent detailTvShowIntent = new Intent(getActivity(), DetailTvShowActivity.class);
                detailTvShowIntent.putExtra(DetailTvShowActivity.EXTRA_TV_SHOW, tvShow);

                startActivity(detailTvShowIntent);
            }
        });
    }
}
