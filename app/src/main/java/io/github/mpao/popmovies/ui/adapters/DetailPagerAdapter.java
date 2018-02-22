package io.github.mpao.popmovies.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import io.github.mpao.popmovies.ui.DetailInfoFragment;
import io.github.mpao.popmovies.ui.PlaceholderFragment;

public class DetailPagerAdapter extends FragmentPagerAdapter {

    public DetailPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position){
            case 0: fragment = new DetailInfoFragment(); break;
            case 1: fragment = new PlaceholderFragment(); break;
            case 2: fragment = new PlaceholderFragment(); break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
