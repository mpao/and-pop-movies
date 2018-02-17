package io.github.mpao.popmovies.network;

import io.github.mpao.popmovies.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesApi {

    // retrofit method for get the popular movies and return them in an array
    @GET("/3/movie/popular")
    Call<Movie[]> getPopularMovies( @Query("api_key") String key);

    // retrofit method for get top rated movies and return them in an array
    @GET("/3/movie/top_rated")
    Call<Movie[]> getTopRatedMovies( @Query("api_key") String key);

}