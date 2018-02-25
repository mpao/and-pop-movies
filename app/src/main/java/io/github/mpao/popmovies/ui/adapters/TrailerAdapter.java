package io.github.mpao.popmovies.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import java.util.List;
import io.github.mpao.popmovies.R;
import io.github.mpao.popmovies.databinding.TrailerBinding;
import io.github.mpao.popmovies.entities.Trailer;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder>{

    private final List<Trailer> list;
    private Context context;

    public TrailerAdapter(List<Trailer> list){

        this.list = list;

    }

    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from( context );
        TrailerBinding bind = TrailerBinding.inflate( layoutInflater, parent, false);
        return new ViewHolder( bind );

    }

    @Override
    public void onBindViewHolder(TrailerAdapter.ViewHolder holder, int position) {

        Trailer trailer = list.get(position);
        holder.bind( trailer );

    }

    @Override
    public int getItemCount() {

        return list != null ? list.size() :  0;

    }

    /*
    ViewHolder class
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        private final TrailerBinding trailerBinding;

        private ViewHolder( TrailerBinding binding ){
            super( binding.getRoot() );
            this.trailerBinding = binding;
        }

        public void bind(final Trailer trailer){

            trailerBinding.setTrailer( trailer );
            String imageUrl = context.getString(R.string.preview_url, trailer.key);
            Picasso.with(context).load( imageUrl ).into( trailerBinding.background);

            trailerBinding.preview.setOnClickListener( view ->{
                String url    = context.getString(R.string.watch_url, trailer.key);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
            });

        }

    }
}
