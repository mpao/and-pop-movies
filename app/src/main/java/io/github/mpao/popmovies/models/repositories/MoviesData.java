package io.github.mpao.popmovies.models.repositories;

import android.arch.lifecycle.LiveData;
import java.util.List;
import io.github.mpao.popmovies.entities.Movie;
import io.github.mpao.popmovies.entities.MovieListType;

public interface MoviesData {

    LiveData<List<Movie>> get(MovieListType movieListType);

}
