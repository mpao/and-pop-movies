package io.github.mpao.popmovies.ui;

import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.squareup.picasso.Picasso;
import io.github.mpao.popmovies.R;
import io.github.mpao.popmovies.databinding.DetailActivityBinding;
import io.github.mpao.popmovies.entities.Movie;
import io.github.mpao.popmovies.ui.adapters.DetailPagerAdapter;

public class DetailActivity extends AppCompatActivity {

    DetailActivityBinding detailActivity;
    protected Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        detailActivity = DataBindingUtil.setContentView(this, R.layout.detail_activity);

        // get intent information about the movie
        movie = getIntent().getParcelableExtra("movie"); //todo check null

        setUpToolbar(movie);
        setUpViewPager();

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

    /*
     * set the toolbar with the movie title and image
     */
    private void setUpToolbar(Movie movie){

        detailActivity.toolbar.setTitle( movie.getTitle() );
        setSupportActionBar( detailActivity.toolbar );
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String imageUrl = getString(R.string.api_imageurl).concat( movie.getPosterPath() );
        Picasso.with(this).load( imageUrl ).into(detailActivity.toolbarMovie);

    }

    /*
     * Set up the ViewPager with the sections adapter.
     */
    private void setUpViewPager(){

        DetailPagerAdapter pagerAdapter = new DetailPagerAdapter( getSupportFragmentManager() );
        detailActivity.viewpager.setAdapter(pagerAdapter);
        detailActivity.viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(detailActivity.tabs));
        detailActivity.tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(detailActivity.viewpager));

    }

}
