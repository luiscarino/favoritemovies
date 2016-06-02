package com.example.lucarino.whattowatch;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.example.lucarino.whattowatch.data.FavMoviesContract;
import com.example.lucarino.whattowatch.util.FavMoviesProvider;

/**
 * Created by luiscarino on 6/2/16.
 */

public class TestUriMatcher extends AndroidTestCase {


    private static final long ID_KEY = 2343L;


    private static final Uri TEST_MOVIES_DIR = FavMoviesContract.MovieEntry.CONTENT_URI;
    private static final Uri TEST_MOVIE_WITH_ID_DIR = FavMoviesContract.MovieEntry.buildMoviesUri(ID_KEY);


    /*
        Students: This function tests that your UriMatcher returns the correct integer value
        for each of the Uri types that our ContentProvider can handle.  Uncomment this when you are
        ready to test your UriMatcher.
     */
    public void testUriMatcher() {
        UriMatcher testMatcher = FavMoviesProvider.buildUriMatcher();

        assertEquals("Error: The WEATHER URI was matched incorrectly.",
                testMatcher.match(TEST_MOVIES_DIR), FavMoviesProvider.MOVIES);
        assertEquals("Error: The WEATHER WITH LOCATION URI was matched incorrectly.",
                testMatcher.match(TEST_MOVIE_WITH_ID_DIR), FavMoviesProvider.MOVIE_WITH_ID);

    }

}
