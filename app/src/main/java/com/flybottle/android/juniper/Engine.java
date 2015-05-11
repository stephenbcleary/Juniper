package com.flybottle.android.juniper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 11/05/15.
 */
public class Engine {
    private static List<TipEntry> tipsList = new ArrayList<TipEntry>();

    private Engine() {
        // Enforce singleton.
    }

    public static void addTip(TipEntry entry) {
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

    public static List<TipEntry> getTipList() {
        return tipsList;
    }

}
