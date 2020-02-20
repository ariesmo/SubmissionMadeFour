package com.example.mysubmissionmadefour.helper;

import android.database.Cursor;

import com.example.mysubmissionmadefour.db.DatabaseContract;
import com.example.mysubmissionmadefour.entity.Movie;
import com.example.mysubmissionmadefour.entity.TvShow;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<Movie> mapCursorToArrayListMovie(Cursor movieCursor){
        ArrayList<Movie> movieList = new ArrayList<>();

        while (movieCursor.moveToNext()){
            int id              = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID));
            String name         = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.NAME));
            String release      = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.RELEASE));
            String description  = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.DESCRIPTION));
            String photo        = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.PHOTO));
            movieList.add(new Movie(id, name, release, description, photo));
        }
        return movieList;
    }

    public static ArrayList<TvShow> mapCursorToArrayListTvShow(Cursor tvShowCursor){
        ArrayList<TvShow> tvShowList = new ArrayList<>();

        while (tvShowCursor.moveToNext()){
            int id              = tvShowCursor.getInt(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns._ID));
            String name         = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.NAME));
            String release      = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.RELEASE));
            String description  = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.DESCRIPTION));
            String photo        = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.PHOTO));
            tvShowList.add(new TvShow(id, name, release, description, photo));
        }
        return tvShowList;
    }

    public static Movie mapCursorToObjectMovie(Cursor movieCursor){
        movieCursor.moveToFirst();

        int id              = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID));
        String name         = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.NAME));
        String release      = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.RELEASE));
        String description  = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.DESCRIPTION));
        String photo        = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.PHOTO));

        return new Movie(id, name, release, description, photo);
    }

    public static TvShow mapCursorToObjectTvShow(Cursor tvShowCursor){
        tvShowCursor.moveToFirst();

        int id              = tvShowCursor.getInt(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns._ID));
        String name         = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.NAME));
        String release      = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.RELEASE));
        String description  = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.DESCRIPTION));
        String photo        = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.PHOTO));

        return new TvShow(id, name, release, description, photo);
    }
}
