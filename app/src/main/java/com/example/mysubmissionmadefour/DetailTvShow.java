package com.example.mysubmissionmadefour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mysubmissionmadefour.db.DatabaseContract;
import com.example.mysubmissionmadefour.entity.TvShow;
import com.example.mysubmissionmadefour.helper.MappingHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailTvShow extends AppCompatActivity {

    public static final String EXTRA_TVSHOWS = "extra_tvshows";

    ImageView imgPhoto;
    TextView tvName, tvRelease, tvDescription;

    FloatingActionButton fabTvShow;

    private TvShow tvShow, favTvShow;
    private boolean isFavorite = false;
    private Uri uriTvShow;

    public DetailTvShow(){ isFavorite = false; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        tvName          = findViewById(R.id.tv_item_name_tvshow);
        tvRelease       = findViewById(R.id.tv_item_release_tvshow);
        tvDescription   = findViewById(R.id.tv_item_description_tvshow);
        imgPhoto        = findViewById(R.id.img_item_photo_tvshow);
        fabTvShow        = findViewById(R.id.fab_fav_tvshow);

        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOWS);

        Cursor cursor;

        if (tvShow != null){
            tvName.setText(tvShow.getName());
            tvRelease.setText(tvShow.getRelease());
            tvDescription.setText(tvShow.getDescription());

            String url_image = "https://image.tmdb.org/t/p/w185"+tvShow.getPhoto();
            Glide.with(DetailTvShow.this).load(url_image).apply(new RequestOptions().override(350, 550)).into(imgPhoto);

            uriTvShow = Uri.parse(DatabaseContract.TvShowColumns.TVSHOW_CONTENT_URI + "/" + tvShow.getId());
            if (uriTvShow != null){
                cursor = getContentResolver().query(uriTvShow, null, null, null, null);
                Log.d("Uri", String.valueOf(cursor));
                if (cursor != null && cursor.moveToFirst()){
                    favTvShow = MappingHelper.mapCursorToObjectTvShow(cursor);
                    Log.d("Fav", String.valueOf(favTvShow));
                    cursor.close();
                }
            }

            if(favTvShow != null){
                if (tvShow.getId() == favTvShow.getId()){
                    isFavorite = true;
                } else {
                    isFavorite = false;
                }
            }

            fabTvShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFavorite){
                        getContentResolver().delete(uriTvShow, null, null);
                        isFavorite = false;

                    } else {
                        ContentValues values = new ContentValues();
                        values.put(DatabaseContract.TvShowColumns._ID, tvShow.getId());
                        values.put(DatabaseContract.TvShowColumns.PHOTO, tvShow.getPhoto());
                        values.put(DatabaseContract.TvShowColumns.NAME, tvShow.getName());
                        values.put(DatabaseContract.TvShowColumns.RELEASE, tvShow.getRelease());
                        values.put(DatabaseContract.TvShowColumns.DESCRIPTION, tvShow.getDescription());

                        getContentResolver().insert(DatabaseContract.TvShowColumns.TVSHOW_CONTENT_URI, values);
                        Toast.makeText(DetailTvShow.this, getResources().getString(R.string.name), Toast.LENGTH_SHORT).show();
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
