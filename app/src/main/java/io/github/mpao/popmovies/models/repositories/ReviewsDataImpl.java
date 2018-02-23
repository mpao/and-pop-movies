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
import io.github.mpao.popmovies.entities.Review;
import io.github.mpao.popmovies.models.network.TheMovieDbApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsDataImpl implements ReviewsData {

    final private MutableLiveData<List<Review>> data = new MutableLiveData<>();
    @Inject Context context;
    @Inject @Named("reviews") TheMovieDbApi api;

    public ReviewsDataImpl(){
        App.graph.inject(this);
    }

    @Override
    public LiveData<List<Review>> get(int id) {

        String key = context.getString(R.string.tmdb_key);
        api.getReviews(id,key).enqueue(new Callback<Review[]>() {
            @Override
            public void onResponse(Call<Review[]> call, Response<Review[]> response) {
                Review[] array = response.body();
                if( array!=null ) {
                    data.setValue( Arrays.asList(array) );
                }
            }
            @Override
            public void onFailure(Call<Review[]> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;

    }

}
