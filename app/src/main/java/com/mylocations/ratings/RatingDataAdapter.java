package com.mylocations.ratings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.daimajia.swipe.SwipeLayout;
import com.mylocations.DetailActivity;
import com.mylocations.R;
import com.mylocations.database.DatabaseHandler;
import com.mylocations.places.PlacesUtil;
import com.mylocations.utils.RatingComparators;

import java.util.ArrayList;
import java.util.Collections;

/**
 * DataAdapter welcher die Bewertungsobjekte im Recyclerview verwaltet
 *
 * Created by Tobias Feldmann on 23.12.14.
 */
public class RatingDataAdapter extends RecyclerView.Adapter<RatingDataAdapter.ViewHolder> {

    private ArrayList<RatingDataModel> mDataset;
    private DatabaseHandler databaseHandler;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mNameTextView;
        public TextView mTypeTextView;
        public TextView mAddressTextView;
        public ImageView mImageView;
        public ViewHolder(View v) {
            super(v);
            mNameTextView = (TextView)v.findViewById(R.id.location_name);
            mTypeTextView = (TextView)v.findViewById(R.id.location_type);
            mAddressTextView = (TextView)v.findViewById(R.id.loction_address);
            mImageView = (ImageView)v.findViewById(R.id.imageView);
        }
    }

    public RatingDataAdapter(Context context) {
        this.context = context;
        databaseHandler = new DatabaseHandler(context);
        mDataset = new ArrayList<>();
    }

    @Override
    public RatingDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_list_item, parent, false);
        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(R.id.swipeLayout);
        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onStartOpen(SwipeLayout layout) {
                layout.close(true);
            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = (String) v.getTag();

            }
        });

        ImageView deleteImage = (ImageView) v.findViewById(R.id.remove);
        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = (String)((View)(v.getParent().getParent())).getTag();
                databaseHandler.deleteRating(id);
                swipeLayout.close();
                reloadData();

            }
        });

        ImageView locateImage = (ImageView) v.findViewById(R.id.position);
        locateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = (String)((View)(v.getParent().getParent())).getTag();
                RatingDataModel model = databaseHandler.getRating(id);
                PlacesUtil.showPlacesPicker(model.getBounds());
                swipeLayout.close();

            }
        });

        ImageView openImage = (ImageView) v.findViewById(R.id.open);
        openImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = (String)((View)(v.getParent().getParent())).getTag();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.RATING_ID, id);
                context.startActivity(intent);
                swipeLayout.close();

            }
        });

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        RatingDataModel dataModel = mDataset.get(position);
        holder.mNameTextView.setText(dataModel.getName());
        holder.mNameTextView.setTypeface(Typeface.DEFAULT_BOLD);
        holder.mTypeTextView.setText(dataModel.getPlaceTypes());
        holder.mAddressTextView.setText(dataModel.getAddress());
        holder.itemView.setTag(dataModel.getId());
        switch (dataModel.getPrivateRating())
        {
            case 0 : holder.mImageView.setImageResource(R.drawable.ampel_green); break;
            case 1 : holder.mImageView.setImageResource(R.drawable.ampel_yellow); break;
            case 2 : holder.mImageView.setImageResource(R.drawable.ampel_red); break;
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    /**
     * Läd die Bewertungsobjekte aus der Datenbank neu
     */
    public void reloadData()
    {
        mDataset = sortRatings(filterRatings(databaseHandler.getAllRatings()));
        this.notifyDataSetChanged();
    }

    /**
     * Filtert die Bewertungen nach dem eingestellten Filter
     *
     * @param ratings die Bewertungsobjekte
     * @return die gefilterten Bewertungsobjekte
     */
    private ArrayList<RatingDataModel> filterRatings(ArrayList<RatingDataModel> ratings)
    {
        ArrayList<RatingDataModel> filteredRatings = new ArrayList<>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String filter = prefs.getString("filter", "Alle");

        if(filter == null || filter.equals("Alle"))
            return ratings;

        for(RatingDataModel rating : ratings)
        {
            if(rating.getPlaceTypes().contains(filter))
               filteredRatings.add(rating);
        }
        return filteredRatings;
    }

    /**
     * Sortiert die Bewertungen nach der eingestellten Sortierung
     *
     * @param ratings die Bewertungsobjekte
     * @return die sortierten Bewertungsobjekte
     */
    private ArrayList<RatingDataModel> sortRatings(ArrayList<RatingDataModel> ratings)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String filter = prefs.getString("sort", "Locationbewertung");

        if(filter == null)
            return ratings;

        if(filter.equals("Locationbewertung"))
        {
            Collections.sort(ratings, RatingComparators.RatingValueComparator);
        }
        else if (filter.equals("Locationname"))
        {
            Collections.sort(ratings, RatingComparators.RatingNameComparator);
        }
        else if (filter.equals("Locationtyp"))
        {
            Collections.sort(ratings, RatingComparators.RatingTypComparator);
        }

        return ratings;
    }
}