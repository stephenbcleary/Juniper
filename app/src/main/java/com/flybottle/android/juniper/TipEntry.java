package com.flybottle.android.juniper;

import java.util.Calendar;


public class TipEntry {
    private double ammount;
    private Calendar startDate;
    private Calendar endDate;

    public TipEntry() {
        // initialize to 0 dollars
        ammount = 0.0;
        Calendar now = Calendar.getInstance();
        // initialize endDate to 6 hours before NOW
        startDate = Calendar.getInstance();
        startDate.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY) - 6);

        // initialize endDate to NOW
        endDate = Calendar.getInstance();
    }

    public static boolean isValidAmmount(double ammount) {
        double mantisa = ammount - ((int) ammount);
        double thousands = (mantisa * 100) - ((int)(mantisa * 100));
        return (thousands == 0.0) && (ammount >= 0);
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
        this.ammount = ammount;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public boolean isComplete() {
        return TipEntry.isValidAmmount(ammount) && TipEntry.isValidDate(startDate)
                && TipEntry.isValidDate(endDate) && startDate.before(endDate);
    }

    public double perHour() {
        return ammount / startDate.compareTo(endDate);
    }

}
