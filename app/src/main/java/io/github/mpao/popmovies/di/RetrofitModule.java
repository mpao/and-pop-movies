package io.github.mpao.popmovies.di;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import io.github.mpao.popmovies.R;
import io.github.mpao.popmovies.entities.Movie;
import io.github.mpao.popmovies.models.network.MovieDeserializer;
import io.github.mpao.popmovies.models.network.MoviesApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {

    private Context context;

    public RetrofitModule(Context context){
        this.context = context;
    }

    @Singleton
    @Provides
    public MoviesApi provide(){

        Gson gson = new GsonBuilder().registerTypeAdapter(Movie[].class, new MovieDeserializer()).create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_baseurl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(MoviesApi.class);

    }

}
