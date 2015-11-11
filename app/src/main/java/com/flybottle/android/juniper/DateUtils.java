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
        now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), 1);
        return now.getTime();
    }

    protected static Date getMonthEnd() {
        Calendar now = Calendar.getInstance();
        now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, 0);
        return now.getTime();
    }

    protected static int daysBetween(Date start, Date end) {
        // milliseconds to days = 1000 * 60 * 60 * 24 = 86400000
        return (int)((end.getTime() - start.getTime()) / 86400000);
    }
}
