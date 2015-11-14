package com.flybottle.android.juniper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class LauncherActivity extends Activity {
    private Juniper juniper = Juniper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        createWeeklyGraph();
        createMonthlyGraph();
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
        // TODO Should update the "This Week" label to state the actual week range.
        updateWeeklyGraph();
        updateMonthlyGraph();
        Log.d("onResume", "Called");
    }

    /**
     * Called when the user clicks the Floating Action Button
     */
    public void addEntry(View view){
        openTipEntryActivity(null);
    }

    public void openTipEntryActivity(TipEntry entry) {
        Intent intent = new Intent(this, TipEntryActivity.class);
        if (entry != null) {
            intent.putExtra("entryDate", entry.getStartDate());
        }
        startActivity(intent);
    }

    private GraphView createWeeklyGraph() {
        GraphView graph = getStyledWeeklyGraph();
        graph.addSeries(getWeeklySeries());
        return graph;
    }

    private BarGraphSeries<TipEntry> getWeeklySeries(){
        BarGraphSeries<TipEntry> series = new BarGraphSeries<TipEntry>(juniper.getWeekAsArray());
        series.setDrawValuesOnTop(true);
        series.setSpacing(50);
        series.setValuesOnTopColor(getResources().getColor(R.color.graph_labels));
        series.setColor(getResources().getColor(R.color.graph_bars));
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface datapoint) {
                //Toast.makeText(getApplicationContext(), "Series1 - Clicked: " + datapoint, Toast.LENGTH_SHORT).show();
                openTipEntryActivity((TipEntry) datapoint);
            }
        });
        return series;
    }

    private GraphView getStyledWeeklyGraph(){
        GraphView graph = (GraphView) findViewById(R.id.weekGraph);
        // format graph
        DateFormat dateFormat = new SimpleDateFormat("EEE"); // EEE
        //graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext(), dateFormat));
        graph.getGridLabelRenderer().setNumHorizontalLabels(7);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        graph.getGridLabelRenderer().setNumVerticalLabels(4);
        graph.getGridLabelRenderer().setHighlightZeroLines(false);
        return graph;
    }

    private void updateWeeklyGraph() {
        GraphView graph = (GraphView) findViewById(R.id.weekGraph);
        graph.removeAllSeries();
        createWeeklyGraph();
    }

    private GraphView createMonthlyGraph() {
        GraphView graph = getStyledMonthlyGraph();
        graph.addSeries(getMonthlySeries());
        return graph;
    }

    private BarGraphSeries<TipEntry> getMonthlySeries(){
        BarGraphSeries<TipEntry> series = new BarGraphSeries<TipEntry>(juniper.getMonthAsArray());
        series.setColor(getResources().getColor(R.color.graph_bars));
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface datapoint) {
                //Toast.makeText(getApplicationContext(), "Series1 - Clicked: " + datapoint, Toast.LENGTH_SHORT).show();
                openTipEntryActivity((TipEntry) datapoint);
            }
        });
        return series;
    }

    private GraphView getStyledMonthlyGraph() {
        GraphView graph = (GraphView) findViewById(R.id.monthGraph);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        graph.getGridLabelRenderer().setNumVerticalLabels(4);
        graph.getGridLabelRenderer().setHighlightZeroLines(false);
        return graph;
    }

    private void updateMonthlyGraph() {
        GraphView graph = (GraphView) findViewById(R.id.monthGraph);
        graph.removeAllSeries();
        createMonthlyGraph();
    }
}