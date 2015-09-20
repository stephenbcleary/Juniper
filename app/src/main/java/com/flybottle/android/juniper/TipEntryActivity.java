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
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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


    private void initializeFields() {
        tipEntry = new TipEntry();
        refreshFields();
    }

    private void refreshFields() {
        refreshDate();
        refreshTime();
    }

    private void refreshDate() {
        DateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy");
        TextView startDate = (TextView) findViewById(R.id.tip_entry_date_field);
        startDate.setText(formatter.format(tipEntry.getStartDate().getTime()));
        if (!TipEntry.isValidDate(tipEntry.getStartDate())) {
            startDate.setError("Invalid Date");
        } else {
            startDate.setError(null);
        }
    }

    private void refreshTime() {
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        TextView startTime = (TextView)findViewById(R.id.tip_entry_time_field);
        startTime.setText(formatter.format(tipEntry.getStartDate().getTime()));
        if (!TipEntry.isValidDate(tipEntry.getStartDate())) {
            startTime.setError("Invalid Date");
        } else {
            startTime.setError(null);
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar startDate = tipEntry.getStartDate();
        startDate.set(year, month, day);
        refreshDate();
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar startDate = tipEntry.getStartDate();
        startDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
        startDate.set(Calendar.MINUTE, minute);
        refreshTime();
    }

    public void saveEntry(View v) {
        TextView ammount = (TextView)findViewById(R.id.tipout_entry);
        String ammountString = ammount.getText().toString();
        double ammountDouble = Double.parseDouble(ammountString);
        tipEntry.setAmmount(Double.parseDouble(ammountString));

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
