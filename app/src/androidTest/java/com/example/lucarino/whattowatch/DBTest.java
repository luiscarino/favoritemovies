package com.example.lucarino.whattowatch;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.example.lucarino.whattowatch.data.FavMoviesDbHelper;

import java.util.HashSet;

import static com.example.lucarino.whattowatch.data.FavMoviesContract.MovieEntry;

/**
 * Created by luiscarino on 6/2/16.
 */

public class DBTest extends AndroidTestCase {

    // Since we want each test to start with a clean slate
    void deleteTheDatabase() {
        mContext.deleteDatabase(FavMoviesDbHelper.DATABASE_NAME);
    }

    /*
        This function gets called before each test is executed to delete the database.  This makes
        sure that we always have a clean test.
     */
    public void setUp() {
        deleteTheDatabase();
    }

    public void testDbCreation() throws Throwable {

        // build a HashSet of all of the table names we wish to look for
        // Note that there will be another table in the DB that stores the
        // Android metadata (db version information)
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(MovieEntry.TABLE_NAME);


        mContext.deleteDatabase(FavMoviesDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new FavMoviesDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );

        // if this fails, it means that your database doesn't contain both the location entry
        // and weather entry tables
        assertTrue("Error: Your database was created without both the location entry and weather entry tables",
                tableNameHashSet.isEmpty());

        // now, do our tables contain the correct columns?
        c = db.rawQuery("PRAGMA table_info(" + MovieEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> moviesColumnHashSet = new HashSet<String>();
        moviesColumnHashSet.add(MovieEntry._ID);
        moviesColumnHashSet.add(MovieEntry.COLUMN_KEY);
        moviesColumnHashSet.add(MovieEntry.COLUMN_OVERVIEW);
        moviesColumnHashSet.add(MovieEntry.COLUMN_POSTER_PATH);
        moviesColumnHashSet.add(MovieEntry.COLUMN_TITLE);
        moviesColumnHashSet.add(MovieEntry.COLUMN_POPULARITY);
        moviesColumnHashSet.add(MovieEntry.COLUMN_VOTE_AVERAGE);
        moviesColumnHashSet.add(MovieEntry.COLUMN_RELEASE_DATE);


        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            moviesColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                moviesColumnHashSet.isEmpty());
        db.close();

    }


    public void testMoviesTable() {
        insertMovie();
    }

    private void insertMovie() {
        final SQLiteDatabase db = new FavMoviesDbHelper(mContext).getWritableDatabase();
        // Create ContentValues of what you want to insert
        // (you can use the createNorthPoleLocationValues if you wish)
        ContentValues valuesToInsert = TestUtilities.createMovieValues();

        // Insert ContentValues into database and get a row ID back
        final long rowID = db.insert(MovieEntry.TABLE_NAME, null, valuesToInsert);

        assertTrue(rowID != -1);

        // Query the database and receive a Cursor back
        final Cursor cursor = db.query(true, MovieEntry.TABLE_NAME, null, null, null, null, null, null, null);

        // Move the cursor to a valid database row
        assertTrue("Error no records found", cursor.moveToFirst());

        // Validate data in resulting Cursor with the original ContentValues
        // (you can use the validateCurrentRecord function in TestUtilities to validate the
        // query if you like)
        TestUtilities.validateCurrentRecord("Error", cursor, valuesToInsert);

        // Finally, close the cursor and database
        cursor.close();
        db.close();

    }
}
