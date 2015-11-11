package com.flybottle.android.juniper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LauncherActivity extends Activity {
    private Juniper juniper = Juniper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
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
        getWeeklyGraph();
        getMonthlyGraph();
    }

    /**
     * Called when the user clicks the Floating Action Button
     * @param view The current view.
     */
    public void addEntry(View view) {
        Intent intent = new Intent(this, TipEntryActivity.class);
        startActivity(intent);
    }

    private GraphView getWeeklyGraph() {
        // Generate graph
        Date startOfWeek = DateUtils.getWeekStart();
        Date endOfWeek = DateUtils.getWeekEnd();
        /*FIXME The rendering of the appropriate Data is using 100% CPU.
         Even with getSpoofTips()
         It looks to be part of the .getX and .getY methods in TipEntry.
         This looks to be related to submitting unordered data on the X value.
         */

        //BarGraphSeries<TipEntry> series = getSpoofTips();
        BarGraphSeries<DataPoint> series = getSpoofData();
        GraphView graph = (GraphView) findViewById(R.id.weekGraph);
        graph.addSeries(series);

        // format series
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(getResources().getColor(R.color.graph_labels));
        series.setColor(getResources().getColor(R.color.graph_bars));
        series.setSpacing(50);
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface datapoint) {
                Toast.makeText(getApplicationContext(), "Series1 - Clicked: " + datapoint, Toast.LENGTH_SHORT).show();
            }
        });

        // format graph
        DateFormat dateFormat = new SimpleDateFormat("EEE");
        graph.getGridLabelRenderer().setLabelFormatter(
                new DateAsXAxisLabelFormatter(getApplicationContext(), dateFormat));
        graph.getGridLabelRenderer().setNumHorizontalLabels(7);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        graph.getGridLabelRenderer().setNumVerticalLabels(4);
        return graph;
    }

    private GraphView getMonthlyGraph() {
        Date startOfMonth = DateUtils.getMonthStart();
        Date endOfMonth = DateUtils.getMonthEnd();
        return generateGraph((GraphView) findViewById(R.id.monthGraph), startOfMonth, endOfMonth);
    }

    private BarGraphSeries<DataPoint> getSpoofData(){
        return new BarGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(new Date(2015, 8, 5), 105.0),
                new DataPoint(new Date(2015, 8, 6), 258.0),
                new DataPoint(new Date(2015, 8, 7), 59.0),
                new DataPoint(new Date(2015, 8, 8), 36.0),
                new DataPoint(new Date(2015, 8, 9), 185.0),
                new DataPoint(new Date(2015, 8, 10), 585.0),
                new DataPoint(new Date(2015, 8, 11), 33.0)
        });
    }

    private BarGraphSeries<TipEntry> getSpoofTips() {
        TipEntry[] tips = new TipEntry[7];

        for (int i=0; i<tips.length; i++) {
            TipEntry entry = new TipEntry();
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();

            // This appears to need to be ordered.
            start.set(Calendar.DAY_OF_YEAR, start.get(Calendar.DAY_OF_YEAR) - (7-i));
            end.set(Calendar.DAY_OF_YEAR, end.get(Calendar.DAY_OF_YEAR) - (7-i));
            end.set(Calendar.HOUR_OF_DAY, end.get(Calendar.HOUR_OF_DAY) - 5);

            entry.setStartDate(start);
            entry.setEndDate(end);
            entry.setAmount(i * 10.15);
            Log.d("BarGraphSeries", i + ": x=" + entry.getStartDate() + " y=" + entry.getY());
            tips[i] = entry;
        }
        return new BarGraphSeries<TipEntry>(tips);
    }

    private GraphView generateGraph(GraphView graph, Date start, Date end) {

        BarGraphSeries<DataPoint> series = getSpoofData();
        //BarGraphSeries<TipEntry> series = new BarGraphSeries<TipEntry>(juniper.getWeekAsArray());

        graph.addSeries(series);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        graph.getGridLabelRenderer().setNumVerticalLabels(4);
        series.setColor(getResources().getColor(R.color.graph_bars));
        series.setSpacing(50);
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface datapoint) {
                Toast.makeText(getApplicationContext(), "Series1 - Clicked: " + datapoint, Toast.LENGTH_SHORT).show();
            }
        });
        return graph;
    }
}