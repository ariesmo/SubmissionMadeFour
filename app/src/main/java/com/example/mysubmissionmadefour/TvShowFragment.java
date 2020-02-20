package com.example.mysubmissionmadefour;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.mysubmissionmadefour.adapter.TvShowAdapter;
import com.example.mysubmissionmadefour.entity.TvShow;
import com.example.mysubmissionmadefour.viewModel.TvShowViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private TvShowAdapter adapter;
    private ProgressBar progressBar;
    private TvShowViewModel tvShowViewModel;


    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        RecyclerView recyclerView = view.findViewById(R.id.rv_tvshow);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TvShowAdapter(getContext());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvShowViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TvShowViewModel.class);

        if (tvShowViewModel.setTvShow()){
            progressBar.setVisibility(View.VISIBLE);
        }

        tvShowViewModel.getTvShows().observe(this, new Observer<ArrayList<TvShow>>() {
            @Override
            public void onChanged(ArrayList<TvShow> tvShows) {
                if (tvShows != null){
                    adapter.setListTvShow(tvShows);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
