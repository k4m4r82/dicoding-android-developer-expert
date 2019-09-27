package net.coding4ever.roedhi.moviecataloguefinalproject.repositories.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import net.coding4ever.roedhi.moviecataloguefinalproject.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    private final String TAG = this.getClass().getSimpleName();
    private final String TABLE_NAME = "movie";
    private DbContext conn;

    public MovieRepository(Context context) {
        conn = new DbContext(context);
    }

    public void save(List<Movie> list) {

        // get reference to writable DB
        SQLiteDatabase db = conn.getWritableDatabase();

        try {

            db.beginTransaction();

            for (Movie obj : list) {

                if (!isExist(obj)) {

                    // create ContentValues to add key "column"/value
                    ContentValues values = new ContentValues();
                    values.put("id", obj.getId());
                    values.put("language_id", obj.getLanguageId());
                    values.put("title", obj.getTitle());
                    values.put("overview", obj.getOverview());
                    values.put("poster_path", obj.getPosterPath());

                    // insert
                    db.insert(TABLE_NAME, null, values);
                }
            }

            db.setTransactionSuccessful();
            db.endTransaction();

        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            conn.close(db);
        }

    }

    public int setFavorite(Movie obj) {

        int result = 0;

        // get reference to writable DB
        SQLiteDatabase db = conn.getWritableDatabase();

        try {
            // create ContentValues to add key "column"/value
            ContentValues values = new ContentValues();
            values.put("is_favorite", obj.getIsFavorite());

            // set parameter query
            String[] params = { String.valueOf(obj.getId()) };

            // update
            result = db.update(TABLE_NAME, values, "id = ?", params);

        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            conn.close(db);
        }

        return result;
    }

    public List<Movie> getFavoriteMovies(String languageId) {

        List<Movie> list = new ArrayList<>();

        // get reference to readable DB
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = null;

        try {

            // set parameter query
            String[] params = { languageId };

            // build query
            String sql = "SELECT * " +
                    "FROM %s " +
                    "WHERE language_id = ? AND id IN (SELECT DISTINCT id FROM %s WHERE is_favorite = 1) " +
                    "ORDER BY title";

            cursor = db.rawQuery(String.format(sql, TABLE_NAME, TABLE_NAME), params);

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
            conn.close(cursor, db);
        }

        return list;

    }

    private boolean isExist(Movie obj) {

        boolean result = false;

        // get reference to readable DB
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor;

        try {

            // set parameter query
            String[] params = { String.valueOf(obj.getId()), obj.getLanguageId() };

            // build query
            String sql = "SELECT COUNT(*) " +
                    "FROM %s " +
                    "WHERE id = ? AND language_id = ?";
            cursor = db.rawQuery(String.format(sql, TABLE_NAME), params);

            // if we got results get the first one
            if (cursor.moveToFirst())
                result = cursor.getInt(0) > 0;

        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

}
