package net.coding4ever.roedhi.favoritemoviefinalproject.ui.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import net.coding4ever.roedhi.favoritemoviefinalproject.R;
import net.coding4ever.roedhi.favoritemoviefinalproject.models.Movie;
import net.coding4ever.roedhi.favoritemoviefinalproject.ui.BaseView;
import net.coding4ever.roedhi.favoritemoviefinalproject.ui.favorite.FavoriteMovieAdapter;
import net.coding4ever.roedhi.favoritemoviefinalproject.ui.favorite.FavoriteMoviePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BaseView<Movie> {

    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.rv_movie) RecyclerView recyclerView;

    private final String TAG = this.getClass().getSimpleName();

    private static final String STATE_RESULT = "state_result";
    private FavoriteMoviePresenter presenter;
    private FavoriteMovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeView();

        adapter = new FavoriteMovieAdapter(this);

        recyclerView.setAdapter(adapter);

        if (savedInstanceState != null) {
            ArrayList<Movie> stateResultList = savedInstanceState.getParcelableArrayList(STATE_RESULT);
            adapter.setItems(stateResultList);
        } else {
            presenter = new FavoriteMoviePresenter(this, this);
            presenter.loadData();

            showLoading(true);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_RESULT, (ArrayList<Movie>) adapter.getItems());
        super.onSaveInstanceState(outState);
    }

    private void initializeView() {
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onLoadDataSucceed(List<Movie> list) {
        adapter.setItems(list);
        showLoading(false);
    }

    @Override
    public void onLoadDataFailure() { }

    private void showLoading(Boolean state) {
        progressBar.setVisibility(state ? View.VISIBLE : View.GONE);
    }
}
