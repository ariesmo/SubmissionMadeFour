package com.example.mysubmissionmadefour.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mysubmissionmadefour.entity.TvShow;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvShowViewModel extends ViewModel {
    private static final String API_KEY = "05619450040812a2122e7541353fb8bb";
    private MutableLiveData<ArrayList<TvShow>> listTvShow = new MutableLiveData<>();

    public boolean setTvShow(){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++){
                        JSONObject tvShow = list.getJSONObject(i);
                        Log.d("Test: ", tvShow.getString("poster_path"));
                        TvShow tvShowItems = new TvShow();
                        tvShowItems.setId(tvShow.getInt("id"));
                        tvShowItems.setName(tvShow.getString("name"));
                        tvShowItems.setRelease(tvShow.getString("first_air_date"));
                        tvShowItems.setDescription(tvShow.getString("overview"));
                        tvShowItems.setPhoto(tvShow.getString("poster_path"));
                        listItems.add(tvShowItems);
                    }
                    listTvShow.postValue(listItems);
                } catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
        return false;
    }

    public LiveData<ArrayList<TvShow>> getTvShows(){
        return listTvShow;
    }
}
