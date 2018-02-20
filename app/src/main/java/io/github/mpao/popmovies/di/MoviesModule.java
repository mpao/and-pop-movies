package io.github.mpao.popmovies.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import io.github.mpao.popmovies.models.repositories.MoviesData;
import io.github.mpao.popmovies.models.repositories.MoviesDataImpl;

@Module
public class MoviesModule {

    @Singleton
    @Provides
    public MoviesData provide(){
        return new MoviesDataImpl();
    }

}
