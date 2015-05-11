package com.flybottle.android.juniper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by alex on 10/05/15.
 */
public class DatePickerFragment extends DialogFragment
                                                    implements DatePickerDialog.OnDateSetListener {

    DatePickerDialog.OnDateSetListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (DatePickerDialog.OnDateSetListener) activity;
        } catch (ClassCastException error) {
            throw new ClassCastException(activity.toString()
                                                        + "must implement OnDateSelectListener");
        }
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        listener.onDateSet(view, year, month, day);
    }
}
