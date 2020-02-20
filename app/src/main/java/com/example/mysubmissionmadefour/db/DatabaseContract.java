package com.example.mysubmissionmadefour.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String MOVIE_AUTHORITY = "com.example.mysubmissionmadefour";
    public static final String TVSHOW_AUTHORITY = "com.example.mysubmissionmadefour";
    private static final String MOVIE_SCHEME = "content";
    private static final String TVSHOW_SCHEME = "content";

    public static final String MOVIE_TABLE = "movies";
    public static final String TVSHOW_TABLE = "tvshows";

    public DatabaseContract() {}

    public static final class MovieColumns implements BaseColumns {
        public static final String _ID = "id";
        public static final String PHOTO = "photo";
        public static final String NAME = "name";
        public static final String RELEASE = "release";
        public static final String DESCRIPTION ="description";

        /* Untuk membuat URI content :  com.example.mysubmissionmadefour/movie */
        public static final Uri MOVIE_CONTENT_URI = new Uri.Builder().scheme(MOVIE_SCHEME).authority(MOVIE_AUTHORITY).appendPath(MOVIE_TABLE).build();

    }

    public static final class TvShowColumns implements BaseColumns {
        public static final String _ID = "id";
        public static final String PHOTO = "photo";
        public static final String NAME = "name";
        public static final String RELEASE = "release";
        public static final String DESCRIPTION = "description";

        public static final Uri TVSHOW_CONTENT_URI = new Uri.Builder().scheme(TVSHOW_SCHEME).authority(TVSHOW_AUTHORITY).appendPath(TVSHOW_TABLE).build();
    }
}
