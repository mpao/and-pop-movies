package io.github.mpao.popmovies.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import java.util.List;
import javax.inject.Inject;
import io.github.mpao.popmovies.di.App;
import io.github.mpao.popmovies.entities.Movie;
import io.github.mpao.popmovies.entities.MovieListType;
import io.github.mpao.popmovies.models.repositories.MoviesData;

public class MoviesViewModel extends ViewModel{

    private LiveData<List<Movie>> data;
    @Inject MoviesData repo;

    public void init(MovieListType movieListType)  {

        App.graph.inject(this);
        this.data = repo.get(movieListType);

    }

    public LiveData<List<Movie>> getData() {

        return this.data;

    }

}
