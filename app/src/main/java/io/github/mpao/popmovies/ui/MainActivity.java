package io.github.mpao.popmovies.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import io.github.mpao.popmovies.R;
import io.github.mpao.popmovies.databinding.MainActivityBinding;
import io.github.mpao.popmovies.entities.MovieListType;

/**
 * Pop Movies launcher activity
 */
public class MainActivity extends AppCompatActivity {

    // define which fragment will be open in onCreate method
    private final MovieListType DEFAULT = MovieListType.POPULAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MainActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        insertFragment( DEFAULT ); //todo rotation state, recyclerview state

        // set up the bottom menu navigation
        binding.navigation.setOnNavigationItemSelectedListener( item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:          insertFragment( MovieListType.POPULAR );   return true;
                case R.id.navigation_dashboard:     insertFragment( MovieListType.TOP_RATED ); return true;
                case R.id.navigation_notifications: insertFragment( MovieListType.FAVORITES ); return true;
            }
            return false;
        });

    }

    /**
     * Insert a fragment inside the container view of the activity
     * @param type define the type of the movie list showed in the fragment
     */
    private void insertFragment(MovieListType type){

        Fragment fragment = MoviesFragment.newInstance(type);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

    }

}
