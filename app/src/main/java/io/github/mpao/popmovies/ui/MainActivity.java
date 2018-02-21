package io.github.mpao.popmovies.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MainActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        if(savedInstanceState == null) {
            insertFragment(DEFAULT);
        }
        // set up the bottom menu navigation
        binding.navigation.setOnNavigationItemSelectedListener( item -> {
            switch (item.getItemId()) {
                case R.id.navigation_popular:   insertFragment( MovieListType.POPULAR ); return true;
                case R.id.navigation_top_rated: insertFragment( MovieListType.TOP_RATED ); return true;
                case R.id.navigation_favorites: insertFragment( MovieListType.FAVORITES ); return true;
            }
            return false;
        });

    }

    /* NO Java-doc
     * Add a new fragment and hide the other. Using hide/show instead of replace method.
     * This method allows to keep the data and the position of the recyclerview even at the fast change of
     * fragment with the bottom navigation tab.
     * I dont add all the fragments on activity creation, but only if the user request it, just for
     * saving resources and data network
     */
    private void insertFragment(MovieListType type){

        // scan the enum for values
        for ( MovieListType t: MovieListType.values()) {
            Fragment fragment = searchForReusableFragment(t);
            if( t!= type)
                fm.beginTransaction().hide(fragment).commit();
            else{
                if( !fragment.isAdded() ){
                    fm.beginTransaction().add(R.id.container, fragment, type.getValue()).commit();
                }
                fm.beginTransaction().show(fragment).commit();
            }
        }

    }

    /* NO Java-doc
     * Fragments are created only if a previous one of the same type doesnt exist
     */
    private Fragment searchForReusableFragment(MovieListType type){

        return fm.findFragmentByTag( type.getValue() ) == null ?
                MoviesFragment.newInstance(type) : fm.findFragmentByTag( type.getValue() );

    }

}
