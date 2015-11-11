package com.flybottle.android.juniper;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;

/**
 * This class contains utilities for handling dates.
 *
 * Created by alex on 10/11/15.
 */
public abstract class DateUtils {

    protected static Date getWeekStart() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_WEEK, now.getFirstDayOfWeek());
        return now.getTime();
    }

    protected static Date getWeekEnd() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_WEEK, now.getFirstDayOfWeek() + 6);
        return now.getTime();
    }

    protected static Date getMonthStart() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DAY_OF_YEAR, now.get(Calendar.DAY_OF_WEEK) - now.get(Calendar.DAY_OF_MONTH));
        return now.getTime();
    }

    protected static Date getMonthEnd() {
        Calendar now = Calendar.getInstance();
        now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), 0);
        return now.getTime();
    }
}
