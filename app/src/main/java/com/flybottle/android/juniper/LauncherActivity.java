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
        BarGraphSeries<TipEntry> series = new BarGraphSeries<TipEntry>(juniper.getWeekAsArray());
        GraphView graph = (GraphView) findViewById(R.id.weekGraph);
        graph.addSeries(series);

        // format series
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(getResources().getColor(R.color.graph_labels));
        series.setColor(getResources().getColor(R.color.graph_bars));
        series.setSpacing(30);
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
        BarGraphSeries<TipEntry> series = new BarGraphSeries<TipEntry>(juniper.getMonthAsArray());
        GraphView graph = (GraphView) findViewById(R.id.monthGraph);
        graph.addSeries(series);
        return graph;
    }


}