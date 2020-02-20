package com.example.mysubmissionmadefour.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mysubmissionmadefour.db.TvShowHelper;

import static com.example.mysubmissionmadefour.db.DatabaseContract.TVSHOW_AUTHORITY;
import static com.example.mysubmissionmadefour.db.DatabaseContract.TVSHOW_TABLE;
import static com.example.mysubmissionmadefour.db.DatabaseContract.TvShowColumns.TVSHOW_CONTENT_URI;

public class TvShowProvider extends ContentProvider {

    private static final int TVSHOW = 1;
    private static final int TVSHOW_ID = 2;
    private TvShowHelper tvShowHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(TVSHOW_AUTHORITY, TVSHOW_TABLE, TVSHOW);
        sUriMatcher.addURI(TVSHOW_AUTHORITY, TVSHOW_TABLE + "/#", TVSHOW_ID);
    }

    public TvShowProvider() {
    }

    @Override
    public boolean onCreate() {
        tvShowHelper = TvShowHelper.getInstance(getContext());
        tvShowHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case TVSHOW:
                cursor = tvShowHelper.queryAll();
                break;
            case TVSHOW_ID:
                cursor = tvShowHelper.queryById(uri.getLastPathSegment());
                break;
                default:
                    cursor = null;
                    break;
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
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added;
        switch (sUriMatcher.match(uri)){
            case TVSHOW:
                added = tvShowHelper.insert(values);
                break;
                default:
                    added = 0;
                    break;
        }
        getContext().getContentResolver().notifyChange(TVSHOW_CONTENT_URI, null);
        return Uri.parse(TVSHOW_CONTENT_URI + "/" + added);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updated;
        switch (sUriMatcher.match(uri)){
            case TVSHOW_ID:
                updated = tvShowHelper.update(uri.getLastPathSegment(), values);
                break;
                default:
                    updated = 0;
                    break;
        }
        getContext().getContentResolver().notifyChange(TVSHOW_CONTENT_URI, null);
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)){
            case TVSHOW_ID:
                deleted = tvShowHelper.deleteById(uri.getLastPathSegment());
                break;
                default:
                    deleted = 0;
                    break;
        }
        getContext().getContentResolver().notifyChange(TVSHOW_CONTENT_URI, null);
        return deleted;
    }
}
