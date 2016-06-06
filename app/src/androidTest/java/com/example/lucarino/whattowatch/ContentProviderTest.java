package com.example.lucarino.whattowatch;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.example.lucarino.whattowatch.data.FavMoviesContract;
import com.example.lucarino.whattowatch.data.FavMoviesDbHelper;
import com.example.lucarino.whattowatch.domain.FavMoviesProvider;

import static com.example.lucarino.whattowatch.data.FavMoviesContract.MovieEntry;

/**
 * Created by luiscarino on 6/2/16.
 */

public class ContentProviderTest extends AndroidTestCase {


    /*
        This test checks to make sure that the content provider is registered correctly.
     */
    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();

        // We define the component name based on the package name from the context and the
        // WeatherProvider class.
        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                FavMoviesProvider.class.getName());
        try {
            // Fetch the provider info using the component name from the PackageManager
            // This throws an exception if the provider isn't registered.
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals("Error: WeatherProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + FavMoviesContract.CONTENT_AUTHORITY,
                    providerInfo.authority, FavMoviesContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            // I guess the provider isn't registered correctly.
            assertTrue("Error: WeatherProvider not registered at " + mContext.getPackageName(),
                    false);
        }
    }


    /*
            This test  verifies that the ContentProvider returns
            the correct type for each type of URI that it can handle.

         */
    public void testGetType() {
        // content://com.fav.movies/movies/
        String type = mContext.getContentResolver().getType(MovieEntry.CONTENT_URI);
        // vnd.android.cursor.dir/com.example.android.sunshine.app/movies
        assertEquals("Error: the WeatherEntry CONTENT_URI should return WeatherEntry.CONTENT_TYPE",
                MovieEntry.CONTENT_TYPE, type);

        long testLocation = 2343L;
        // content://com.fav.movies/movies/94074
        type = mContext.getContentResolver().getType(
                MovieEntry.buildMoviesUri(testLocation));
        // vnd.android.cursor.dir/com.example.android.sunshine.app/weather
        assertEquals("Error: the WeatherEntry CONTENT_URI with location should return WeatherEntry.CONTENT_TYPE",
                MovieEntry.CONTENT_ITEM_TYPE, type);

    }


    /*
        This test uses the database directly to insert and then uses the ContentProvider to
        read out the data.  Uncomment this test to see if the basic movies query functionality
        given in the ContentProvider is working correctly.
     */
    public void testBasicWeatherQuery() {
        // insert our test records into the database
        FavMoviesDbHelper dbHelper = new FavMoviesDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValues = TestUtilities.createMovieValues();


        long weatherRowId = db.insert(MovieEntry.TABLE_NAME, null, testValues);
        assertTrue("Unable to Insert WeatherEntry into the Database", weatherRowId != -1);



        Cursor moviesCursor = mContext.getContentResolver().query(MovieEntry.buildMoviesUriForSorted("vote_average"),
                null,
                null,
                null,
                null);

        // Test the basic content provider query
         moviesCursor = mContext.getContentResolver().query(
                MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );



        db.close();

    }




    // Make sure we can still delete after adding/updating stuff
    //
    public void testInsertReadProvider() {
        ContentValues testValues = TestUtilities.createMovieValues();

        // Register a content observer for our insert.  This time, directly with the content resolver
        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(FavMoviesContract.MovieEntry.CONTENT_URI, true, tco);
        Uri locationUri = mContext.getContentResolver().insert(FavMoviesContract.MovieEntry.CONTENT_URI, testValues);

        // If this fails, the insert location
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(tco);

        long locationRowId = ContentUris.parseId(locationUri);

        // Verify we got a row back.
        assertTrue(locationRowId != -1);

        // Data's inserted.  IN THEORY.  Now pull some out to stare at it and verify it made
        // the round trip.

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                FavMoviesContract.MovieEntry.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        TestUtilities.validateCursor("testInsertReadProvider. Error validating MovieEntry.",
                cursor, testValues);



        // Get the joined Weather data for a specific date
        cursor = mContext.getContentResolver().query(
                MovieEntry.buildMoviesUri(2343),
                null,
                null,
                null,
                null
        );

        TestUtilities.validateCursor("testInsertReadProvider. Error validating MovieEntry.",
                cursor, testValues);


    }

}
