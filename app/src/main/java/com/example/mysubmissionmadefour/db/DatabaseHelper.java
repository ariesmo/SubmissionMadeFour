package com.example.mysubmissionmadefour.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "database";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.MOVIE_TABLE,
            DatabaseContract.MovieColumns._ID,
            DatabaseContract.MovieColumns.PHOTO,
            DatabaseContract.MovieColumns.NAME,
            DatabaseContract.MovieColumns.RELEASE,
            DatabaseContract.MovieColumns.DESCRIPTION
    );

    private static final String SQL_CREATE_TABLE_TVSHOW = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TVSHOW_TABLE,
            DatabaseContract.TvShowColumns._ID,
            DatabaseContract.TvShowColumns.PHOTO,
            DatabaseContract.TvShowColumns.NAME,
            DatabaseContract.TvShowColumns.RELEASE,
            DatabaseContract.TvShowColumns.DESCRIPTION
    );

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_TVSHOW);
        Log.d("Test", "Database created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.MOVIE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TVSHOW_TABLE);
        onCreate(db);
    }
}
