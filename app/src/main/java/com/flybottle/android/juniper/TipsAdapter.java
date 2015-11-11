package com.flybottle.android.juniper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * This adapter manages the communication between the tips datasource and the tips layout.
 * Created by alex on 09/05/15.
 */
public class TipsAdapter  extends RecyclerView.Adapter<TipsAdapter.ViewHolder> {
    private List<TipEntry> tipsDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more that one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Each data item is just a String in this case
        //public TextView tipsTextView;
        public TextView tipDate;
        public TextView tipPerHour;

        public ViewHolder(View view) {
            super(view);
            //tipsTextView = (TextView) view;
            tipDate = (TextView) view.findViewById(R.id.tip_list_date);
            tipPerHour = (TextView) view.findViewById(R.id.tip_list_per_hour);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TipsAdapter(List<TipEntry> tipsDataset) {
        this.tipsDataset = tipsDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TipsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.tip_text_view, parent, false);

        // set the view's size, margins, padding and layout params
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        DateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy");
        holder.tipDate.setText(formatter.format(tipsDataset.get(position).getStartDate()));

        NumberFormat numberFormat = new DecimalFormat("#0.00");
        holder.tipPerHour.setText(numberFormat.format(tipsDataset.get(position).perHour()) + "/hr");

        //holder.tipsTextView.setText(entry);
    }

    // Return the size of yoru dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tipsDataset.size();
    }
}
