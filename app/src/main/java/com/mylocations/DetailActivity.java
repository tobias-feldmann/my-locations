package com.mylocations;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceTypes;
import com.google.android.gms.maps.model.LatLngBounds;
import com.mylocations.places.PlacesController;
import com.mylocations.places.PlacesUtil;


public class DetailActivity extends ActionBarActivity {

    private PlacesController placesController;
    private Place currentPlace;

    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary)));
        bar.setTitle("Details");
//        Spannable text = new SpannableString(bar.getTitle());
//        text.setSpan(new ForegroundColorSpan(Color.BLACK), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        bar.setTitle(text);
//        bar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        bar.setDisplayHomeAsUpEnabled(true);
        placesController = PlacesController.getInstance();
        currentPlace = placesController.getPlace();

//        name = (TextView) findViewById(R.id.name);
//        name.setText(placesController.getPlace().getName());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home)
        {
            PlacesUtil.showPlacesPicker(placesController.getBounds());
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
