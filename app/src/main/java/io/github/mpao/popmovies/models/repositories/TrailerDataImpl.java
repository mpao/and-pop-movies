package io.github.mpao.popmovies.models.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import io.github.mpao.popmovies.R;
import io.github.mpao.popmovies.di.App;
import io.github.mpao.popmovies.entities.Trailer;
import io.github.mpao.popmovies.models.network.MoviesApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrailerDataImpl implements TrailerData {

    final private MutableLiveData<List<Trailer>> data = new MutableLiveData<>();
    @Inject Context context;
    @Inject @Named("trailers") MoviesApi api;

    public TrailerDataImpl(){
        App.graph.inject(this);
    }

    @Override
    public LiveData<List<Trailer>> get(int id) {

        String key = context.getString(R.string.tmdb_key);
        api.getTrailers(id,key).enqueue(new Callback<Trailer[]>() {
            @Override
            public void onResponse(Call<Trailer[]> call, Response<Trailer[]> response) {
                Trailer[] array = response.body();
                if( array!=null ) {
                    data.setValue( Arrays.asList(array) );
                }
            }
            @Override
            public void onFailure(Call<Trailer[]> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;

    }

}
