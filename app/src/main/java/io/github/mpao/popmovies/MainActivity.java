package io.github.mpao.popmovies;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Arrays;
import java.util.List;
import io.github.mpao.popmovies.databinding.ActivityMainBinding;
import io.github.mpao.popmovies.network.MovieDeserializer;
import io.github.mpao.popmovies.network.MoviesApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainActivity;
    private int orderType = R.id.action_popular; //todo move in preference ?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // set the recyclerview
        int columns = getResources().getInteger(R.integer.activity_columns);
        RecyclerView.LayoutManager lm = new GridLayoutManager(this, columns);
        mainActivity.list.setLayoutManager(lm);
        mainActivity.list.setHasFixedSize(true);

        //get data and set the adapter
        mainActivity.refresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        mainActivity.refresh.setRefreshing(true);
        mainActivity.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MainActivity.this.getData();
            }
        });
        this.getData(); //todo save state of list, avoid network request

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // on menu selected item, change the order type and
        // request remote data accordingly.
        switch ( item.getItemId() ){
            case R.id.action_popular : orderType = R.id.action_popular; break;
            case R.id.action_rated   : orderType = R.id.action_rated; break;
        }
        mainActivity.refresh.setRefreshing(true);
        this.getData();
        return super.onOptionsItemSelected(item);

    }

    private void getData(){

        // prepare retrofit stuff
        Gson gson = new GsonBuilder().registerTypeAdapter(Movie[].class, new MovieDeserializer()).create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_baseurl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        MoviesApi api = retrofit.create(MoviesApi.class);

        // prepare the common Callback for both methods, popular and top rated movies
        Callback<Movie[]> callback = new Callback<Movie[]>() {
            @Override
            public void onResponse(Call<Movie[]> call, Response<Movie[]> response) {
                Movie[] data = response.body();
                if( data!=null ) {
                    MainActivity.this.setAdapter( Arrays.asList(data) );
                }
            }
            @Override
            public void onFailure(Call<Movie[]> call, Throwable t) {
                Toast.makeText(MainActivity.this, getString(R.string.network_error), Toast.LENGTH_LONG).show();
                MainActivity.this.setAdapter(null);
            }
        };

        // execute the right method according to the sorting preference
        if( orderType == R.id.action_popular) {
            api.getPopularMovies(getString(R.string.tmdb_key)).enqueue(callback);
        }else{
            api.getTopRatedMovies(getString(R.string.tmdb_key)).enqueue(callback);
        }

    }

    /**
     * Set the adapter for the API returned object and hide the
     * waiting loader
     * @param list Object to show in the adapter
     */
    private void setAdapter(List<Movie> list){

        MoviesAdapter adapter = new MoviesAdapter(list);
        mainActivity.list.setAdapter(adapter);
        mainActivity.refresh.setRefreshing(false);

    }

}
