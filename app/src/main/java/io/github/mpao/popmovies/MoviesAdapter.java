package io.github.mpao.popmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import java.util.List;
import io.github.mpao.popmovies.databinding.PosterBinding;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private final List<Movie> list;
    private Context context;

    public MoviesAdapter(List<Movie> list){

        this.list = list;

    }

    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from( context );
        PosterBinding bind = PosterBinding.inflate( layoutInflater, parent, false);
        return new ViewHolder( bind );

    }

    @Override
    public void onBindViewHolder(MoviesAdapter.ViewHolder holder, int position) {

        Movie movie = list.get(position);
        holder.bind( movie );

    }

    @Override
    public int getItemCount() {

        return list != null ? list.size() :  0;

    }

    /*
    ViewHolder class
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        private final PosterBinding posterVIew;

        private ViewHolder( PosterBinding binding ){
            super( binding.getRoot() );
            this.posterVIew = binding;
        }

        public void bind(final Movie movie){
            posterVIew.setPoster( movie );
            String imageUrl = context.getString(R.string.api_imageurl).concat( movie.getPosterPath() );
            Picasso.with(context).load( imageUrl ).into(posterVIew.posterImage);
            posterVIew.posterImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("movie", movie);
                    context.startActivity(intent);
                }
            });
            posterVIew.executePendingBindings();
        }

    }

}
