package io.github.mpao.popmovies.di;

import javax.inject.Singleton;
import dagger.Component;
import io.github.mpao.popmovies.models.repositories.MoviesDataImpl;
import io.github.mpao.popmovies.viewmodels.MoviesViewModel;

@Singleton
@Component(modules = {
        ContextModule.class,
        MoviesModule.class,
        RetrofitModule.class
})

public interface Graph {

    void inject(MoviesViewModel moviesViewModel);
    void inject(MoviesDataImpl moviesData);

}
