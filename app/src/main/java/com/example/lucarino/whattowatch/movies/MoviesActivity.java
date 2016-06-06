package com.example.lucarino.whattowatch.movies;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lucarino.whattowatch.R;
import com.example.lucarino.whattowatch.common.BaseActivity;
import com.example.lucarino.whattowatch.common.event.FilterAppliedEvent;
import com.example.lucarino.whattowatch.common.event.MovieSelectedEvent;
import com.example.lucarino.whattowatch.moviedetail.MovieDetailActivity;
import com.example.lucarino.whattowatch.moviedetail.MovieDetailFragment;

import rx.Subscriber;

import static com.example.lucarino.whattowatch.movies.MoviesFragment.KEY_MOVIE_CLIKED;


public class MoviesActivity extends BaseActivity {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, new MoviesFragment())
                .commit();


        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;

            if (savedInstanceState == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.movie_detail_container, new MovieDetailFragment())
                        .commit();
            }
        } else {
            mTwoPane = false;

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_discovery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.rb_most_popular:
                item.setChecked(!item.isChecked());
                mBus.post(new FilterAppliedEvent(FilterAppliedEvent.MOST_POPULAR_FILTER));
                return true;
            case R.id.rb_highest_rated:
                item.setChecked(!item.isChecked());
                mBus.post(new FilterAppliedEvent(FilterAppliedEvent.HIGHEST_RATED_FILTER));
                return true;
            case R.id.rb_favorites:
                item.setChecked(!item.isChecked());
                mBus.post(new FilterAppliedEvent(FilterAppliedEvent.FAVORITES_FILTER));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    protected Subscriber<? super Object> getSubscriber() {
        return new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                Log.d("TAG", "completed");

            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "error", e);

            }

            @Override
            public void onNext(Object o) {

                if(o instanceof MovieSelectedEvent) {
                    MovieSelectedEvent event = (MovieSelectedEvent) o;

                    if(mTwoPane) {

                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.movie_detail_container, MovieDetailFragment.newInstance(event.getmMovie()))
                                .commit();

                    } else {
                        Intent mIntent = new Intent(getApplicationContext(), MovieDetailActivity.class);
                        mIntent.putExtra(KEY_MOVIE_CLIKED, event.getmMovie());
                        startActivity(mIntent);
                    }

                }

            }
        };
    }
}
