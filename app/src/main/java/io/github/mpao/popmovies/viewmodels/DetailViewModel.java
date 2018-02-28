package io.github.mpao.popmovies.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import io.github.mpao.popmovies.models.repositories.FavoriteModel;

public class DetailViewModel extends ViewModel {

    private LiveData<Boolean> isFavorite;

    public void init(int id)  {

        this.isFavorite = new FavoriteModel(id).get();

    }

    public LiveData<Boolean> isFavorite() {

        return isFavorite;

    }

}
