package com.example.lucarino.whattowatch.common.event;

import com.example.lucarino.whattowatch.data.Result;

/**
 * Created by luiscarino on 6/6/16.
 */

public class MovieSelectedEvent extends BaseEvent {

    Result mMovie;

    public MovieSelectedEvent(Result mMovie) {
        this.mMovie = mMovie;
    }

    public Result getmMovie() {
        return mMovie;
    }
}
