package net.coding4ever.roedhi.moviecataloguefinalproject.repositories.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbContext extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "db_themovie.db";

    private static final String SQL_CREATE_TABLE_MOVIE = "CREATE TABLE [movie](\n" +
            "    [id] INT, \n" +
            "    [language_id] VARCHAR(6), \n" +
            "    [title] VARCHAR(100), \n" +
            "    [overview] VARCHAR(500), \n" +
            "    [poster_path] VARCHAR(100), \n" +
            "    [is_favorite] SMALLINT DEFAULT 0, \n" +
            "    PRIMARY KEY([id], [language_id]));";

    private static final String SQL_CREATE_TABLE_TV_SHOW = "CREATE TABLE [tv_show](\n" +
            "    [id] INT, \n" +
            "    [language_id] VARCHAR(6), \n" +
            "    [title] VARCHAR(100), \n" +
            "    [overview] VARCHAR(500), \n" +
            "    [poster_path] VARCHAR(100), \n" +
            "    [is_favorite] SMALLINT DEFAULT 0, \n" +
            "    PRIMARY KEY([id], [language_id]));\n";

    public DbContext(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_TV_SHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void close(SQLiteDatabase db) {
        db.close();
    }

    public void close(Cursor cursor, SQLiteDatabase db) {
        if (cursor != null) cursor.close();

        db.close();
    }
}
