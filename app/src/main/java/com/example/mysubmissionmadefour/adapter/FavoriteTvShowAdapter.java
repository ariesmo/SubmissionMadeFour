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
import com.example.mysubmissionmadefour.DetailTvShow;
import com.example.mysubmissionmadefour.R;
import com.example.mysubmissionmadefour.entity.TvShow;

import java.util.ArrayList;

public class FavoriteTvShowAdapter extends RecyclerView.Adapter<FavoriteTvShowAdapter.FavoriteTvViewHolder> {

    private Context context;
    private ArrayList<TvShow> listFavTvShow = new ArrayList<>();

    public FavoriteTvShowAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<TvShow> getListFavTvShow() {
        return listFavTvShow;
    }

    public void setListFavTvShow(ArrayList<TvShow> listFavTvShow) {
        this.listFavTvShow.clear();
        this.listFavTvShow.addAll(listFavTvShow);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tvshow, parent, false);
        return new FavoriteTvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTvViewHolder holder, int position) {

        String url_image = "https://image.tmdb.org/t/p/w185"+listFavTvShow.get(position).getPhoto();
        Glide.with(context).load(url_image).apply(new RequestOptions().override(350, 550)).into(holder.imgPhoto);
        holder.tvName.setText(listFavTvShow.get(position).getName());
        holder.tvRelease.setText(listFavTvShow.get(position).getRelease());
        holder.tvDescription.setText(listFavTvShow.get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "TvShow" + listFavTvShow.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailTvShow.class);
                intent.putExtra(DetailTvShow.EXTRA_TVSHOWS, listFavTvShow.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listFavTvShow.size();
    }

    public class FavoriteTvViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvRelease, tvDescription;
        ImageView imgPhoto;

        public FavoriteTvViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName              = (TextView) itemView.findViewById(R.id.tv_item_name_tvshow);
            tvRelease           = (TextView) itemView.findViewById(R.id.tv_item_release_tvshow);
            tvDescription       = (TextView) itemView.findViewById(R.id.tv_item_description_tvshow);
            imgPhoto            = (ImageView) itemView.findViewById(R.id.img_item_photo_tvshow);
        }
    }
}
