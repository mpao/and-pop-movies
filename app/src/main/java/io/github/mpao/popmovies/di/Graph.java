package io.github.mpao.popmovies.di;

import javax.inject.Singleton;
import dagger.Component;
import io.github.mpao.popmovies.models.repositories.MoviesDataImpl;
import io.github.mpao.popmovies.models.repositories.TrailerDataImpl;
import io.github.mpao.popmovies.viewmodels.MoviesViewModel;
import io.github.mpao.popmovies.viewmodels.TrailerViewModel;

@Singleton
@Component(modules = {
        ContextModule.class,
        MoviesModule.class,
        RetrofitModule.class,
        TrailerModule.class
})

public interface Graph {

    void inject(MoviesViewModel moviesViewModel);
    void inject(MoviesDataImpl moviesData);
    void inject(TrailerViewModel trailerViewModel);
    void inject(TrailerDataImpl trailerData);
}
