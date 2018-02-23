package io.github.mpao.popmovies.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import io.github.mpao.popmovies.models.repositories.ReviewsData;
import io.github.mpao.popmovies.models.repositories.ReviewsDataImpl;

@Module
public class ReviewsModule {

    @Singleton
    @Provides
    public ReviewsData provide(){
        return new ReviewsDataImpl();
    }

}
