package io.github.mpao.popmovies.di;

import android.app.Application;
import android.content.Context;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private Context context;

    public ContextModule(Application application){
        this.context = application.getBaseContext();
    }

    @Singleton
    @Provides
    public Context provide(){
        return context;
    }

}
