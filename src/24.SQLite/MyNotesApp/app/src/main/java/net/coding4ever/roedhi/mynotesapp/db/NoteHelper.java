package net.coding4ever.roedhi.mynotesapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.coding4ever.roedhi.mynotesapp.entity.Note;

import java.util.ArrayList;

import static net.coding4ever.roedhi.mynotesapp.db.DatabaseContract.NoteColumns.DATE;
import static net.coding4ever.roedhi.mynotesapp.db.DatabaseContract.NoteColumns.DESCRIPTION;
import static net.coding4ever.roedhi.mynotesapp.db.DatabaseContract.NoteColumns.TITLE;

public class NoteHelper {

    private static final String DATABASE_TABLE = DatabaseContract.TABLE_NOTE;
    private static DatabaseHelper dataBaseHelper;
    private static NoteHelper INSTANCE;
    private static SQLiteDatabase database;

    private NoteHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static NoteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NoteHelper(context);
                }
            }
        }

        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                DatabaseContract.NoteColumns._ID + " ASC",
                null);
        cursor.moveToFirst();
        Note note;
        if (cursor.getCount() > 0) {
            do {
                note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                note.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                note.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                arrayList.add(note);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertNote(Note note) {
        ContentValues args = new ContentValues();
        args.put(TITLE, note.getTitle());
        args.put(DESCRIPTION, note.getDescription());
        args.put(DATE, note.getDate());

        return database.insert(DATABASE_TABLE, null, args);
    }

    public int updateNote(Note note) {
        ContentValues args = new ContentValues();
        args.put(TITLE, note.getTitle());
        args.put(DESCRIPTION, note.getDescription());
        args.put(DATE, note.getDate());

        return database.update(DATABASE_TABLE, args, DatabaseContract.NoteColumns._ID + "= '" + note.getId() + "'", null);
    }

    public int deleteNote(int id) {
        return database.delete(DatabaseContract.TABLE_NOTE, DatabaseContract.NoteColumns._ID + " = '" + id + "'", null);
    }
}
