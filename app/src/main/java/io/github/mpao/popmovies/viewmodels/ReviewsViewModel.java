package io.github.mpao.popmovies.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import java.util.List;
import javax.inject.Inject;
import io.github.mpao.popmovies.di.App;
import io.github.mpao.popmovies.entities.Review;
import io.github.mpao.popmovies.models.repositories.ReviewsData;

public class ReviewsViewModel extends ViewModel {

    private LiveData<List<Review>> data;
    @Inject ReviewsData repo;

    public void init(int id)  {

        App.graph.inject(this);
        this.data = repo.get(id);

    }

    public LiveData<List<Review>> getData() {

        return this.data;

    }

}
