package com.mylocations;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.melnykov.fab.FloatingActionButton;
import com.mylocations.database.DatabaseHandler;
import com.mylocations.places.PlacesController;
import com.mylocations.places.PlacesUtil;
import com.mylocations.ratings.RatingDataAdapter;
import com.mylocations.ratings.RatingDataModel;
import com.mylocations.utils.DividerItemDecoration;

import java.util.ArrayList;

/**
 * RatingActivity in der alle Bewertungen angezeigt werden und zum PlacePicker + Settings
 * navigiert werden kann
 *
 * Created by Tobias Feldmann on 29.04.15.
 */
public class RatingActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private RatingDataAdapter mAdapter;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary)));
        bar.setTitle("Bewertungen");
        bar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        Spannable text = new SpannableString(bar.getTitle());
        text.setSpan(new ForegroundColorSpan(Color.WHITE), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        bar.setTitle(text);

        PlacesController.getInstance().setMainActivity(this);
        databaseHandler = new DatabaseHandler(this);
        if(databaseHandler.isEmpty())
        {
            PlacesUtil.showPlacesPicker();
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacesUtil.showPlacesPicker();
            }
        });
        fab.attachToRecyclerView(mRecyclerView);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(llm);

        mAdapter = new RatingDataAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.reloadData();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        PlacesUtil.handlePlacesResult(requestCode, resultCode, data, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
