package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.habittracker.data.RecordContract.RecordEntry;
import com.example.android.habittracker.data.RecordDbHelper;

public class EntryActivity extends AppCompatActivity {

    // Spinner for the month options for the date
    private Spinner mMonthSpinner;

    // EditText field to enter the day
    private EditText mDayEditText;

    // EditText field to enter the year
    private EditText mYearEditText;

    // EditText field to enter steps taken
    private EditText mStepsEditText;

    // EditText field to enter current weight
    private EditText mWeightEditText;

    // EditText field to enter cups of water
    private EditText mWaterEditText;

    // Spinner for the meds options
    private Spinner mMedsSpinner;

    // Integer for the month selected
    private int mMonthChosen = 0;

    // Integer for medication taken options (0=no, 1=yes)
    private int mMedsAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        // Find all relevant views that we will need to read user input from
        mMonthSpinner = (Spinner) findViewById(R.id.spinner_month);
        mDayEditText = (EditText) findViewById(R.id.edit_text_day);
        mYearEditText = (EditText) findViewById(R.id.edit_text_year);
        mStepsEditText = (EditText) findViewById(R.id.edit_text_steps);
        mWeightEditText = (EditText) findViewById(R.id.edit_text_weight);
        mWaterEditText = (EditText) findViewById(R.id.edit_text_water);
        mMedsSpinner = (Spinner) findViewById(R.id.spinner_meds);

        setupMonthSpinner();
        setupMedsSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_main.xml file
        getMenuInflater().inflate(R.menu.menu_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.menu_item_save:
                // Save record to database
                insertRecord();
                // Exit activity
                finish();
                return true;
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // This method sets up the dropdown spinner for the month (January, February, March, April, May,
    // June, July, August, September, October, November, December)
    private void setupMonthSpinner() {
        // Create an ArrayAdapter for the spinner using the resource array named array_month_options
        ArrayAdapter monthSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_month_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        monthSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mMonthSpinner.setAdapter(monthSpinnerAdapter);

        // Set the integer mDateMonth to the constant values
        mMonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String monthSelection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(monthSelection)) {
                    if (monthSelection.equals(getString(R.string.month_jan))) {
                        mMonthChosen = RecordEntry.MONTH_JANUARY;       // January
                    } else if (monthSelection.equals(getString(R.string.month_feb))) {
                        mMonthChosen = RecordEntry.MONTH_FEBRUARY;      // February
                    } else if (monthSelection.equals(getString(R.string.month_mar))) {
                        mMonthChosen = RecordEntry.MONTH_MARCH;         // March
                    } else if (monthSelection.equals(getString(R.string.month_apr))) {
                        mMonthChosen = RecordEntry.MONTH_APRIL;         // April
                    } else if (monthSelection.equals(getString(R.string.month_may))) {
                        mMonthChosen = RecordEntry.MONTH_MAY;           // May
                    } else if (monthSelection.equals(getString(R.string.month_jun))) {
                        mMonthChosen = RecordEntry.MONTH_JUNE;          // June
                    } else if (monthSelection.equals(getString(R.string.month_jul))) {
                        mMonthChosen = RecordEntry.MONTH_JULY;          // July
                    } else if (monthSelection.equals(getString(R.string.month_aug))) {
                        mMonthChosen = RecordEntry.MONTH_AUGUST;        // August
                    } else if (monthSelection.equals(getString(R.string.month_sept))) {
                        mMonthChosen = RecordEntry.MONTH_SEPTEMBER;     // September
                    } else if (monthSelection.equals(getString(R.string.month_oct))) {
                        mMonthChosen = RecordEntry.MONTH_OCTOBER;       // October
                    } else if (monthSelection.equals(getString(R.string.month_nov))) {
                        mMonthChosen = RecordEntry.MONTH_NOVEMBER;      // November
                    } else if (monthSelection.equals(getString(R.string.month_dec))) {
                        mMonthChosen = RecordEntry.MONTH_DECEMBER;      // December
                    } else {
                        mMonthChosen = RecordEntry.MONTH_CHOOSE;        // Choose Month
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mMonthChosen = 0; // Choose Month
            }
        });
    }

    // This method sets up the dropdown spinner for medication taken (YES/NO)
    private void setupMedsSpinner() {
        // Create an ArrayAdapter for the spinner using the resource array named array_meds_options
        ArrayAdapter medsSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_meds_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        medsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mMedsSpinner.setAdapter(medsSpinnerAdapter);

        // Set the integer mMedsAnswer to the constant values
        mMedsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String answerSelection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(answerSelection)) {
                    if (answerSelection.equals(getString(R.string.answer_yes))) {
                        mMedsAnswer = RecordEntry.ANSWER_YES; // Yes
                    } else {
                        mMedsAnswer = RecordEntry.ANSWER_NO; // No
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mMedsAnswer = 0;    // No
            }
        });
    }

    // This method inserts a record into the habits.db
    private void insertRecord() {
        // Read from input fields, using trim to eliminate leading/trailing white space
        String dayString = mDayEditText.getText().toString().trim();
        String yearString = mYearEditText.getText().toString().trim();
        String stepsString = mStepsEditText.getText().toString().trim();
        int steps = Integer.parseInt(stepsString);
        String weightString = mWeightEditText.getText().toString().trim();
        int weight = Integer.parseInt(weightString);
        String waterString = mWaterEditText.getText().toString().trim();
        int water = Integer.parseInt(waterString);

        // Create database helper
        RecordDbHelper mDbHelper = new RecordDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys and record attributes from
        // the EntryActivity are the values
        ContentValues values = new ContentValues();
        values.put(RecordEntry.COLUMN_RECORD_MONTH, mMonthChosen);
        values.put(RecordEntry.COLUMN_RECORD_DAY, dayString);
        values.put(RecordEntry.COLUMN_RECORD_YEAR, yearString);
        values.put(RecordEntry.COLUMN_RECORD_STEPS, steps);
        values.put(RecordEntry.COLUMN_RECORD_WEIGHT, weight);
        values.put(RecordEntry.COLUMN_RECORD_WATER, water);
        values.put(RecordEntry.COLUMN_RECORD_MEDS, mMedsAnswer);

        // Insert a new record row in the database, returning the ID of that new row
        long newRowId = db.insert(RecordEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving record", Toast.LENGTH_LONG).show();    // error
        } else {
            Toast.makeText(this, "Record saved with ro id: " + newRowId, Toast.LENGTH_LONG).show();
        }
    }
}
