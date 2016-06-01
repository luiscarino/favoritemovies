package com.example.lucarino.whattowatch.moviedetail;

import com.example.lucarino.whattowatch.data.Result;

/**
 * This specifies the contract between the view and the presenter,
 * and the presenter and the interactor for the movie detail feature.
 */
public interface MovieDetailContract {

    interface View {
        void loadMovieDetail(Result movie);
    }

    interface UserActionsListener {
        void playVideo();
    }
}
