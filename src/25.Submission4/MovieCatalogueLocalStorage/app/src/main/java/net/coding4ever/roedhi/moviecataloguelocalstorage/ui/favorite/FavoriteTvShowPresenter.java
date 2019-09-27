package net.coding4ever.roedhi.moviecataloguelocalstorage.ui.favorite;

import android.content.Context;
import android.os.AsyncTask;

import net.coding4ever.roedhi.moviecataloguelocalstorage.R;
import net.coding4ever.roedhi.moviecataloguelocalstorage.models.TvShow;
import net.coding4ever.roedhi.moviecataloguelocalstorage.repositories.local.TvShowRepository;
import net.coding4ever.roedhi.moviecataloguelocalstorage.ui.BaseView;

import java.lang.ref.WeakReference;
import java.util.List;

public class FavoriteTvShowPresenter {

    private BaseView<TvShow> view;

    private Context context;
    private String languageId;

    public FavoriteTvShowPresenter(BaseView<TvShow> view, Context context) {
        languageId = context.getResources().getString(R.string.language_id);

        this.context = context;
        this.view = view;
    }

    public void loadData() {
        TvShowRepository repository = new TvShowRepository(context);
        new FavoriteTvShowPresenter.FavoriteTvShowAsync(languageId, repository, view).execute();
    }

    private static class FavoriteTvShowAsync extends AsyncTask<Void, Void, List<TvShow>> {

        private final WeakReference<TvShowRepository> weakRepository;
        private final WeakReference<BaseView<TvShow>> weakCallback;
        private String languageId;

        private FavoriteTvShowAsync(String languageId, TvShowRepository repository, BaseView<TvShow> callback) {
            this.languageId = languageId;

            weakRepository = new WeakReference<>(repository);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected List<TvShow> doInBackground(Void... voids) {
            return weakRepository.get().getFavoriteTvShow(languageId);
        }

        @Override
        protected void onPostExecute(List<TvShow> list) {
            super.onPostExecute(list);

            weakCallback.get().onLoadDataSucceed(list);
        }
    }

}
