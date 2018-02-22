package io.github.mpao.popmovies.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.squareup.picasso.Picasso;
import io.github.mpao.popmovies.R;
import io.github.mpao.popmovies.databinding.DetailActivityBinding;
import io.github.mpao.popmovies.entities.Movie;

public class DetailActivity extends AppCompatActivity {

    DetailActivityBinding detailActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        detailActivity = DataBindingUtil.setContentView(this, R.layout.detail_activity);

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

    /**
     * I override the setDisplayHomeAsUpEnabled in the toolbar because I dont want the "Up behavior" but
     * the "Back" one. In this way, I can return on the recyclerview with the transitionName animation.
     * @param item selected menu item
     * @return boolean state
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
