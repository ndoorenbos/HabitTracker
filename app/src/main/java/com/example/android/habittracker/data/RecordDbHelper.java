package com.example.android.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.habittracker.data.RecordContract.RecordEntry;

/**
 * Created by ndoor on 12/8/2016.
 */

public class RecordDbHelper extends SQLiteOpenHelper {
    // Name of the database
    private static final String DATABASE_NAME = "habits.db";

    // Database version. If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;

    // Constructs a new instance of {@link RecordDbHelper}
    public RecordDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the records table
        // For this exercise I am considering the day and month entries to be Strings
        String SQL_CREATE_RECORDS_TABLE = "CREATE TABLE " +
                RecordEntry.TABLE_NAME + " (" +
                RecordEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RecordEntry.COLUMN_RECORD_MONTH + " INTEGER NOT NULL DEFAULT 0, " +
                RecordEntry.COLUMN_RECORD_DAY + " TEXT, " +
                RecordEntry.COLUMN_RECORD_YEAR + " TEXT, " +
                RecordEntry.COLUMN_RECORD_STEPS + " INTEGER NOT NULL DEFAULT 0, " +
                RecordEntry.COLUMN_RECORD_WEIGHT + " INTEGER, " +
                RecordEntry.COLUMN_RECORD_WATER + " INTEGER NOT NULL DEFAULT 0, " +
                RecordEntry.COLUMN_RECORD_MEDS + " INTEGER NOT NULL DEFAULT 0);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_RECORDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here
    }
}
