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

import com.example.mysubmissionmadefour.adapter.FavoriteTvShowAdapter;
import com.example.mysubmissionmadefour.db.DatabaseContract;
import com.example.mysubmissionmadefour.db.TvShowHelper;
import com.example.mysubmissionmadefour.entity.TvShow;
import com.example.mysubmissionmadefour.helper.MappingHelper;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FavoriteTvShow extends Fragment implements LoadTvShowCallback {

    private static final String EXTRA_STATE_TVSHOW = "extra_state_tvshow";

    private FavoriteTvShowAdapter adapter;
    private ProgressBar progressBar;
    private RecyclerView rvTvShows;
    private TvShowHelper tvShowHelper;
    ArrayList<TvShow> listTvShow = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        rvTvShows   = view.findViewById(R.id.rv_tvshow);
        rvTvShows.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTvShows.setHasFixedSize(true);

        adapter     = new FavoriteTvShowAdapter(getContext());
        adapter.notifyDataSetChanged();
        rvTvShows.setAdapter(adapter);

        HandlerThread thread = new HandlerThread("TvShowObserver");
        thread.start();

        Handler handler = new Handler(thread.getLooper());
        TvShowObserver observer = new TvShowObserver(handler, getContext());
        getActivity().getApplicationContext().getContentResolver().registerContentObserver(DatabaseContract.TvShowColumns.TVSHOW_CONTENT_URI, true, observer);


        if (savedInstanceState == null){
            new LoadTvShowAsync(getContext(), this).execute();
        } else {
            ArrayList<TvShow> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE_TVSHOW);
            if (list != null){
                adapter.setListFavTvShow(listTvShow);
            }
        }
        return view;
    }

    private void showSnackbarMessage(String message){
        Snackbar.make(rvTvShows, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvShowHelper.close();
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
    public void postExecute(ArrayList<TvShow> listTvShow) {
        progressBar.setVisibility(View.INVISIBLE);
        if (listTvShow.size() > 0){
            adapter.setListFavTvShow(listTvShow);
        } else {
            adapter.setListFavTvShow(new ArrayList<TvShow>());
            showSnackbarMessage("Tidak ada data saat ini");
        }
    }

    private static class LoadTvShowAsync extends AsyncTask<Void, Void, ArrayList<TvShow>> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadTvShowCallback> weakCallback;

        private LoadTvShowAsync(Context context, LoadTvShowCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<TvShow> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor cursor = context.getContentResolver().query(DatabaseContract.TvShowColumns.TVSHOW_CONTENT_URI, null, null, null, null);
            Log.d("cursor", String.valueOf(cursor));
            return MappingHelper.mapCursorToArrayListTvShow(cursor);
        }

        @Override
        protected void onPostExecute(ArrayList<TvShow> list) {
            super.onPostExecute(list);
            weakCallback.get().postExecute(list);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE_TVSHOW, adapter.getListFavTvShow());
    }

    public static class TvShowObserver extends ContentObserver {
        final Context context;

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public TvShowObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadTvShowAsync(context, (LoadTvShowCallback) context).execute();
        }
    }
}

interface LoadTvShowCallback {
    void preExecute();
    void postExecute(ArrayList<TvShow> listTvShow);
}
