package com.flybottle.android.juniper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

public class LauncherActivity extends Activity {
    private RecyclerView tipsRecyclerView;
    private RecyclerView.Adapter tipsAdapter;
    private RecyclerView.LayoutManager tipsLayoutManager;
    private String[] tipsDataset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tipsDataset = makeStringsArray();
        setContentView(R.layout.activity_launcher);
        tipsRecyclerView = (RecyclerView) findViewById(R.id.tips_recycler_view);

        // user this setting to improve performance if you that changes
        // in content do not change the layout size of the RecyclerView
        tipsRecyclerView.setHasFixedSize(true);

        // Use a linear layout manger
        tipsLayoutManager = new LinearLayoutManager(this);
        tipsRecyclerView.setLayoutManager(tipsLayoutManager);

        // specify an adapter
        tipsAdapter = new TipsAdapter(tipsDataset);
        tipsRecyclerView.setAdapter(tipsAdapter);


    }

    /*
    TODO: Add in onResume, as we will be resuming this from the 'add Tips' page.
     */

    public String[] makeStringsArray() {
        String[] result = new String[100];
        for (int i=0; i<result.length; i++) {
            result[i] = "Tips Date " + i + ": $" + ((i%4) * 10);
        }
        return result;
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
}
