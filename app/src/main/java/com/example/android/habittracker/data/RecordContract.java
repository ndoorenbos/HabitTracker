package com.example.android.habittracker.data;

import android.content.res.Resources;
import android.provider.BaseColumns;

import com.example.android.habittracker.R;

/**
 * Created by ndoor on 12/5/2016
 * Defines table contents for tables in the habits database
 */

public final class RecordContract {

    // Inner class that defines the table contents of the records table
    public static final class RecordEntry implements BaseColumns {

        // The name of the RecordEntry table in the database
        public static final String TABLE_NAME = "records";

        // Column headers for the RecordEntry Table in the database
        public static final String _ID = BaseColumns._ID; // Unique ID given to a record entry
        public static final String COLUMN_RECORD_MONTH = "month";       // Record month for date
        public static final String COLUMN_RECORD_DAY = "day";           // Record day for date
        public static final String COLUMN_RECORD_YEAR = "year";         // Record year for date
        public static final String COLUMN_RECORD_STEPS = "steps";       // Recorded steps taken
        public static final String COLUMN_RECORD_WEIGHT = "weight";     // Recorded weight
        public static final String COLUMN_RECORD_WATER = "water";       // Recorded cups of water
        public static final String COLUMN_RECORD_MEDS = "meds";         // Recorded meds (yes or no)

        // Constants for the month selection spinner
        public static final int MONTH_CHOOSE = 0;
        public static final int MONTH_JANUARY = 1;
        public static final int MONTH_FEBRUARY = 2;
        public static final int MONTH_MARCH = 3;
        public static final int MONTH_APRIL = 4;
        public static final int MONTH_MAY = 5;
        public static final int MONTH_JUNE = 6;
        public static final int MONTH_JULY = 7;
        public static final int MONTH_AUGUST = 8;
        public static final int MONTH_SEPTEMBER = 9;
        public static final int MONTH_OCTOBER = 10;
        public static final int MONTH_NOVEMBER = 11;
        public static final int MONTH_DECEMBER = 12;

        // Constants for a no/yes selection spinner
        public static final int ANSWER_NO = 0;
        public static final int ANSWER_YES = 1;
    }
}