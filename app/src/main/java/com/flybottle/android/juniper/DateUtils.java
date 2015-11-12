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
        return DateTime.now().withDayOfWeek(1).withTimeAtStartOfDay();
    }

    protected static DateTime getWeekEnd() {
        return getWeekStart().plusDays(6).plusHours(23).plusMinutes(59).plusSeconds(59);
    }

    protected static DateTime getMonthStart() {
        return DateTime.now().withDayOfMonth(1).withTimeAtStartOfDay();
    }

    protected static DateTime getMonthEnd() {
        return getMonthStart().withDayOfMonth(DateTime.now().dayOfMonth().getMaximumValue());
                                                //.plusHours(23).plusMinutes(59).plusSeconds(59);
    }
}
