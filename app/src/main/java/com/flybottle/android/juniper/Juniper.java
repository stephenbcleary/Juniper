package com.flybottle.android.juniper;

import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by alex on 11/05/15.
 */
// TODO This should probably establish other services on multiple threads.
public class Juniper {
    private static Juniper instance = null;
    private static List<TipEntry> tipsList = new ArrayList<TipEntry>();

    // FIXME This shouldn't need to generate example data.
    static {
        TipEntry entry = new TipEntry();
        entry.setStartDate(Calendar.getInstance());
        entry.setEndDate(Calendar.getInstance());
        entry.setAmount(258.85D);
        tipsList.add(entry);

        for (int i=0; i<30; i++) {
            TipEntry entry2 = new TipEntry();
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();

            start.set(Calendar.DAY_OF_YEAR, start.get(Calendar.DAY_OF_YEAR) - i);
            end.set(Calendar.DAY_OF_YEAR, end.get(Calendar.DAY_OF_YEAR) - i);
            end.set(Calendar.HOUR_OF_DAY, end.get(Calendar.HOUR_OF_DAY) - 5);

            entry2.setStartDate(start);
            entry2.setEndDate(end);
            entry2.setAmount((i % 7) * 10.15);
            tipsList.add(entry2);
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
        Date startOfWeek = DateUtils.getWeekStart();
        Date endOfWeek = DateUtils.getWeekEnd();
        return getTimeInterval(startOfWeek, endOfWeek);
    }

    public List<TipEntry> getMonth() {
        Date startOfMonth = DateUtils.getMonthStart();
        Date endOfMonth = DateUtils.getMonthEnd();
        return getTimeInterval(startOfMonth, endOfMonth);
    }

    public List<TipEntry> getTimeInterval(Date start, Date end) {
        List<TipEntry> entryList = new ArrayList<TipEntry>();
        for (TipEntry entry : tipsList) {
            if (entry.getStartDate().before(end) && start.before(entry.getStartDate())) {
                entryList.add(entry);
            }
        }
        Collections.sort(entryList);
        return entryList;
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
