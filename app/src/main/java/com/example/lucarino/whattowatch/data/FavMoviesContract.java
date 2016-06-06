package com.example.lucarino.whattowatch.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines tables and columns for the FavMovies DB.
 * Created by luiscarino on 6/2/16.
 */

public class FavMoviesContract {


        // The "Content authority" is a name for the entire content provider, similar to the
        // relationship between a domain name and its website.  A convenient string to use for the
        // content authority is the package name for the app, which is guaranteed to be unique on the
        // device.
        public static final String CONTENT_AUTHORITY = "com.example.lucarino.whattowatch";

        // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
        // the content provider.
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

        // Possible paths (appended to base content URI for possible URI's)
        // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
        // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
        // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
        // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.

        public static final String PATH_MOVIES = "movies";

        /*
            Inner class that defines the table contents of the location table
            Students: This is where you will add the strings.  (Similar to what has been
            done for WeatherEntry)
         */
        public static final class MovieEntry implements BaseColumns {


            public static final Uri CONTENT_URI =  BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

            public static final String CONTENT_TYPE =
                    ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

            public static final String CONTENT_ITEM_TYPE =
                    ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;


            public static final String TABLE_NAME = "movies";

            public static final String COLUMN_KEY = "id";
            public static final String COLUMN_TITLE = "title";
            public static final String COLUMN_OVERVIEW = "overview";
            public static final String COLUMN_POPULARITY = "popularity";
            public static final String COLUMN_VOTE_AVERAGE = "vote_average";
            public static final String COLUMN_RELEASE_DATE = "release_date";
            public static final String COLUMN_POSTER_PATH = "poster_path";
            public static final String COLUMN_VIDEO = "video";


            public static Uri buildMoviesUri(long id) {
                return ContentUris.withAppendedId(CONTENT_URI, id);
            }

            public static String getMovieIdUri(Uri uri) {
                return uri.getPathSegments().get(1);
            }

            public static Uri buildMoviesUriForSorted(String filter) {
                return CONTENT_URI.buildUpon().appendPath(filter).build();
            }


        }
}
