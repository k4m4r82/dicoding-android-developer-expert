package net.coding4ever.roedhi.favoritemoviefinalproject.repositories;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;

import net.coding4ever.roedhi.favoritemoviefinalproject.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieRepository {

    private final String TAG = this.getClass().getSimpleName();

    private static final String AUTHORITY = "net.coding4ever.roedhi.moviecataloguefinalproject";
    private static final String SCHEME = "content";
    private static final String TABLE_NAME = "movie";

    private static final Uri CONTENT_URI = new Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    private Context context;

    public FavoriteMovieRepository(Context context) {
        this.context = context;
    }

    public List<Movie> getFavoriteMovies() {

        List<Movie> list = new ArrayList<>();

        Cursor cursor = context.getContentResolver()
                .query(CONTENT_URI, null, null, null, null);

        try {

            while (cursor.moveToNext()) {

                // build object
                Movie obj = new Movie();
                obj.setId(cursor.getInt(cursor.getColumnIndex("id")));
                obj.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                obj.setOverview(cursor.getString(cursor.getColumnIndex("overview")));
                obj.setPosterPath(cursor.getString(cursor.getColumnIndex("poster_path")));

                // add to collection
                list.add(obj);

            }

        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());

        } finally {
            if (cursor != null) cursor.close();
        }

        return list;

    }
}
