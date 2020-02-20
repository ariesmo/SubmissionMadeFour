package com.example.mysubmissionmadefour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mysubmissionmadefour.db.DatabaseContract;
import com.example.mysubmissionmadefour.entity.Movie;
import com.example.mysubmissionmadefour.entity.TvShow;
import com.example.mysubmissionmadefour.helper.MappingHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailMovie extends AppCompatActivity {

    public static final String EXTRA_MOVIES = "extra_movies";

    ImageView imgPhoto;
    TextView tvName, tvRelease, tvDescription;
    FloatingActionButton fabMovie;

    private Movie movie, favMovie;
    private boolean isFavorite = false;

    private Uri uriMovie;

    public DetailMovie() {
        isFavorite = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        tvName          = findViewById(R.id.tv_item_name_movie);
        tvRelease       = findViewById(R.id.tv_item_release_movie);
        tvDescription   = findViewById(R.id.tv_item_description_movie);
        imgPhoto        = findViewById(R.id.img_item_photo_movie);
        fabMovie        = findViewById(R.id.fab_fav_movie);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIES);

        Cursor cursor;

        if (movie != null){
            tvName.setText(movie.getName());
            tvRelease.setText(movie.getRelease());
            tvDescription.setText(movie.getDescription());

            String url_image = "https://image.tmdb.org/t/p/w185"+movie.getPhoto();
            Glide.with(DetailMovie.this).load(url_image).apply(new RequestOptions().override(350, 550)).into(imgPhoto);

            uriMovie = Uri.parse(DatabaseContract.MovieColumns.MOVIE_CONTENT_URI + "/" + movie.getId());
            if (uriMovie != null){
                cursor = getContentResolver().query(uriMovie, null, null, null, null);
                Log.d("Uri", String.valueOf(cursor));
                if (cursor != null && cursor.moveToFirst()){
                    favMovie = MappingHelper.mapCursorToObjectMovie(cursor);
                    Log.d("Fav", String.valueOf(favMovie));
                    cursor.close();
                }
            }
            if (favMovie != null){
                if (movie.getId() == favMovie.getId()){
                    isFavorite = true;
                } else {
                    isFavorite = false;
                }
            }
            fabMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFavorite){
                        getContentResolver().delete(uriMovie, null, null);
                        isFavorite = false;
                    } else {
                        ContentValues values = new ContentValues();
                        values.put(DatabaseContract.MovieColumns._ID, movie.getId());
                        values.put(DatabaseContract.MovieColumns.PHOTO, movie.getPhoto());
                        values.put(DatabaseContract.MovieColumns.NAME, movie.getName());
                        values.put(DatabaseContract.MovieColumns.RELEASE, movie.getRelease());
                        values.put(DatabaseContract.MovieColumns.DESCRIPTION, movie.getDescription());

                        getContentResolver().insert(DatabaseContract.MovieColumns.MOVIE_CONTENT_URI, values);
                        Toast.makeText(DetailMovie.this, getResources().getString(R.string.name), Toast.LENGTH_SHORT).show();
                        isFavorite = true;
                        Log.d("msg", String.valueOf(values));
                    }

                }
            });
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(tvName.getText());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
