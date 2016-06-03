package com.example.lucarino.whattowatch.movies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lucarino.whattowatch.R;
import com.example.lucarino.whattowatch.data.FavMoviesContract;
import com.example.lucarino.whattowatch.data.Movies;
import com.example.lucarino.whattowatch.data.Result;
import com.example.lucarino.whattowatch.domain.EndlessScrollListener;
import com.example.lucarino.whattowatch.domain.MyListCursorAdapter;
import com.example.lucarino.whattowatch.moviedetail.MovieDetailActivity;

import java.util.List;
import java.util.Vector;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Displays a grid of movies {@link Result}.
 */
public class MoviesFragment extends Fragment implements MoviesContract.View {
    // b5c017a3b95c06a7e161e9b9ccd68aeb
    private MoviesContract.UserActionListener mActionsListener;
    public static final String KEY_MOVIE_CLIKED = "movie.selected";
    // Bind layout views
    @Bind(R.id.my_recycler_view)
    RecyclerView mRecyclerView;

    MyListCursorAdapter myListCursorAdapter;


    public MoviesFragment() {
        // Requires empty public constructor
    }

    ///////////////// Lifecycle //////////////////////////
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up grid adapter with an empty result and a click listener for movie items
       // mAdapter = new MoviesAdapter(new Movies(new ArrayList<Result>()), mItemClickListener);
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

        // Test the basic content provider query
        Cursor weatherCursor = getContext().getContentResolver().query(
                FavMoviesContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        myListCursorAdapter = new MyListCursorAdapter(getContext(), weatherCursor, mItemClickListener);
        mRecyclerView.setAdapter(myListCursorAdapter);

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
        //mAdapter.replaceData(movies);
    }

    @Override
    public void showMovieDetailUi(String movieId) {

    }

    @Override
    public void updateSortOrder(int orderType) {
        //mAdapter.sortBy(orderType);
    }

    @Override
    public void addMoviePage(List<Result> movies) {

        // insert data to the db // TODO: this should be done in the interactor, using DI inject the context in the interactor
        Vector<ContentValues> cVVector = new Vector<>(movies.size());
        for(Result result : movies) {
            ContentValues movieValues = new ContentValues();

            movieValues.put(FavMoviesContract.MovieEntry.COLUMN_KEY, result.getId());
            movieValues.put(FavMoviesContract.MovieEntry.COLUMN_TITLE, result.getTitle());
            movieValues.put(FavMoviesContract.MovieEntry.COLUMN_OVERVIEW, result.getOverview());
            movieValues.put(FavMoviesContract.MovieEntry.COLUMN_POPULARITY, result.getPopularity());
            movieValues.put(FavMoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE, result.getVoteAverage());
            movieValues.put(FavMoviesContract.MovieEntry.COLUMN_POSTER_PATH, result.getPosterPath());
            movieValues.put(FavMoviesContract.MovieEntry.COLUMN_VIDEO, result.isVideo());
            movieValues.put(FavMoviesContract.MovieEntry.COLUMN_RELEASE_DATE, result.getReleaseDate());

            cVVector.add(movieValues);
        }

        // add to database
        if ( cVVector.size() > 0 ) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            getContext().getContentResolver().bulkInsert(FavMoviesContract.MovieEntry.CONTENT_URI, cvArray);
        }

        // Test the basic content provider query
        Cursor weatherCursor = getContext().getContentResolver().query(
                FavMoviesContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        myListCursorAdapter.changeCursor(weatherCursor);


    }

    private MyListCursorAdapter.OnItemClickListener mItemClickListener = new MyListCursorAdapter.OnItemClickListener() {
        @Override
        public void onClick(Result movie) {
            Intent mIntent = new Intent(getContext(), MovieDetailActivity.class);
            mIntent.putExtra(KEY_MOVIE_CLIKED, movie);
            startActivity(mIntent);
        }
    };
}
