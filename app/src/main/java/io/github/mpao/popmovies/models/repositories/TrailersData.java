package io.github.mpao.popmovies.models.repositories;

import android.arch.lifecycle.LiveData;
import java.util.List;
import io.github.mpao.popmovies.entities.Trailer;

public interface TrailersData {

    LiveData<List<Trailer>> get(int id);

}
