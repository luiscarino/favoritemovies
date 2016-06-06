package com.example.lucarino.whattowatch.common.event;

import android.support.annotation.StringDef;

import com.example.lucarino.whattowatch.data.FavMoviesContract;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by luiscarino on 6/3/16.
 */

public class FilterAppliedEvent extends BaseEvent {

    private String filter;

    @Retention(SOURCE)
    @StringDef({
            MOST_POPULAR_FILTER,
            HIGHEST_RATED_FILTER,
            FAVORITES_FILTER
    })

    public @interface FilterOption {}
    public static final String MOST_POPULAR_FILTER = FavMoviesContract.MovieEntry.COLUMN_POPULARITY;
    public static final String HIGHEST_RATED_FILTER = FavMoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE;
    public static final String FAVORITES_FILTER = "favorites";

    public FilterAppliedEvent(@FilterOption String filter) {
        this.filter = filter;
    }

    public String getFilter() {
        return filter;
    }
}
