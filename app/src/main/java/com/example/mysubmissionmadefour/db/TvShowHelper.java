package com.example.mysubmissionmadefour.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.mysubmissionmadefour.db.DatabaseContract.TVSHOW_TABLE;
import static com.example.mysubmissionmadefour.db.DatabaseContract.TvShowColumns._ID;

public class TvShowHelper {
    private static final String DATABASE_TABLE = TVSHOW_TABLE;
    private static DatabaseHelper databaseHelper;
    private static TvShowHelper INSTANCE;

    private static SQLiteDatabase database;

    public TvShowHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static TvShowHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new TvShowHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public Cursor queryAll() {
        return database.query(DATABASE_TABLE, null, null, null, null, null, _ID);
    }

    public Cursor queryById(String id){
        return database.query(DATABASE_TABLE, null, _ID + " = ?", new String[]{id}, null, null, null, null);
    }

    public long insert(ContentValues values){
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int update(String id, ContentValues values){
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteById(String id){
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
