package net.coding4ever.roedhi.moviecataloguelocalstorage.ui.movie;

import android.content.Context;

import net.coding4ever.roedhi.moviecataloguelocalstorage.BuildConfig;
import net.coding4ever.roedhi.moviecataloguelocalstorage.R;
import net.coding4ever.roedhi.moviecataloguelocalstorage.listeners.LoadDataCallback;
import net.coding4ever.roedhi.moviecataloguelocalstorage.models.Movie;
import net.coding4ever.roedhi.moviecataloguelocalstorage.repositories.api.MovieServiceRepository;
import net.coding4ever.roedhi.moviecataloguelocalstorage.repositories.local.MovieRepository;
import net.coding4ever.roedhi.moviecataloguelocalstorage.ui.BaseView;

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
