package io.github.mpao.popmovies.models.repositories;

import android.arch.lifecycle.LiveData;
import java.util.List;
import io.github.mpao.popmovies.entities.Review;

public interface ReviewsData {

    LiveData<List<Review>> get(int id);

}
