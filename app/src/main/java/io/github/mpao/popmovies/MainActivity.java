package io.github.mpao.popmovies;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import io.github.mpao.popmovies.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mainActivity = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // set the recyclerview
        int columns = getResources().getInteger(R.integer.activity_columns);
        RecyclerView.LayoutManager lm = new GridLayoutManager(this, columns);
        mainActivity.list.setLayoutManager(lm);
        mainActivity.list.setHasFixedSize(true);

        //set adapter
        MockData data = new MockData();
        MoviesAdapter adapter = new MoviesAdapter( data.list );
        mainActivity.list.setAdapter( adapter );

    }

}
