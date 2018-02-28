package io.github.mpao.popmovies.models.repositories;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import javax.inject.Inject;
import io.github.mpao.popmovies.di.App;
import static io.github.mpao.popmovies.models.database.AppContract.AppContractElement.CONTENT_URI;

public class FavoriteModel {

    @Inject Context context;
    final private MutableLiveData<Boolean> data = new MutableLiveData<>();

    @SuppressLint("StaticFieldLeak")
    public FavoriteModel(int id){

        App.graph.inject(this);
        data.setValue( false );
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {

                boolean exists = false;
                Cursor cursor = context.getContentResolver().query(
                        CONTENT_URI,
                        null,
                        "id="+id,
                        null,
                        null
                );

                if(cursor != null){
                    exists = cursor.getCount() > 0;
                    cursor.close();
                }
                return exists;
            }
            @Override
            protected void onPostExecute(Boolean result) {
                data.setValue( result );
            }
        }.execute();

    }

    public LiveData<Boolean> get(){
        return data;
    }

}
