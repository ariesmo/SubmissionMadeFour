package com.example.mysubmissionmadefour.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mysubmissionmadefour.DetailMovie;
import com.example.mysubmissionmadefour.R;
import com.example.mysubmissionmadefour.entity.Movie;

import java.util.ArrayList;

public class FavoriteMovieAdapter  extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder> {

    private Context context;
    private ArrayList<Movie> listFavMovie = new ArrayList<>();

    public FavoriteMovieAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Movie> getListFavMovie() {
        return listFavMovie;
    }

    public void setListFavMovie(ArrayList<Movie> listFavMovie) {
        this.listFavMovie.clear();
        this.listFavMovie.addAll(listFavMovie);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new FavoriteMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieViewHolder holder, int position) {
        String url_image = "https://image.tmdb.org/t/p/w185"+listFavMovie.get(position).getPhoto();

        Glide.with(context).load(url_image).apply(new RequestOptions().override(350, 550)).into(holder.imgPhoto);
        holder.tvName.setText(listFavMovie.get(position).getName());
        holder.tvRelease.setText(listFavMovie.get(position).getRelease());
        holder.tvDescription.setText(listFavMovie.get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Test" + listFavMovie.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailMovie.class);
                intent.putExtra(DetailMovie.EXTRA_MOVIES, listFavMovie.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFavMovie.size();
    }

    public class FavoriteMovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvRelease, tvDescription;
        ImageView imgPhoto;

        public FavoriteMovieViewHolder(View itemView) {
            super(itemView);

            tvName              = (TextView) itemView.findViewById(R.id.tv_item_name_movie);
            tvRelease           = (TextView) itemView.findViewById(R.id.tv_item_release_movie);
            tvDescription       = (TextView) itemView.findViewById(R.id.tv_item_description_movie);
            imgPhoto            = (ImageView) itemView.findViewById(R.id.img_item_photo_movie);
        }
    }
}
