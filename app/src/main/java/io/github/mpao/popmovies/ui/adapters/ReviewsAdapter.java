package io.github.mpao.popmovies.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;
import io.github.mpao.popmovies.databinding.ReviewBinding;
import io.github.mpao.popmovies.entities.Review;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder>{

    private final List<Review> list;
    private Context context;

    public ReviewsAdapter(List<Review> list){

        this.list = list;

    }

    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from( context );
        ReviewBinding bind = ReviewBinding.inflate( layoutInflater, parent, false);
        return new ViewHolder( bind );

    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.ViewHolder holder, int position) {

        Review review = list.get(position);
        holder.bind( review );

    }

    @Override
    public int getItemCount() {

        return list != null ? list.size() :  0;

    }

    /*
        ViewHolder class
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        private final ReviewBinding reviewBinding;

        private ViewHolder( ReviewBinding binding ){
            super( binding.getRoot() );
            this.reviewBinding = binding;
        }

        public void bind(final Review review){

            reviewBinding.setReview( review );
            reviewBinding.open.setOnClickListener( view ->{
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse( review.url ));
                context.startActivity(intent);
                //todo open instant browser
            });

        }

    }

}
