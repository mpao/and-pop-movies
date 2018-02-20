package io.github.mpao.popmovies.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.github.mpao.popmovies.R;
import io.github.mpao.popmovies.databinding.MovieFragmentBinding;
import io.github.mpao.popmovies.entities.MovieListType;
import io.github.mpao.popmovies.ui.adapters.MoviesAdapter;
import io.github.mpao.popmovies.viewmodels.MoviesViewModel;

public class MoviesFragment extends Fragment {

    private MovieFragmentBinding binding;
    private static final String TYPE = "type";

    public MoviesFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance( MovieListType type ){

        Fragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putSerializable(TYPE, type);
        fragment.setArguments( args);
        return fragment;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        MovieListType type = (MovieListType) getArguments().getSerializable(TYPE);

        MoviesViewModel viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        viewModel.init( type );
        viewModel.getData().observe(this, list ->{
            if(list != null){
                MoviesAdapter adapter = new MoviesAdapter( list );
                binding.list.setAdapter(adapter);
                binding.refresh.setRefreshing(false);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate( inflater, R.layout.movie_fragment, container, false);
        int columns = getResources().getInteger(R.integer.activity_columns);

        RecyclerView.LayoutManager lm = new GridLayoutManager(getActivity(), columns);
        binding.list.setLayoutManager(lm);
        binding.list.setHasFixedSize(true);

        return binding.getRoot();
    }

}
