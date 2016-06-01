package com.example.lucarino.whattowatch.moviedetail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lucarino.whattowatch.R;
import com.example.lucarino.whattowatch.data.Result;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String KEY_MOVIE_CLICKED = "movie.selected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent mIntent = getIntent();

        if (mIntent.hasExtra(KEY_MOVIE_CLICKED)) {

            Result result = (Result) mIntent.getSerializableExtra(KEY_MOVIE_CLICKED);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_detail_container, MovieDetailFragment.newInstance(result))
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
