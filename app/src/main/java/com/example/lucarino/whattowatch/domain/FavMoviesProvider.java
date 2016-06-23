package com.example.lucarino.whattowatch.domain;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.lucarino.whattowatch.data.FavMoviesContract;
import com.example.lucarino.whattowatch.data.FavMoviesDbHelper;

/**
 * Created by luiscarino on 6/2/16.
 */

public class FavMoviesProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private FavMoviesDbHelper mOpenHelper;

    public static final int MOVIES = 100;
    public static final int MOVIE_WITH_ID = 101;
    public static final int MOVIES_SORTED_BY = 102;
    public static final int SET_MOVIE_AS_FAVORITE = 103;

    //movies.key = ?
    private static final String mSelectedMovieSelection =
            FavMoviesContract.MovieEntry.TABLE_NAME+
                    "." + FavMoviesContract.MovieEntry.COLUMN_KEY + " = ? ";


    private Cursor getMovieById(Uri uri, String[] projection, String sortOrder) {
        String movieId = FavMoviesContract.MovieEntry.getMovieIdUri(uri);

        String[] selectionArgs;
        String selection;

        selection = mSelectedMovieSelection;
        selectionArgs = new String[]{movieId};


        return mOpenHelper.getWritableDatabase().query(
                FavMoviesContract.MovieEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );


    }


    private Cursor getMoviesBySortOrder(Uri uri, String selection, String[] selectionArgs, String[] projection, String sortOrder) {

        String sort = FavMoviesContract.MovieEntry.getMovieIdUri(uri);
        String sortOder = sort+" DESC";
        return mOpenHelper.getWritableDatabase().query(
                FavMoviesContract.MovieEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOder
        );
    }

    private int setMovieAsFavorite(Uri uri, ContentValues values) {
        String movieId = FavMoviesContract.MovieEntry.getMovieIdFromFavUri(uri);

        return mOpenHelper.getWritableDatabase().update(
                FavMoviesContract.MovieEntry.TABLE_NAME, values, "id=" + movieId, null);
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new FavMoviesDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

            // "movie/*"
            case MOVIE_WITH_ID: {
                retCursor = getMovieById(uri, projection, sortOrder);
                break;
            }
            // "movies"
            case MOVIES: {
                retCursor = mOpenHelper.getWritableDatabase().query(
                        FavMoviesContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder,
                        null);
                break;
            }
            // "movies sorted by"
            case MOVIES_SORTED_BY: {
                retCursor = getMoviesBySortOrder(uri, selection, selectionArgs, projection, sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
            case MOVIES:
                return FavMoviesContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_WITH_ID:
                return FavMoviesContract.MovieEntry.CONTENT_ITEM_TYPE;
            case MOVIES_SORTED_BY:
                return FavMoviesContract.MovieEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MOVIES: {
                long _id = db.insert(FavMoviesContract.MovieEntry.TABLE_NAME, null, values);
                if(_id > 0) {
                    returnUri = FavMoviesContract.MovieEntry.buildMoviesUri(_id);
                }  else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO : implement me
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int retCount;
        switch (sUriMatcher.match(uri)) {
            case SET_MOVIE_AS_FAVORITE :
                retCount = setMovieAsFavorite(uri, values);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return retCount;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        // FIXME: more elegant fashion
                        String id = value.getAsString("id");
                        Cursor cursor = db.rawQuery("select * from movies where id = ?", new String[] {id+""});
                        if(!cursor.moveToFirst()) {
                            long _id = db.insert(FavMoviesContract.MovieEntry.TABLE_NAME, null, value);
                            if (_id != -1) {
                                returnCount++;
                            }
                        }
                        cursor.close();
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }


    /*
        This UriMatcher will
        match each URI to the MOVIES and MOVIES_WITH_ID integer constants defined above.  You can test this by uncommenting the
        testUriMatcher test within TestUriMatcher.
     */
    public static UriMatcher buildUriMatcher() {
        // 1) The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case. Add the constructor below.
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // 2) Use the addURI function to match each of the types.  Use the constants from
        // MoviesContract to help define the types to the UriMatcher.
        uriMatcher.addURI(FavMoviesContract.CONTENT_AUTHORITY, FavMoviesContract.PATH_MOVIES + "/*/*", SET_MOVIE_AS_FAVORITE);
        uriMatcher.addURI(FavMoviesContract.CONTENT_AUTHORITY, FavMoviesContract.PATH_MOVIES + "/*", MOVIES_SORTED_BY);
        uriMatcher.addURI(FavMoviesContract.CONTENT_AUTHORITY, FavMoviesContract.PATH_MOVIES, MOVIES);
       // uriMatcher.addURI(FavMoviesContract.CONTENT_AUTHORITY, FavMoviesContract.PATH_MOVIES + "/#", MOVIE_WITH_ID);


        // 3) Return the new matcher!
        return uriMatcher;
    }
}
