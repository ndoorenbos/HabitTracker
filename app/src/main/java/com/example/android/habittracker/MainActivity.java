package com.example.android.habittracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.habittracker.data.RecordContract.RecordEntry;
import com.example.android.habittracker.data.RecordDbHelper;

public class MainActivity extends AppCompatActivity {
    // Database helper that will provide us access to the database
    private RecordDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate our subclass of SQLiteOpenHelper and pass the context
        mDbHelper = new RecordDbHelper(this);

        displayDatabaseInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_main.xml file
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "New Entry" menu option
            case R.id.menu_item_new_entry:
                Intent newEntryIntent = new Intent(MainActivity.this, EntryActivity.class);
                startActivity(newEntryIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // This method displays the information from the database
    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Create the projection String[], selection String, and selectionArgs String[]
        String[] projection = null;
        String selection = null;
        String[] selectionArgs = null;

        // Perform an SQL query with the projection, selection, and selectionArgs variables
        Cursor cursor = db.query(RecordEntry.TABLE_NAME, projection, selection, selectionArgs, null,
                null, null);

        // Find the TextView to display the records
        TextView displayTextView = (TextView) findViewById(R.id.main_display_text_view);

        try {
            // Display the number of rows in the cursor (number of rows in records table in the
            // database)
            displayTextView.setText(getString(R.string.display_records_quantity) + cursor.getCount()
                    + getString(R.string.display_records) + "\n\n");
            displayTextView.append(RecordEntry._ID + " - " +
                    RecordEntry.COLUMN_RECORD_MONTH + "-" +
                    RecordEntry.COLUMN_RECORD_DAY + "-" +
                    RecordEntry.COLUMN_RECORD_YEAR + " - " +
                    RecordEntry.COLUMN_RECORD_STEPS + " - " +
                    RecordEntry.COLUMN_RECORD_WEIGHT + " - " +
                    RecordEntry.COLUMN_RECORD_WATER + " - " +
                    RecordEntry.COLUMN_RECORD_MEDS + "\n\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(RecordEntry._ID);
            int monthColumnIndex = cursor.getColumnIndex(RecordEntry.COLUMN_RECORD_MONTH);
            int dayColumnIndex = cursor.getColumnIndex(RecordEntry.COLUMN_RECORD_DAY);
            int yearColumnIndex = cursor.getColumnIndex(RecordEntry.COLUMN_RECORD_YEAR);
            int stepsColumnIndex = cursor.getColumnIndex(RecordEntry.COLUMN_RECORD_STEPS);
            int weightColumnIndex = cursor.getColumnIndex(RecordEntry.COLUMN_RECORD_WEIGHT);
            int waterColumnIndex = cursor.getColumnIndex(RecordEntry.COLUMN_RECORD_WATER);
            int medsColumnIndex = cursor.getColumnIndex(RecordEntry.COLUMN_RECORD_MEDS);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Extract the values in the current row of the cursor, use the above index numbers
                int currentID = cursor.getInt(idColumnIndex);
                int currentMonth = cursor.getInt(monthColumnIndex);
                String currentDay = cursor.getString(dayColumnIndex);
                String currentYear = cursor.getString(yearColumnIndex);
                int currentSteps = cursor.getInt(stepsColumnIndex);
                int currentWeight = cursor.getInt(weightColumnIndex);
                int currentWater = cursor.getInt(waterColumnIndex);
                int currentMeds = cursor.getInt(medsColumnIndex);

                // Change the meds integer into a yes/no answer
                String currentMedsAnswer;
                if (currentMeds == 1) {
                    currentMedsAnswer = getString(R.string.answer_yes);
                } else {
                    currentMedsAnswer = getString(R.string.answer_no);
                }

                // Display the values above in the displayTextView
                displayTextView.append(currentID + " - " +
                        currentMonth + "-" +
                        currentDay + "-" +
                        currentYear + " - " +
                        currentSteps + " " + getString(R.string.entry_unit_steps) + " - " +
                        currentWeight + " " + getString(R.string.entry_unit_weight) + " - " +
                        currentWater + " " + getString(R.string.entry_unit_water) + " - " +
                        getString(R.string.entry_meds) + " " + currentMedsAnswer + "\n\n");
            }
        } finally {
            // Close the cursor
            cursor.close();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }
}
