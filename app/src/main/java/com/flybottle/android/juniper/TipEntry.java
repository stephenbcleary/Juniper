package com.flybottle.android.juniper;

import com.jjoe64.graphview.series.DataPointInterface;

import java.util.Calendar;
import java.util.Date;


public class TipEntry implements Comparable<TipEntry>, DataPointInterface {
    private double amount;
    private Date startDate;
    private Date endDate;

    public TipEntry() {
        // initialize to 0 dollars
        amount = 0.0;
        Calendar now = Calendar.getInstance();
        // initialize endDate to 6 hours before NOW
        now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY) - 6);
        startDate = now.getTime();

        // initialize endDate to NOW
        endDate = Calendar.getInstance().getTime();
    }

    public static boolean isValidAmmount(double ammount) {
        double mantisa = ammount - ((int) ammount);
        double thousands = (mantisa * 100) - ((int)(mantisa * 100));
        return (thousands == 0.0) && (ammount >= 0);
    }

    public static boolean isValidDate(Calendar calendar) {
        return isValidDate(calendar.getTime());
    }

    public static boolean isValidDate(Date date) {
        Calendar now = Calendar.getInstance();
        return date.before(now.getTime());
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate.getTime();
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate.getTime();
    }

    public boolean isComplete() {
        return TipEntry.isValidAmmount(amount) && TipEntry.isValidDate(startDate)
                && TipEntry.isValidDate(endDate) && startDate.before(endDate);
    }

    public double perHour() {
        return amount / startDate.compareTo(endDate);
    }

    @Override
    public double getY() {
        return getAmount();
    }

    @Override
    public double getX() {
            return getStartDate().getTime();
    }

    @Override
    public String toString() {
        return "TipEntry{" +
                "amount=" + amount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
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
        return startDate.compareTo(otherTipEntry.getStartDate());
    }
}
