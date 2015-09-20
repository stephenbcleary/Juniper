package com.flybottle.android.juniper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class LauncherActivity extends Activity {
    private RecyclerView tipListRecyclerView;
    private RecyclerView.Adapter tipListAdapter;
    private RecyclerView.LayoutManager tipListLayoutManager;
    private Juniper juniper = Juniper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        tipListRecyclerView = (RecyclerView) findViewById(R.id.tip_list_recycler_view);

        tipListRecyclerView.setHasFixedSize(true);

        tipListLayoutManager = new LinearLayoutManager(this);
        tipListRecyclerView.setLayoutManager(tipListLayoutManager);

        tipListAdapter = new TipsAdapter(juniper.getTipList());
        tipListRecyclerView.setAdapter(tipListAdapter);

        updateStats();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_launcher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateStats();
    }

    /**
     * Called when the user clicks the Floating Action Button
     * @param view The current view.
     */
    public void addEntry(View view) {
        Intent intent = new Intent(this, TipEntryActivity.class);
        startActivity(intent);
    }

    private void updateStats() {
        NumberFormat formatter = new DecimalFormat("#0.00");

        TextView view = (TextView)findViewById(R.id.weekly_stats_field);
        view.setText("$" + formatter.format(juniper.getWeekTotal()));

        view = (TextView)findViewById(R.id.week_best_day);
        view.setText("$" + formatter.format(juniper.getBestDayThisWeek()));

        view = (TextView)findViewById(R.id.week_worst_day);
        view.setText("$" + formatter.format(juniper.getWorstDayThisWeek()));

        view = (TextView)findViewById(R.id.week_average_day);
        view.setText("$" + formatter.format(juniper.getAverageDayThisWeek()));

        view = (TextView)findViewById(R.id.month_best_day);
        view.setText("$" + formatter.format(juniper.getBestDayThisMonth()));

        view = (TextView)findViewById(R.id.month_worst_day);
        view.setText("$" + formatter.format(juniper.getWorstDayThisMonth()));

        view = (TextView)findViewById(R.id.month_average_day);
        view.setText("$" + formatter.format(juniper.getAverageDayThisMonth()));

        view = (TextView)findViewById(R.id.month_stats_field);
        view.setText("$" + formatter.format(juniper.getMonthTotal()));
    }
}