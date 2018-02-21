package io.github.mpao.popmovies.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import io.github.mpao.popmovies.models.repositories.MoviesData;
import io.github.mpao.popmovies.models.repositories.MoviesDataImpl;

@Module
public class MoviesModule {

    // this is NOT a Singleton, every MoviesFragment instance has its own repository
    @Provides
    public MoviesData provide(){
        return new MoviesDataImpl();
    }

}
