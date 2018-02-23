package io.github.mpao.popmovies.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import io.github.mpao.popmovies.R;
import io.github.mpao.popmovies.databinding.DetailInfoFragmentBinding;
import io.github.mpao.popmovies.viewmodels.TrailersViewModel;

public class DetailTrailersFragment extends Fragment {

    private DetailInfoFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate( inflater, R.layout.detail_info_fragment, container, false);
        DetailActivity activity = (DetailActivity) getActivity();

        // get data from viewmodel
        TrailersViewModel viewModel = ViewModelProviders.of(this).get(TrailersViewModel.class);
        viewModel.init( activity.movie.getId() );
        this.observeData(viewModel);

        return binding.getRoot();

    }

    private void observeData(TrailersViewModel viewModel){

        viewModel.getData().observe(this, list ->{
            if(list == null){
                Toast.makeText(getContext(), getString(R.string.network_error), Toast.LENGTH_LONG).show();
            }else{
                Log.d("TRAILERS", list.toString());
            }
        });

    }

}
