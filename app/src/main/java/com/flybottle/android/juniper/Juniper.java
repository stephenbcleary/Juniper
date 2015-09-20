package com.flybottle.android.juniper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by alex on 11/05/15.
 */
public class Juniper {
    private static Juniper instance = null;
    private static List<TipEntry> tipsList = new ArrayList<TipEntry>();

    static {
        TipEntry entry = new TipEntry();
        entry.setStartDate(Calendar.getInstance());
        entry.setEndDate(Calendar.getInstance());
        entry.setAmmount(258.85D);
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
            entry2.setAmmount((i % 7) * 10.15);
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
        tipsList.add(entry);
    }

    public static TipEntry getTip(long id) {
        for (TipEntry entry : tipsList) {
            if (entry.getId() == id) {
                return entry;
            }
        }
        return null;
    }

    public List<TipEntry> getTipList() {
        return tipsList;
    }

    public double getWeekTotal() {
        Calendar now = Calendar.getInstance();
        Calendar past = Calendar.getInstance();
        past.set(Calendar.DAY_OF_YEAR, now.get(Calendar.DAY_OF_YEAR) - 7);
        double total = 0.0;
        for (TipEntry entry : tipsList) {
            if (entry.getStartDate().before(now) && past.before(entry.getStartDate())) {
                total += entry.getAmmount();
            }
        }
        return total;
    }

    public double getBestDayThisWeek() {
        Calendar now = Calendar.getInstance();
        Calendar past = Calendar.getInstance();
        past.set(Calendar.DAY_OF_YEAR, now.get(Calendar.DAY_OF_YEAR) - 7);
        double best = 0.0;
        for (TipEntry entry : tipsList) {
            if (entry.getStartDate().before(now) && past.before(entry.getStartDate())) {
                if (best < entry.getAmmount()) {
                    best = entry.getAmmount();
                }
            }
        }
        return best;
    }

    public double getWorstDayThisWeek() {
        Calendar now = Calendar.getInstance();
        Calendar past = Calendar.getInstance();
        past.set(Calendar.DAY_OF_YEAR, now.get(Calendar.DAY_OF_YEAR) - 7);
        List<TipEntry> thisWeek = new ArrayList<TipEntry>();
        for (TipEntry entry : tipsList) {
            if (entry.getStartDate().before(now) && past.before(entry.getStartDate())) {
                thisWeek.add(entry);
            }
        }
        double worst = thisWeek.get(0).getAmmount();
        for (TipEntry entry : thisWeek) {
            if (worst > entry.getAmmount()) {
                worst = entry.getAmmount();
            }
        }
        return worst;
    }

    public double getAverageDayThisWeek() {
        Calendar now = Calendar.getInstance();
        Calendar past = Calendar.getInstance();
        past.set(Calendar.DAY_OF_YEAR, now.get(Calendar.DAY_OF_YEAR) - 7);
        int daysWorked = 0;
        double total = 0.0;
        for (TipEntry entry : tipsList) {
            if (entry.getStartDate().before(now) && past.before(entry.getStartDate())) {
                daysWorked++;
                total += entry.getAmmount();
            }
        }
        return total / daysWorked;
    }

    public double getMonthTotal() {
        Calendar now = Calendar.getInstance();
        Calendar past = Calendar.getInstance();
        past.set(Calendar.MONTH, now.get(Calendar.MONTH) - 1);
        double total = 0.0;
        for (TipEntry entry : tipsList) {
            if (entry.getStartDate().before(now) && past.before(entry.getStartDate())) {
                total += entry.getAmmount();
            }
        }
        return total;
    }

    public double getBestDayThisMonth() {
        Calendar now = Calendar.getInstance();
        Calendar past = Calendar.getInstance();
        past.set(Calendar.MONTH, now.get(Calendar.MONTH) - 1);
        double best = 0.0;
        for (TipEntry entry : tipsList) {
            if (entry.getStartDate().before(now) && past.before(entry.getStartDate())) {
                if (best < entry.getAmmount()) {
                    best = entry.getAmmount();
                }
            }
        }
        return best;
    }

    public double getWorstDayThisMonth() {
        Calendar now = Calendar.getInstance();
        Calendar past = Calendar.getInstance();
        past.set(Calendar.MONTH, now.get(Calendar.MONTH) - 1);
        List<TipEntry> thisWeek = new ArrayList<TipEntry>();
        for (TipEntry entry : tipsList) {
            if (entry.getStartDate().before(now) && past.before(entry.getStartDate())) {
                thisWeek.add(entry);
            }
        }
        double worst = thisWeek.get(0).getAmmount();
        for (TipEntry entry : thisWeek) {
            if (worst > entry.getAmmount()) {
                worst = entry.getAmmount();
            }
        }
        return worst;
    }

    public double getAverageDayThisMonth() {
        Calendar now = Calendar.getInstance();
        Calendar past = Calendar.getInstance();
        past.set(Calendar.MONTH, now.get(Calendar.MONTH) - 1);
        int daysWorked = 0;
        double total = 0.0;
        for (TipEntry entry : tipsList) {
            if (entry.getStartDate().before(now) && past.before(entry.getStartDate())) {
                daysWorked++;
                total += entry.getAmmount();
            }
        }
        return total / daysWorked;
    }

}
