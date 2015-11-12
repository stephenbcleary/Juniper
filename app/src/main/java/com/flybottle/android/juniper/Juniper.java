package com.flybottle.android.juniper;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.Duration;

/**
 * Created by alex on 11/05/15.
 */
// TODO This should probably establish other services on multiple threads.
public class Juniper {
    private static Juniper instance = null;
    private static List<TipEntry> tipsList = new ArrayList<TipEntry>();

    // FIXME This shouldn't need to generate example data.
    static {
        for (int i=0; i<30; i++) {
            TipEntry entry = new TipEntry();
            entry.setStartDate(DateTime.now().minusHours(5).minusDays(i));
            entry.setEndDate(DateTime.now().minusDays(i));
            entry.setAmount((i % 7) * 10.15);
            tipsList.add(entry);
        }
    }

    private Juniper() {
        // Enforce singleton.
    }

    public static Juniper getInstance() {
        if (instance == null) {
            instance = new Juniper();
        }
        return instance;
    }

    public void addTip(TipEntry entry) {
        // FIXME: This should add to a database.
        tipsList.add(entry);
    }

    public List<TipEntry> getWeek() {
        return getTipsWithinInterval(DateUtils.getWeekStart(), DateUtils.getWeekEnd());
    }

    public List<TipEntry> getMonth() {
        return getTipsWithinInterval(DateUtils.getMonthStart(), DateUtils.getMonthEnd());
    }

    public List<TipEntry> getTipsWithinInterval(Interval dateInterval) {
        // Create an Array of length interval.
        int days = dateInterval.toDuration().toStandardDays().getDays() + 1; // +1 for inclusive.
        TipEntry[] entryList = new TipEntry[days];

        // Add tips to the element in the list that corresponds with the date.
        for (TipEntry entry : tipsList) {
            if (dateInterval.contains(entry.getStartDate())) {
                // Interval of start of array to start of entry
                Interval distance = new Interval(dateInterval.getStart(), entry.getStartDate());
                // insert entry at days from start.
                entryList[distance.toDuration().toStandardDays().getDays()] = entry;
            }
        }

        //Collections.sort(entryList);
        for (int i=0; i<entryList.length; i++) {
            if (entryList[i] == null) {
                // set empty entry
                TipEntry emptyEntry = new TipEntry();
                Interval emptyDate = new Interval(dateInterval.getStart().plusDays(i),
                                                  dateInterval.getStart().plusDays(i));
                emptyEntry.setDateInterval(emptyDate);
                // add entry to empty day.
                entryList[i] = emptyEntry;
            }
        }
        ArrayList<TipEntry> resultList = new ArrayList<TipEntry>();
        for (TipEntry entry : entryList) {
            resultList.add(entry);
        }
        return resultList;
    }

    /**
     * This method returns all the tips within a given date range (inclusive).  If there are days
     * with no entries these are created (but not saved to the Database).
     * @param start The start date of the interval.
     * @param end The end date of the interval.
     * @return A List of seven TipEntries, one for each day.
     */
    public List<TipEntry> getTipsWithinInterval(DateTime start, DateTime end) {
        return getTipsWithinInterval(new Interval(start, end));
    }

    public TipEntry[] getWeekAsArray() {
        List<TipEntry> tips = getWeek();
        return tips.toArray(new TipEntry[tips.size()]);
    }

    public TipEntry[] getMonthAsArray() {
        List<TipEntry> tips = getMonth();
        return tips.toArray(new TipEntry[tips.size()]);
    }

}
