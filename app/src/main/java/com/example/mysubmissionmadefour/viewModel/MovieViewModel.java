package com.example.mysubmissionmadefour.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mysubmissionmadefour.entity.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieViewModel extends ViewModel {
    private static final String API_KEY = "05619450040812a2122e7541353fb8bb";
    private MutableLiveData<ArrayList<Movie>> listMovie = new MutableLiveData<>();

    public boolean setMovie(){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++){
                        JSONObject movie = list.getJSONObject(i);
                        Log.d("Test: ", movie.getString("poster_path"));
                        Movie movieItems = new Movie();
                        movieItems.setId(movie.getInt("id"));
                        movieItems.setName(movie.getString("original_title"));
                        movieItems.setRelease(movie.getString("release_date"));
                        movieItems.setDescription(movie.getString("overview"));
                        movieItems.setPhoto(movie.getString("poster_path"));
                        listItems.add(movieItems);
                    }
                    listMovie.postValue(listItems);
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

    public LiveData<ArrayList<Movie>> getMovies(){
        return listMovie;
    }
}
