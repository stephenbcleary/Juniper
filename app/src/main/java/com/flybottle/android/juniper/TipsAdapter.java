package com.flybottle.android.juniper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * This adapter manages the communication between the tips datasource and the tips layout.
 * Created by alex on 09/05/15.
 */
public class TipsAdapter  extends RecyclerView.Adapter<TipsAdapter.ViewHolder> {
    private String[] tipsDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more that one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Each data item is just a String in this case
        public TextView tipsTextView;
        public ViewHolder(View view) {
            super(view);
            tipsTextView = (TextView) view;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TipsAdapter(String[] tipsDataset) {
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
        holder.tipsTextView.setText(tipsDataset[position]);
    }

    // Return the size of yoru dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tipsDataset.length;
    }
}
