package io.github.mpao.popmovies.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import io.github.mpao.popmovies.R;
import io.github.mpao.popmovies.databinding.MovieFragmentBinding;
import io.github.mpao.popmovies.entities.MovieListType;
import io.github.mpao.popmovies.ui.adapters.MoviesAdapter;
import io.github.mpao.popmovies.viewmodels.MoviesViewModel;

public class MoviesFragment extends Fragment {

    private MovieFragmentBinding binding;
    private MoviesViewModel viewModel;

    public MoviesFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance( MovieListType type ){

        Fragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putSerializable("type", type);
        fragment.setArguments( args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate( inflater, R.layout.movie_fragment, container, false);
        int columns = getResources().getInteger(R.integer.activity_columns);

        RecyclerView.LayoutManager lm = new GridLayoutManager(getActivity(), columns);
        binding.list.setLayoutManager(lm);
        binding.list.setHasFixedSize(true);

        // get data from viewmodel
        MovieListType type = (MovieListType) getArguments().getSerializable("type");
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        if( savedInstanceState == null) {
            viewModel.init(type);
        }
        this.observeData(viewModel);

        // pull to refresh
        binding.refresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        binding.refresh.setRefreshing(true);
        binding.refresh.setOnRefreshListener(() -> {
            viewModel.init(type);
            this.observeData(viewModel);
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {

        super.onResume();
        MovieListType type = (MovieListType) getArguments().getSerializable("type");
        // refresh the list only for favorite: if I add or remove a favorite, when I
        // return to the favs list, this has to be updated.
        if(type == MovieListType.FAVORITES) {
            viewModel.init(type);
            this.observeData(viewModel);
        }

    }

    private void observeData(MoviesViewModel viewModel){

        viewModel.getData().observe(this, list ->{
            MoviesAdapter adapter = new MoviesAdapter( list );
            binding.list.setAdapter(adapter);
            binding.refresh.setRefreshing(false);
            if(list == null){
                Toast.makeText(getContext(), getString(R.string.network_error), Toast.LENGTH_LONG).show();
            }
        });

    }

}
