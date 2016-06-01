package com.example.lucarino.whattowatch.movies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lucarino.whattowatch.R;
import com.example.lucarino.whattowatch.data.Movies;
import com.example.lucarino.whattowatch.data.Result;
import com.example.lucarino.whattowatch.domain.EndlessScrollListener;
import com.example.lucarino.whattowatch.domain.MoviesAdapter;
import com.example.lucarino.whattowatch.moviedetail.MovieDetailActivity;
import com.example.lucarino.whattowatch.util.SpaceItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Displays a grid of movies {@link Result}.
 */
public class MoviesFragment extends Fragment implements MoviesContract.View {
    // b5c017a3b95c06a7e161e9b9ccd68aeb


    private MoviesContract.UserActionListener mActionsListener;

    private MoviesAdapter mAdapter;

    public static final String KEY_MOVIE_CLIKED = "movie.selected";
    public static final String KEY_SAVED_INSTANCE_DATA_SET = "movie.data.set";

    // Bind layout views
    @Bind(R.id.my_recycler_view)
    RecyclerView mRecyclerView;


    public MoviesFragment() {
        // Requires empty public constructor
    }

    ///////////////// Lifecycle //////////////////////////
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up grid adapter with an empty result and a click listener for movie items
        mAdapter = new MoviesAdapter(new Movies(new ArrayList<Result>()), mItemClickListener);
    }

    private boolean firstTime = true;
    @Override
    public void onResume() {
        super.onResume();
        // FIXME Check if one resume has been called before to just load the first movie pages the vey first time, I shouldn't be doing that.
        if(firstTime){
            // Call the presenter to load movies
            mActionsListener.loadMoreMovies(1);
            firstTime = false;
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Initialize Presenter, exposing this as the View for UI updates
        mActionsListener = new MoviesPresenter(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Set up butter knife for injecting views.
        View rootView = inflater.inflate(R.layout.fragment_main_discovery, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up GridLayout
        final int NUM_COLUMNS = 2;

        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), NUM_COLUMNS);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(0));
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnScrollListener(new EndlessScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page) {
                mActionsListener.loadMoreMovies(page);
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setProgressIndicator(boolean active) {
    }

    @Override
    public void showMovies(Movies movies) {
        mAdapter.replaceData(movies);
    }

    @Override
    public void showMovieDetailUi(String movieId) {

    }

    @Override
    public void updateSortOrder(int orderType) {
        mAdapter.sortBy(orderType);
    }

    @Override
    public void addMoviePage(List<Result> movies) {
        mAdapter.addData(movies);
    }

    private MoviesAdapter.OnItemClickListener mItemClickListener = new MoviesAdapter.OnItemClickListener() {
        @Override
        public void onClick(Result movie) {
            Intent mIntent = new Intent(getContext(), MovieDetailActivity.class);
            mIntent.putExtra(KEY_MOVIE_CLIKED, movie);
            startActivity(mIntent);
        }
    };
}
