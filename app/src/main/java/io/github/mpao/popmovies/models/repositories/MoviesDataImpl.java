package io.github.mpao.popmovies.models.repositories;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import io.github.mpao.popmovies.R;
import io.github.mpao.popmovies.di.App;
import io.github.mpao.popmovies.entities.Movie;
import io.github.mpao.popmovies.entities.MovieListType;
import io.github.mpao.popmovies.models.network.TheMovieDbApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static io.github.mpao.popmovies.models.database.AppContract.AppContractElement.CONTENT_URI;
import static io.github.mpao.popmovies.models.database.AppContract.AppContractElement.ID;
import static io.github.mpao.popmovies.models.database.AppContract.AppContractElement.OVERVIEW;
import static io.github.mpao.popmovies.models.database.AppContract.AppContractElement.POSTER_PATH;
import static io.github.mpao.popmovies.models.database.AppContract.AppContractElement.RELEASE_DATE;
import static io.github.mpao.popmovies.models.database.AppContract.AppContractElement.TITLE;
import static io.github.mpao.popmovies.models.database.AppContract.AppContractElement.VOTE_AVERAGE;

public class MoviesDataImpl implements MoviesData{

    final private MutableLiveData<List<Movie>> data = new MutableLiveData<>();
    @Inject Context context;
    @Inject @Named("movies") TheMovieDbApi api;

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
            case FAVORITES : this.getFavourites(); break;
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
                data.setValue(null);
            }
        };
    }

    private void getFavourites(){

        MyTask t = new MyTask();
        t.execute();

    }

    @SuppressLint("StaticFieldLeak")
    private class MyTask extends AsyncTask<Void, Void, List<Movie>>{
        @Override
        protected List<Movie> doInBackground(Void... voids) {

            Cursor cursor = context.getContentResolver().query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );

            ArrayList<Movie> list = new ArrayList<>();
            if(cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Movie movie = new Movie(
                            cursor.getInt(cursor.getColumnIndex(ID)),
                            cursor.getDouble(cursor.getColumnIndex(VOTE_AVERAGE)),
                            cursor.getString(cursor.getColumnIndex(TITLE)),
                            cursor.getString(cursor.getColumnIndex(POSTER_PATH)),
                            cursor.getString(cursor.getColumnIndex(OVERVIEW)),
                            cursor.getString(cursor.getColumnIndex(RELEASE_DATE))
                    );
                    list.add(movie);
                    cursor.moveToNext();
                }
                cursor.close();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<Movie> result) {
            data.setValue( result );
        }

    }

}
