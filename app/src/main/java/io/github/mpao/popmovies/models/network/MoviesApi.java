package io.github.mpao.popmovies.models.network;

import io.github.mpao.popmovies.entities.Movie;
import io.github.mpao.popmovies.entities.Trailer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesApi {

    // retrofit method for get the popular movies and return them in an array
    @GET("/3/movie/popular")
    Call<Movie[]> getPopularMovies( @Query("api_key") String key);

    // retrofit method for get top rated movies and return them in an array
    @GET("/3/movie/top_rated")
    Call<Movie[]> getTopRatedMovies( @Query("api_key") String key);

    // retrofit method for get trailers for the ID
    @GET("/3/movie/{id}/videos")
    Call<Trailer[]> getTrailers(@Path("id") int id, @Query("api_key") String key);

}
