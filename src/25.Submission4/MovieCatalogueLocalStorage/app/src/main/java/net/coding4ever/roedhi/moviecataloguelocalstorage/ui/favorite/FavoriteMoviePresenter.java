package net.coding4ever.roedhi.moviecataloguelocalstorage.ui.favorite;

import android.content.Context;
import android.os.AsyncTask;

import net.coding4ever.roedhi.moviecataloguelocalstorage.R;
import net.coding4ever.roedhi.moviecataloguelocalstorage.models.Movie;
import net.coding4ever.roedhi.moviecataloguelocalstorage.repositories.local.MovieRepository;
import net.coding4ever.roedhi.moviecataloguelocalstorage.ui.BaseView;

import java.lang.ref.WeakReference;
import java.util.List;

public class FavoriteMoviePresenter {

    private BaseView<Movie> view;

    private Context context;
    private String languageId;

    public FavoriteMoviePresenter(BaseView<Movie> view, Context context) {
        languageId = context.getResources().getString(R.string.language_id);

        this.context = context;
        this.view = view;
    }

    public void loadData() {
        MovieRepository repository = new MovieRepository(context);
        new FavoriteMovieAsync(languageId, repository, view).execute();
    }

    private static class FavoriteMovieAsync extends AsyncTask<Void, Void, List<Movie>> {

        private final WeakReference<MovieRepository> weakRepository;
        private final WeakReference<BaseView<Movie>> weakCallback;
        private String languageId;

        private FavoriteMovieAsync(String languageId, MovieRepository repository, BaseView<Movie> callback) {
            this.languageId = languageId;

            weakRepository = new WeakReference<>(repository);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            return weakRepository.get().getFavoriteMovies(languageId);
        }

        @Override
        protected void onPostExecute(List<Movie> list) {
            super.onPostExecute(list);

            weakCallback.get().onLoadDataSucceed(list);
        }
    }
}
