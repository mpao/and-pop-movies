package io.github.mpao.popmovies.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import io.github.mpao.popmovies.R;
import io.github.mpao.popmovies.databinding.DetailActivityBinding;
import io.github.mpao.popmovies.entities.Movie;
import io.github.mpao.popmovies.models.database.AppContract;
import io.github.mpao.popmovies.ui.adapters.DetailPagerAdapter;
import io.github.mpao.popmovies.viewmodels.DetailViewModel;

public class DetailActivity extends AppCompatActivity {

    DetailActivityBinding detailActivity;
    protected Movie movie;
    private boolean isAlreadyFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        detailActivity = DataBindingUtil.setContentView(this, R.layout.detail_activity);

        // get intent information about the movie
        movie = getIntent().getParcelableExtra("movie"); //todo check null

        DetailViewModel viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        viewModel.init(movie.getId());

        viewModel.isFavorite().observe(this, exists -> {
            if(exists != null){
                setFavoriteIcon(exists);
            }
        });

        setUpToolbar(movie);
        setUpViewPager();

        detailActivity.fab.setOnClickListener( v -> {

            String message;
            if(isAlreadyFavorite){
                Uri uri = AppContract.AppContractElement.CONTENT_URI;
                String id = String.valueOf( movie.getId() );
                uri = uri.buildUpon().appendPath(id).build();
                getContentResolver().delete(uri, null,null);
                setFavoriteIcon(false);
                message = getString(R.string.remove_favorite);
            }else {
                ContentValues values = new ContentValues();
                values.put(AppContract.AppContractElement.ID, movie.getId());
                values.put(AppContract.AppContractElement.VOTE_AVERAGE, movie.getVoteAverage());
                values.put(AppContract.AppContractElement.TITLE, movie.getTitle());
                values.put(AppContract.AppContractElement.POSTER_PATH, movie.getPosterPath());
                values.put(AppContract.AppContractElement.OVERVIEW, movie.getOverview());
                values.put(AppContract.AppContractElement.RELEASE_DATE, movie.getReleaseDate());
                getContentResolver().insert(AppContract.AppContractElement.CONTENT_URI, values);
                setFavoriteIcon(true);
                message = getString(R.string.add_favorite);
            }

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        });

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

    /*
     * Change fab icon.
     * i was not able to use the databind with srcCompat in the fab, let do this with code
     */
    private void setFavoriteIcon(boolean state){

        isAlreadyFavorite = state;
        int icon = state ? R.drawable.ic_favorite : R.drawable.ic_favorite_border;
        detailActivity.fab.setImageDrawable(ContextCompat.getDrawable(this, icon));

    }

}
