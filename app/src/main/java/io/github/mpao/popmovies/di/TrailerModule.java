package io.github.mpao.popmovies.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import io.github.mpao.popmovies.models.repositories.TrailerData;
import io.github.mpao.popmovies.models.repositories.TrailerDataImpl;

@Module
public class TrailerModule {

    @Singleton
    @Provides
    public TrailerData provide(){
        return new TrailerDataImpl();
    }

}
