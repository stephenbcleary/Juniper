package com.flybottle.android.juniper;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import org.joda.time.DateTime;

import java.util.Calendar;


public class TimePickerFragment extends DialogFragment
                                                    implements TimePickerDialog.OnTimeSetListener {

    private android.app.TimePickerDialog.OnTimeSetListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DateTime entryDate = (DateTime) getArguments().getSerializable("entryDate");
        int hour = entryDate.getHourOfDay();
        int minute = entryDate.getMinuteOfHour();

        // Create a new instance of TimePickerFragment and return it
        return new android.app.TimePickerDialog(getActivity(), this, hour, minute,
                                                        DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        listener.onTimeSet(view, hourOfDay, minute);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the contain activity has implemented
        // the callback interface.  If not, it throws an exception.
        try {
            listener = (android.app.TimePickerDialog.OnTimeSetListener) activity;
        } catch (ClassCastException error) {
            throw new ClassCastException(activity.toString() + " must implement OnTimeSetListener");
        }
    }

}
