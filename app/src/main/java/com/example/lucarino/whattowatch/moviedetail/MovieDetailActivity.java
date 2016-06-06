package com.example.lucarino.whattowatch.moviedetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lucarino.whattowatch.R;
import com.example.lucarino.whattowatch.common.BaseActivity;
import com.example.lucarino.whattowatch.data.Result;

import rx.Subscriber;

public class MovieDetailActivity extends BaseActivity {

    private static final String KEY_MOVIE_CLICKED = "movie.selected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent mIntent = getIntent();

        if (mIntent.hasExtra(KEY_MOVIE_CLICKED)) {

            Result result = (Result) mIntent.getSerializableExtra(KEY_MOVIE_CLICKED);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, MovieDetailFragment.newInstance(result))
                    .commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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


        return super.onOptionsItemSelected(item);
    }


    @Override
    protected Subscriber<? super Object> getSubscriber() {
        return new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        };
    }
}
