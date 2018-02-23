package io.github.mpao.popmovies.di;

import android.app.Application;

public class App extends Application {

    public static Graph graph;

    @Override
    public void onCreate(){

        super.onCreate();
        graph = DaggerGraph.builder()
                .moviesModule( new MoviesModule() )
                .trailersModule(new TrailersModule())
                .reviewsModule(new ReviewsModule())
                .contextModule( new ContextModule(this) )
                .retrofitModule( new RetrofitModule(this.getApplicationContext()) )
                .build();

    }

}
