package io.github.mpao.popmovies.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import java.util.List;
import javax.inject.Inject;
import io.github.mpao.popmovies.di.App;
import io.github.mpao.popmovies.entities.Trailer;
import io.github.mpao.popmovies.models.repositories.TrailersData;

public class TrailersViewModel extends ViewModel{

    private LiveData<List<Trailer>> data;
    @Inject
    TrailersData repo;

    public void init(int id)  {

        App.graph.inject(this);
        this.data = repo.get(id);

    }

    public LiveData<List<Trailer>> getData() {

        return this.data;

    }

}
