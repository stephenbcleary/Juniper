package com.flybottle.android.juniper;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.Interval;

/**
 * This class contains utilities for handling dates.
 *
 * Created by alex on 10/11/15.
 */
public abstract class DateUtils {

    protected static DateTime getWeekStart() {
        return DateTime.now().withDayOfWeek(1);
    }

    protected static DateTime getWeekEnd() {
        return DateTime.now().withDayOfWeek(7);
    }

    protected static DateTime getMonthStart() {
        return DateTime.now().withDayOfMonth(1);
    }

    protected static DateTime getMonthEnd() {
        return DateTime.now().withDayOfMonth(DateTime.now().dayOfMonth().getMaximumValue());
    }
}
