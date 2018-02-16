package io.github.mpao.popmovies;

import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Arrays;
import java.util.List;

import io.github.mpao.popmovies.databinding.ActivityMainBinding;
import io.github.mpao.popmovies.network.Config;
import io.github.mpao.popmovies.network.MovieDeserializer;
import io.github.mpao.popmovies.network.MoviesApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainActivity;

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
        this.getData();

    }

    private void getData(){

        Gson gson = new GsonBuilder().registerTypeAdapter(Movie[].class, new MovieDeserializer()).create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        MoviesApi api = retrofit.create(MoviesApi.class);
        api.getPopularMovies( getString(R.string.tmdb_key) ).enqueue(new Callback<Movie[]>() {
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

        });

    }

    private void setAdapter(List<Movie> list){

        MoviesAdapter adapter = new MoviesAdapter(list);
        mainActivity.list.setAdapter(adapter);
        mainActivity.refresh.setRefreshing(false);

    }

}
