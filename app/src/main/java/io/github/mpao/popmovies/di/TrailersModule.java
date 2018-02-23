package io.github.mpao.popmovies.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import io.github.mpao.popmovies.models.repositories.TrailersData;
import io.github.mpao.popmovies.models.repositories.TrailersDataImpl;

@Module
public class TrailersModule {

    @Singleton
    @Provides
    public TrailersData provide(){
        return new TrailersDataImpl();
    }

}
