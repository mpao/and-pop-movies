package io.github.mpao.popmovies.models.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import io.github.mpao.popmovies.R;
import io.github.mpao.popmovies.di.App;
import io.github.mpao.popmovies.entities.Movie;
import io.github.mpao.popmovies.entities.MovieListType;
import io.github.mpao.popmovies.models.network.MoviesApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesDataImpl implements MoviesData{

    final private MutableLiveData<List<Movie>> data = new MutableLiveData<>();
    @Inject Context context;
    @Inject MoviesApi api;

    public MoviesDataImpl(){
        App.graph.inject(this);
    }

    @Override
    public LiveData<List<Movie>> get(MovieListType movieListType) {

        Callback<Movie[]> callback = callback();
        String key = context.getString(R.string.tmdb_key);

        switch (movieListType){
            case POPULAR   : api.getPopularMovies(key).enqueue(callback); break;
            case TOP_RATED : api.getTopRatedMovies(key).enqueue(callback); break;
            case FAVORITES : api.getPopularMovies(key).enqueue(callback); break;
        }
        return data;
    }

    private Callback<Movie[]> callback(){

        return new Callback<Movie[]>() {
            @Override
            public void onResponse(Call<Movie[]> call, Response<Movie[]> response) {
                Movie[] array = response.body();
                if( array!=null ) {
                    data.setValue( Arrays.asList(array) );
                }
            }
            @Override
            public void onFailure(Call<Movie[]> call, Throwable t) {
                //todo action on error
            }
        };
    }
}
