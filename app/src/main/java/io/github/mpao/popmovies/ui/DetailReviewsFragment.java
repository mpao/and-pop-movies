package io.github.mpao.popmovies.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import io.github.mpao.popmovies.R;
import io.github.mpao.popmovies.databinding.DetailReviewFragmentBinding;
import io.github.mpao.popmovies.ui.adapters.ReviewsAdapter;
import io.github.mpao.popmovies.viewmodels.ReviewsViewModel;

public class DetailReviewsFragment extends Fragment {

    private DetailReviewFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate( inflater, R.layout.detail_review_fragment, container, false);
        int id = ((DetailActivity) getActivity()).movie.getId();

        // set up the recyclerview
        RecyclerView.LayoutManager lm = new LinearLayoutManager( getActivity() );
        binding.list.setLayoutManager(lm);
        binding.list.setHasFixedSize(true);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.item_decorator));
        binding.list.addItemDecoration(itemDecorator);

        // get data from viewmodel
        ReviewsViewModel viewModel = ViewModelProviders.of(this).get(ReviewsViewModel.class);
        if( savedInstanceState == null) {
            viewModel.init(id);
        }
        this.observeData(viewModel);

        // pull to refresh
        binding.refresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        binding.refresh.setRefreshing(true);
        binding.refresh.setOnRefreshListener(() -> {
            viewModel.init(id);
            this.observeData(viewModel);
        });

        return binding.getRoot();

    }

    private void observeData(ReviewsViewModel viewModel){

        viewModel.getData().observe(this, list ->{
            ReviewsAdapter adapter = new ReviewsAdapter( list );
            binding.list.setAdapter(adapter);
            binding.refresh.setRefreshing(false);
            //todo, messaggio visibility quando sparisce il loader
            if(list == null){
                Toast.makeText(getContext(), getString(R.string.network_error), Toast.LENGTH_LONG).show();
            }else{
                binding.setList(list);
            }
        });

    }

}
