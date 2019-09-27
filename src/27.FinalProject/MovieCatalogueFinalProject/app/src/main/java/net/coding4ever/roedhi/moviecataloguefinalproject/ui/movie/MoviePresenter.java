package net.coding4ever.roedhi.moviecataloguefinalproject.ui.movie;

import android.content.Context;

import net.coding4ever.roedhi.moviecataloguefinalproject.BuildConfig;
import net.coding4ever.roedhi.moviecataloguefinalproject.R;
import net.coding4ever.roedhi.moviecataloguefinalproject.listeners.LoadDataCallback;
import net.coding4ever.roedhi.moviecataloguefinalproject.models.Movie;
import net.coding4ever.roedhi.moviecataloguefinalproject.repositories.api.MovieServiceRepository;
import net.coding4ever.roedhi.moviecataloguefinalproject.repositories.local.MovieRepository;
import net.coding4ever.roedhi.moviecataloguefinalproject.ui.BaseView;

import java.util.List;

public class MoviePresenter {

    private BaseView<Movie> view;

    private MovieServiceRepository repository;
    private Context context;

    public MoviePresenter(BaseView<Movie> view, Context context) {

        String apiKey = BuildConfig.TheMovieDBApiKey;
        String languageId = context.getResources().getString(R.string.language_id);

        String baseUrl = context.getResources().getString(R.string.the_moviedb_base_api);
        String imageUrl = context.getResources().getString(R.string.poster_image_url);

        repository = new MovieServiceRepository(baseUrl, imageUrl, apiKey, languageId);

        this.view = view;
        this.context = context;

    }

    public void searchData(String query) {
        repository.search(query, new LoadDataCallback<Movie>() {

            @Override
            public void onDataLoaded(List<Movie> list) {

                MovieRepository repo = new MovieRepository(context);
                repo.save(list);

                view.onLoadDataSucceed(list);
            }

            @Override
            public void onError(Throwable e) {
                view.onLoadDataFailure();
            }

        });

    }

    public void loadData() {
        repository.getAll(new LoadDataCallback<Movie>() {

            @Override
            public void onDataLoaded(List<Movie> list) {

                MovieRepository repo = new MovieRepository(context);
                repo.save(list);

                view.onLoadDataSucceed(list);
            }

            @Override
            public void onError(Throwable e) {
                view.onLoadDataFailure();
            }

        });

    }
}
