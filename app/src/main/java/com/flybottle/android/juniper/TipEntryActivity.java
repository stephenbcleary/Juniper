package com.flybottle.android.juniper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class TipEntryActivity extends Activity implements TimePickerDialog.OnTimeSetListener,
                                                          DatePickerDialog.OnDateSetListener {
    private Juniper juniper = Juniper.getInstance();
    private TipEntry tipEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_entry);
        Intent intent = getIntent();
        initializeFields();
    }

    protected void setTipEntry(TipEntry tipEntry) {
        this.tipEntry = tipEntry;
    }

    private void initializeFields() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            DateTime startDate = (DateTime) extras.getSerializable("entryDate");
            tipEntry = juniper.getEntryWithDateTime(startDate);
        } else {
            tipEntry = new TipEntry();
        }
        refreshFields();
    }

    private void refreshFields() {
        refreshDate();
        refreshTime();
        refreshValues();
    }

    private void refreshDate() {
        DateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy");
        TextView startDate = (TextView) findViewById(R.id.tip_entry_date_field);
        startDate.setText(formatter.format(tipEntry.getStartDate().toDate()));
        /*
        if (!TipEntry.isValidStartDate(tipEntry.getStartDate())) {
            startDate.setError("Invalid Date");
        } else {
            startDate.setError(null);
        }
        */
    }

    private void refreshTime() {
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        TextView startTime = (TextView)findViewById(R.id.tip_entry_time_field);
        startTime.setText(formatter.format(tipEntry.getStartDate().toDate()));
        /*
        if (!TipEntry.isValidDate(tipEntry.getStartDate())) {
            startTime.setError("Invalid Date");
        } else {
            startTime.setError(null);
        }
        */
    }

    private void refreshValues() {
        if (tipEntry.getAmount() != 0.0) {
            // format ammount
            NumberFormat formatter = new DecimalFormat("#0.00");
            EditText tipout = (EditText) findViewById(R.id.tipout_entry);
            tipout.setText(formatter.format(tipEntry.getAmount()));

            Duration duration = tipEntry.getDateInterval().toDuration();
            // format hours
            long hours = duration.getStandardHours();
            EditText hourText = (EditText) findViewById(R.id.tip_entry_duration_hours);
            hourText.setText("" + hours);

            // format minutes
            long minutes = (duration.getStandardMinutes() % 60L);
            EditText minuteText = (EditText) findViewById(R.id.tip_entry_duration_minutes);
            minuteText.setText("" + minutes);

        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable("entryDate", tipEntry.getStartDate());
        newFragment.setArguments(args);
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        DateTime dateTime = tipEntry.getStartDate();
        dateTime = dateTime.withDate(year, month, day);
        tipEntry.setStartDate(dateTime);
        refreshDate();
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        DateTime dateTime = tipEntry.getStartDate();
        dateTime = dateTime.withTime(hourOfDay, minute, 0, 0);
        tipEntry.setStartDate(dateTime);
        refreshTime();
    }

    public void saveEntry(View v) {
        TextView ammount = (TextView)findViewById(R.id.tipout_entry);
        String ammountString = ammount.getText().toString();
        double ammountDouble = Double.parseDouble(ammountString);
        tipEntry.setAmount(Double.parseDouble(ammountString));

        //if (tipEntry.isComplete()) {
            juniper.addTip(tipEntry);
            finish();
        /*} else {
            System.out.println("else");
            refreshFields();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tip_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
