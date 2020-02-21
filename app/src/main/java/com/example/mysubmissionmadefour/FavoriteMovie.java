package com.example.mysubmissionmadefour;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysubmissionmadefour.adapter.FavoriteMovieAdapter;
import com.example.mysubmissionmadefour.db.DatabaseContract;
import com.example.mysubmissionmadefour.db.MovieHelper;
import com.example.mysubmissionmadefour.entity.Movie;
import com.example.mysubmissionmadefour.helper.MappingHelper;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FavoriteMovie extends Fragment implements LoadMovieCallback{

    private static final String EXTRA_STATE_MOVIE = "extra_state_movie";

    private FavoriteMovieAdapter adapter;
    private ProgressBar progressBar;
    private RecyclerView rvMovies;
    private MovieHelper movieHelper;
    ArrayList<Movie> listMovie = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        rvMovies    = view.findViewById(R.id.rv_movie);
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovies.setHasFixedSize(true);

        adapter     = new FavoriteMovieAdapter(getContext());
        adapter.notifyDataSetChanged();
        rvMovies.setAdapter(adapter);

//        HandlerThread thread = new HandlerThread("MovieObserver");
//        thread.start();
//
//        Handler handler = new Handler(thread.getLooper());
//
//        MovieObserver observer = new MovieObserver(handler, getContext());
//        getActivity().getContentResolver().registerContentObserver(DatabaseContract.MovieColumns.MOVIE_CONTENT_URI, true, observer);

        if (savedInstanceState == null){
            new LoadMovieAsync(getContext(), this).execute();
        } else {
            ArrayList<Movie> listMovie = savedInstanceState.getParcelableArrayList(EXTRA_STATE_MOVIE);
            if (listMovie != null){
                adapter.setListFavMovie(listMovie);
            }
        }

        return view;
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvMovies, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<Movie> listMovie) {
        progressBar.setVisibility(View.INVISIBLE);
        if (listMovie.size() > 0){
            adapter.setListFavMovie(listMovie);
        } else {
            adapter.setListFavMovie(new ArrayList<Movie>());
            showSnackbarMessage("Tidak ada data saat ini");
        }
    }

    private static class LoadMovieAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMovieCallback> weakCallback;

        private LoadMovieAsync(Context context, LoadMovieCallback callback) {
            weakContext     = new WeakReference<>(context);
            weakCallback    = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            Context context     = weakContext.get();
            Cursor cursor       = context.getContentResolver().query(DatabaseContract.MovieColumns.MOVIE_CONTENT_URI, null, null, null, null);
            Log.d("cursor", String.valueOf(cursor));
            return MappingHelper.mapCursorToArrayListMovie(cursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> list) {
            super.onPostExecute(list);
            weakCallback.get().postExecute(list);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE_MOVIE, adapter.getListFavMovie());
    }

//    public static class MovieObserver extends ContentObserver {
//
//        final Context context;
//        /**
//         * Creates a content observer.
//         *  @param handler The handler to run {@link #onChange} on, or null if none.
//         * @param context
//         */
//        public MovieObserver(Handler handler, Context context) {
//            super(handler);
//            this.context = context;
//        }
//
//        @Override
//        public void onChange(boolean selfChange) {
//            super.onChange(selfChange);
//            new LoadMovieAsync(context, (LoadMovieCallback) context).execute();
//
//
//        }
//    }
}

interface LoadMovieCallback {
    void preExecute();
    void postExecute(ArrayList<Movie> listMovie);
}
