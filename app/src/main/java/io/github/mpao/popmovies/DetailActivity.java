package io.github.mpao.popmovies;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import io.github.mpao.popmovies.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding detailActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        detailActivity = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        // get intent information about the movie
        Movie movie = getIntent().getParcelableExtra("movie"); //todo check null

        //set the toolbar with the movie title
        detailActivity.toolbar.setTitle( movie.getTitle() );
        setSupportActionBar( detailActivity.toolbar );
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // set bind for movie object
        detailActivity.setMovie(movie);
        String imageUrl = getString(R.string.api_imageurl).concat( movie.getPosterPath() );
        Picasso.with(this).load( imageUrl ).into(detailActivity.toolbarMovie);
        Picasso.with(this).load( imageUrl ).into(detailActivity.content.thumbnail);

    }

}
