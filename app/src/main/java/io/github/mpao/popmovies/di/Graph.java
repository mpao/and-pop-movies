package io.github.mpao.popmovies.di;

import javax.inject.Singleton;
import dagger.Component;
import io.github.mpao.popmovies.models.repositories.MoviesDataImpl;
import io.github.mpao.popmovies.models.repositories.TrailersDataImpl;
import io.github.mpao.popmovies.viewmodels.MoviesViewModel;
import io.github.mpao.popmovies.viewmodels.TrailersViewModel;

@Singleton
@Component(modules = {
        ContextModule.class,
        MoviesModule.class,
        RetrofitModule.class,
        TrailersModule.class
})

public interface Graph {

    void inject(MoviesViewModel moviesViewModel);
    void inject(MoviesDataImpl moviesData);
    void inject(TrailersViewModel trailerViewModel);
    void inject(TrailersDataImpl trailerData);
}
