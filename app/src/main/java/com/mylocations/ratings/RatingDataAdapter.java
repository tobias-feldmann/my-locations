package com.mylocations.ratings;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.mylocations.R;

import java.util.ArrayList;

/**
 * Created by Tobias Feldmann on 23.12.14.
 */
public class RatingDataAdapter extends RecyclerView.Adapter<RatingDataAdapter.ViewHolder> {

    private ArrayList<RatingDataModel> mDataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mNameTextView;
        public TextView mTypeTextView;
        public TextView mAddressTextView;
        public ImageView mImageView;
        public ImageView mLocationImageView;
        public ViewHolder(View v) {
            super(v);
            mNameTextView = (TextView)v.findViewById(R.id.location_name);
            mTypeTextView = (TextView)v.findViewById(R.id.location_type);
            mAddressTextView = (TextView)v.findViewById(R.id.loction_address);
            mImageView = (ImageView)v.findViewById(R.id.imageView);
            mLocationImageView = (ImageView)v.findViewById(R.id.locationImage);
        }
    }

    public RatingDataAdapter(ArrayList<RatingDataModel> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RatingDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        RatingDataModel dataModel = mDataset.get(position);
        holder.mNameTextView.setText(dataModel.getName());
        holder.mNameTextView.setTypeface(Typeface.DEFAULT_BOLD);
        holder.mTypeTextView.setText(dataModel.getComment());
        holder.mAddressTextView.setText(dataModel.getAddress());
//        holder.mDateTimeTextView.setText(dataModel.getEventDateTime());
//        holder.mDistanceTextView.setText(dataModel.getDistance());
//        holder.mPeopleCountTextView.setText(dataModel.getPeopleCount());


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}