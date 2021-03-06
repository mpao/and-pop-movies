package io.github.mpao.popmovies.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import io.github.mpao.popmovies.R;
import io.github.mpao.popmovies.databinding.DetailInfoFragmentBinding;

public class DetailInfoFragment extends Fragment {

    private DetailInfoFragmentBinding binding;
    public DetailInfoFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate( inflater, R.layout.detail_info_fragment, container, false);
        DetailActivity activity = (DetailActivity) getActivity();

        binding.setMovie(activity.movie);

        String imageUrl = getString(R.string.api_imageurl).concat( activity.movie.getPosterPath() );
        Picasso.with( getActivity() ).load( imageUrl ).into(binding.thumbnail);
        return binding.getRoot();

    }

}
