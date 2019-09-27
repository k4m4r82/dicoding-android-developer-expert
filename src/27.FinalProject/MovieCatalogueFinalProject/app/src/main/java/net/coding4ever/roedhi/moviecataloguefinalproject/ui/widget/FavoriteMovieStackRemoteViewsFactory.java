package net.coding4ever.roedhi.moviecataloguefinalproject.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;

import net.coding4ever.roedhi.moviecataloguefinalproject.R;
import net.coding4ever.roedhi.moviecataloguefinalproject.models.Movie;
import net.coding4ever.roedhi.moviecataloguefinalproject.repositories.local.MovieRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FavoriteMovieStackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final String TAG = this.getClass().getSimpleName();

    private List<Movie> list = new ArrayList<>();

    private Context mContext;
    private MovieRepository repository;
    private String languageId;

    FavoriteMovieStackRemoteViewsFactory(Context context) {
        mContext = context;
        languageId = context.getResources().getString(R.string.language_id);

        repository = new MovieRepository(mContext);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        list = repository.getFavoriteMovies(languageId);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        try {

            Movie movie = list.get(position);

            // ganti ukuran poster
            String posterPath = movie.getPosterPath().replace("/w92", "/w342");

            Bitmap poster_movie = Glide.with(mContext)
                    .asBitmap()
                    .load(posterPath)
                    .submit()
                    .get();


            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_widget);
            rv.setImageViewBitmap(R.id.imageView, poster_movie);


            return rv;

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
