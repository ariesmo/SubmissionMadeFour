package com.example.mysubmissionmadefour.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mysubmissionmadefour.FavoriteMovie;
import com.example.mysubmissionmadefour.FavoriteTvShow;
import com.example.mysubmissionmadefour.R;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mcontext;

    public SectionsPagerAdapter(@NonNull FragmentManager fm, Context mcontext) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mcontext = mcontext;
    }

    private final int[] TAB_TITLES = new int[]{
        R.string.tab_text_1, R.string.tab_text_2
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new FavoriteMovie();
                break;
            case 1:
                fragment = new FavoriteTvShow();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mcontext.getResources().getString(TAB_TITLES[position]);
    }
}
