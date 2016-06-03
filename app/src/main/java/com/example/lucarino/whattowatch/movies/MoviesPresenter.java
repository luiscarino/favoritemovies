package com.example.lucarino.whattowatch.movies;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.lucarino.whattowatch.application.MoviesApplication;
import com.example.lucarino.whattowatch.data.Result;

import java.util.List;

import javax.inject.Inject;


/**
 * Listener to user actions from the UI {@link MoviesFragment}
 * retrieves data from the {@link MoviesInteractor} and updates the UI as required.
 */
public class MoviesPresenter implements MoviesContract.UserActionListener{


  //  private final DiscoverInteractor mDiscoverInteractor;
    private final MoviesContract.View mMoviesView;

    @Inject
    MoviesContract.Interactor mMoviesInteractor;

    public MoviesPresenter(@NonNull MoviesContract.View moviesView){
        MoviesApplication.getApplicationComponent().inject(this);
        mMoviesView = moviesView;
    }


    @Override
    public void openMovieDetail(@NonNull Result movieRequested) {

    }

    @Override
    public void changeSortOrder(String orderType) {

    }

    @Override
    public void loadMoreMovies(@NonNull int pageNumber) {
        mMoviesInteractor.fetchMoviePage(new MoviesContract.Interactor.retrofitMoviesLoadPageCallback() {
            @Override
            public void onSuccess(List<Result> response) {
                mMoviesView.addMoviePage(response);
            }

            @Override
            public void onFailure() {
                Log.e(getClass().getSimpleName(), "ERROR FETCHING MOVIE PAGE FROM SERVER");
            }
        }, pageNumber);
    }

}
