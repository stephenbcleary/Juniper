package com.flybottle.android.juniper;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by alex on 11/05/15.
 */
public class TipEntry {
    private double ammount;
    private Calendar startDate;
    private Calendar stopDate;

    public TipEntry() {
        // initialize to 0 dollars
        ammount = 0.0;
        Calendar now = Calendar.getInstance();
        // initialize stopDate to 6 hours before NOW
        startDate = Calendar.getInstance();
        startDate.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY) - 6);

        // initialize stopDate to NOW
        stopDate = Calendar.getInstance();
    }

    public static boolean isValidAmmount(double ammount) {
        double mantisa = ammount - ((int) ammount);
        double thousands = (mantisa * 100) - ((int)(mantisa * 100));
        return thousands == 0.0;
    }

    public static boolean isValidDate(Calendar calendar) {
        Calendar now = Calendar.getInstance();
        return calendar.before(now);
    }

    public long getId() {
        return startDate.getTimeInMillis();
    }

    public Double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        if (isValidAmmount(ammount)) {
            this.ammount = ammount;
        }
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        if (isValidDate(startDate)) {
            this.startDate = startDate;
        }
    }

    public Calendar getStopDate() {
        return stopDate;
    }

    public void setStopDate(Calendar stopDate) {
        if (isValidDate(stopDate)) {
            this.stopDate = stopDate;
        }
    }

}
