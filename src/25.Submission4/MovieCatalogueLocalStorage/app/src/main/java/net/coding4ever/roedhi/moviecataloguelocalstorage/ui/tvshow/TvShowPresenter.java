package net.coding4ever.roedhi.moviecataloguelocalstorage.ui.tvshow;

import android.content.Context;

import net.coding4ever.roedhi.moviecataloguelocalstorage.BuildConfig;
import net.coding4ever.roedhi.moviecataloguelocalstorage.R;
import net.coding4ever.roedhi.moviecataloguelocalstorage.listeners.LoadDataCallback;
import net.coding4ever.roedhi.moviecataloguelocalstorage.models.TvShow;
import net.coding4ever.roedhi.moviecataloguelocalstorage.repositories.api.TvShowServiceRepository;
import net.coding4ever.roedhi.moviecataloguelocalstorage.repositories.local.TvShowRepository;
import net.coding4ever.roedhi.moviecataloguelocalstorage.ui.BaseView;

import java.util.List;

public class TvShowPresenter {

    private BaseView<TvShow> view;

    private TvShowServiceRepository repository;
    private Context context;

    public TvShowPresenter(BaseView<TvShow> view, Context context) {
        String apiKey = BuildConfig.TheMovieDBApiKey;
        String languageId = context.getResources().getString(R.string.language_id);

        String baseUrl = context.getResources().getString(R.string.the_moviedb_base_api);
        String imageUrl = context.getResources().getString(R.string.poster_image_url);

        repository = new TvShowServiceRepository(baseUrl, imageUrl, apiKey, languageId);

        this.view = view;
        this.context = context;
    }

    public void loadData() {
        repository.getAll(new LoadDataCallback<TvShow>() {

            @Override
            public void onDataLoaded(List<TvShow> list) {

                TvShowRepository repo = new TvShowRepository(context);
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
