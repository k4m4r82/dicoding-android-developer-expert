package net.coding4ever.roedhi.moviecataloguefinalproject.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.coding4ever.roedhi.moviecataloguefinalproject.helpers.LocaleManager;
import net.coding4ever.roedhi.moviecataloguefinalproject.models.Movie;
import net.coding4ever.roedhi.moviecataloguefinalproject.repositories.local.MovieRepository;

public class FavoriteMovieProvider extends ContentProvider {

    private static final String AUTHORITY = "net.coding4ever.roedhi.moviecataloguefinalproject";
    private static final String TABLE_NAME = "movie";

    private static final int MOVIE = 1;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, MOVIE);
    }

    private MovieRepository repository;

    @Override
    public boolean onCreate() {

        repository = new MovieRepository(getContext());

        return true;

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        LocaleManager localeManager = new LocaleManager(getContext());

        String languageId = localeManager.getLanguage().equals("in") ? "id" :  "en-US";

        MatrixCursor cursor = new MatrixCursor (
                new String[] { "id", "title", "overview", "poster_path" }
        );

        for (Movie movie : repository.getFavoriteMovies(languageId) ) {
            cursor.newRow()
                    .add("id", movie.getId())
                    .add("title", movie.getTitle())
                    .add("overview", movie.getOverview())
                    .add("poster_path", movie.getPosterPath());
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
