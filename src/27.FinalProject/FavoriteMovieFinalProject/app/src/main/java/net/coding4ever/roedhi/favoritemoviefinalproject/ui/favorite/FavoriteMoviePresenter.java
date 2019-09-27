package net.coding4ever.roedhi.favoritemoviefinalproject.ui.favorite;

import android.content.Context;
import android.os.AsyncTask;

import net.coding4ever.roedhi.favoritemoviefinalproject.models.Movie;
import net.coding4ever.roedhi.favoritemoviefinalproject.repositories.FavoriteMovieRepository;
import net.coding4ever.roedhi.favoritemoviefinalproject.ui.BaseView;

import java.lang.ref.WeakReference;
import java.util.List;

public class FavoriteMoviePresenter {

    private BaseView<Movie> view;

    private Context context;

    public FavoriteMoviePresenter(BaseView<Movie> view, Context context) {

        this.context = context;
        this.view = view;
    }

    public void loadData() {
        FavoriteMovieRepository repository = new FavoriteMovieRepository(context);
        new FavoriteMovieAsync(repository, view).execute();
    }

    private static class FavoriteMovieAsync extends AsyncTask<Void, Void, List<Movie>> {

        private final WeakReference<FavoriteMovieRepository> weakRepository;
        private final WeakReference<BaseView<Movie>> weakViewCallback;

        private FavoriteMovieAsync(FavoriteMovieRepository repository, BaseView<Movie> viewCallback) {
            weakRepository = new WeakReference<>(repository);
            weakViewCallback = new WeakReference<>(viewCallback);
        }

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            return weakRepository.get().getFavoriteMovies();
        }

        @Override
        protected void onPostExecute(List<Movie> list) {
            super.onPostExecute(list);

            weakViewCallback.get().onLoadDataSucceed(list);
        }
    }

}