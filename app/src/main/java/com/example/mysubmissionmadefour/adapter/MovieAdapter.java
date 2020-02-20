package com.example.mysubmissionmadefour.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mysubmissionmadefour.DetailMovie;
import com.example.mysubmissionmadefour.R;
import com.example.mysubmissionmadefour.entity.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> listMovie = new ArrayList<>();
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Movie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie.clear();
        this.listMovie.addAll(listMovie);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        String url_image = "https://image.tmdb.org/t/p/w185"+listMovie.get(position).getPhoto();

        Glide.with(context).load(url_image).apply(new RequestOptions().override(350, 550)).into(holder.imgPhoto);
        holder.tvName.setText(listMovie.get(position).getName());
        holder.tvRelease.setText(listMovie.get(position).getRelease());
        holder.tvDescription.setText(listMovie.get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Test" + listMovie.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent detailIntent = new Intent(context, DetailMovie.class);
                detailIntent.putExtra(DetailMovie.EXTRA_MOVIES, listMovie.get(position));
                context.startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvRelease, tvDescription;
        ImageView imgPhoto;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName          = (TextView) itemView.findViewById(R.id.tv_item_name_movie);
            tvRelease       = (TextView) itemView.findViewById(R.id.tv_item_release_movie);
            tvDescription   = (TextView) itemView.findViewById(R.id.tv_item_description_movie);
            imgPhoto        = (ImageView) itemView.findViewById(R.id.img_item_photo_movie);
        }
    }
}
