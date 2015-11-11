package com.flybottle.android.juniper;

import com.jjoe64.graphview.series.DataPointInterface;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;


public class TipEntry implements Comparable<TipEntry>, DataPointInterface {
    private double amount;
    private Interval dateInterval;

    public TipEntry() {
        // initialize to 0 dollars
        amount = 0.0;
        // initialize interval to 6 hours prior to now and now.
        dateInterval = new Interval(DateTime.now().minusHours(6), DateTime.now());
    }

    // Getters & Setters
    public Double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (isValidAmmount(amount)) {
            this.amount = amount;
        }
    }

    public Interval getDateInterval() {
        return dateInterval;
    }

    public void setDateInterval(Interval dateInterval) {
        this.dateInterval = dateInterval;
    }

    public DateTime getStartDate() {
        return dateInterval.getStart();
    }

    public void setStartDate(DateTime startDate) {
        if (startDate.isBefore(dateInterval.getEnd())) {
            dateInterval = dateInterval.withStart(startDate);
        }
    }

    public DateTime getEndDate() {
        return dateInterval.getEnd();
    }

    public void setEndDate(DateTime endDate) {
        if (endDate.isAfter(dateInterval.getStart()) && endDate.isBefore(DateTime.now())) {
            dateInterval = dateInterval.withEnd(endDate);
        }
    }

    // Class Methods
    public static boolean isValidAmmount(double ammount) {
        double mantisa = ammount - ((int) ammount);
        double thousands = (mantisa * 100) - ((int)(mantisa * 100));
        //return (thousands == 0.0) && (ammount >= 0);
        return true;
    }

    public boolean isComplete() {
        return TipEntry.isValidAmmount(amount);
    }

    public double perHour() {
        return amount / dateInterval.toDuration().toPeriod().getHours();
    }

    @Override
    public double getY() {
        return getAmount();
    }

    @Override
    public double getX() {
            return (double) dateInterval.getStartMillis();
    }

    @Override
    public String toString() {
        return "TipEntry{" +
                "amount=" + amount +
                ", dateInterval=" + dateInterval +
                '}';
    }

    /**
     * Objects of this class are ordered by start date.  This method compares to objects for
     * ordering.
     * @param otherTipEntry The Object to be compared to.
     * @return A positive integer if thisTip > otherTip.  Zero if equal.  negative if <.
     */
    @Override
    public int compareTo(TipEntry otherTipEntry) {
        return getStartDate().compareTo(otherTipEntry.getStartDate());
    }
}
