package com.example.lucarino.whattowatch.movies;

import android.support.annotation.NonNull;

import com.example.lucarino.whattowatch.data.Movies;
import com.example.lucarino.whattowatch.data.Result;

import java.util.List;

/**
 * This interface specifies the contract between  the view and the presenter
 * and the presenter and  the interactor for the main movies feature.
 */
public interface MoviesContract {


    // The View layer handles the user interface and these are the only functions that we expose to other layers.
    interface View {

        void setProgressIndicator(boolean active);

        void showMovies(Movies movies);

        void showMovieDetailUi(String movieId);

        void updateSortOrder(int orderType);

        void addMoviePage(List<Result> movies);

    }

    // It describes the actions that can be started from the View - for example opening or adding a movie.
    // The view should not handle user interaction directly where it affects the model.
    // Instead, it should forward interactions to the presenter.

    interface UserActionListener {

        void openMovieDetail(@NonNull Result movieRequested);

        void changeSortOrder(String orderType);

        void loadMoreMovies(@NonNull int pageNumber);
    }

    // It describes the direct interactions with the model layer.
    // The presenter should forward all the data management to the interactor.
    interface Interactor{

        void fetchMoviePage(@NonNull retrofitMoviesLoadPageCallback listener, @NonNull int page);


        interface retrofitMoviesLoadPageCallback{
            void onSuccess(List<Result> response);

            void onFailure();
        }
    }

}
