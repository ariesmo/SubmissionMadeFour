package com.example.mysubmissionmadefour.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.example.mysubmissionmadefour.db.MovieHelper;

import static com.example.mysubmissionmadefour.db.DatabaseContract.MOVIE_AUTHORITY;
import static com.example.mysubmissionmadefour.db.DatabaseContract.MOVIE_TABLE;
import static com.example.mysubmissionmadefour.db.DatabaseContract.MovieColumns.MOVIE_CONTENT_URI;

public class MovieProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private MovieHelper movieHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(MOVIE_AUTHORITY, MOVIE_TABLE, MOVIE);
        sUriMatcher.addURI(MOVIE_AUTHORITY, MOVIE_TABLE + "/#", MOVIE_ID);
    }

    public MovieProvider() {
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        movieHelper = MovieHelper.getInstance(getContext());
        movieHelper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                cursor = movieHelper.queryAll();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        long added;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                added = movieHelper.insert(values);
                break;
                default:
                    added = 0;
                    break;
        }
        getContext().getContentResolver().notifyChange(MOVIE_CONTENT_URI, null);
        return Uri.parse(MOVIE_CONTENT_URI + "/" + added);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        int updated;
        switch (sUriMatcher.match(uri)){
            case MOVIE_ID:
                updated = movieHelper.update(uri.getLastPathSegment(), values);
                break;
                default:
                    updated = 0;
                    break;
        }
        getContext().getContentResolver().notifyChange(MOVIE_CONTENT_URI, null);
        return updated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int deleted;
        switch (sUriMatcher.match(uri)){
            case MOVIE_ID:
                deleted = movieHelper.deleteById(uri.getLastPathSegment());
                break;
                default:
                    deleted = 0;
                    break;
        }
        getContext().getContentResolver().notifyChange(MOVIE_CONTENT_URI, null);
        return deleted;
    }


}
