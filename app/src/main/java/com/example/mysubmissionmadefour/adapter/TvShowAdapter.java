package com.example.mysubmissionmadefour.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mysubmissionmadefour.DetailMovie;
import com.example.mysubmissionmadefour.DetailTvShow;
import com.example.mysubmissionmadefour.R;
import com.example.mysubmissionmadefour.entity.TvShow;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {

    private Context context;
    private ArrayList<TvShow> listTvShow = new ArrayList<>();

    public TvShowAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<TvShow> getListTvShow() {
        return listTvShow;
    }

    public void setListTvShow(ArrayList<TvShow> listTvShow) {
        this.listTvShow.clear();
        this.listTvShow.addAll(listTvShow);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tvshow, parent, false);
        return new TvShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int position) {
        String url_image = "https://image.tmdb.org/t/p/w185"+listTvShow.get(position).getPhoto();

        Glide.with(context).load(url_image).apply(new RequestOptions().override(350, 550)).into(holder.imgPhoto);
        holder.tvName.setText(listTvShow.get(position).getName());
        holder.tvRelease.setText(listTvShow.get(position).getRelease());
        holder.tvDescription.setText(listTvShow.get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Test " + listTvShow.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent detailIntent = new Intent(context, DetailTvShow.class);
                detailIntent.putExtra(DetailTvShow.EXTRA_TVSHOWS, listTvShow.get(position));
                context.startActivity(detailIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listTvShow.size();
    }

    public class TvShowViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvRelease, tvDescription;
        ImageView imgPhoto;
        Button btnDetail;

        public TvShowViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName          = (TextView) itemView.findViewById(R.id.tv_item_name_tvshow);
            tvRelease       = (TextView) itemView.findViewById(R.id.tv_item_release_tvshow);
            tvDescription   = (TextView) itemView.findViewById(R.id.tv_item_description_tvshow);
            imgPhoto        = (ImageView) itemView.findViewById(R.id.img_item_photo_tvshow);
            btnDetail       = (Button) itemView.findViewById(R.id.btn_set_favorite_tvshow);
        }
    }
}
