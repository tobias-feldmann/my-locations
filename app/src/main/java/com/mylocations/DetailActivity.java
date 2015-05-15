package com.mylocations;

import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.mylocations.places.PlacesController;
import com.mylocations.places.PlacesUtil;
import com.mylocations.utils.LocationTypes;

import java.util.List;


public class DetailActivity extends ActionBarActivity {

    private PlacesController placesController;
    private Place currentPlace;

    private TextView name;
    private TextView type;
    private TextView address;
    private TextView website;
    private TextView phone;

    private Spinner spinner;
    private ImageView ratingImage;

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

        name = (TextView) findViewById(R.id.detail_name);
        name.setText(placesController.getPlace().getName());

        address = (TextView) findViewById(R.id.detail_address);
        address.setText(placesController.getPlace().getAddress());

        phone = (TextView) findViewById(R.id.detail_phone);
        phone.setText(placesController.getPlace().getPhoneNumber());

        type = (TextView) findViewById(R.id.detail_type);
        type.setText(typesToString(placesController.getPlace().getPlaceTypes()));

        website = (TextView) findViewById(R.id.detail_website);

        Uri websiteUri = placesController.getPlace().getWebsiteUri();
        website.setText(websiteUri == null ? "" : websiteUri.toString());

        ratingImage = (ImageView) findViewById(R.id.imageView);
        ratingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
            }
        });

        spinner = (Spinner) findViewById(R.id.rating_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.rating_values, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position)
                {
                    case 0 : ratingImage.setImageResource(R.drawable.ampel_green); break;
                    case 1 : ratingImage.setImageResource(R.drawable.ampel_yellow); break;
                    case 2 : ratingImage.setImageResource(R.drawable.ampel_red); break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setSelection(0,true);

    }

    private String typesToString(List<Integer> types)
    {
        String returnString = "";
        for(int a = 0; a < types.size(); a++)
        {
            returnString += LocationTypes.getType(types.get(a));
            if(!(a >= types.size() - 1))
            {
                returnString += ", ";
            }
        }
        return returnString;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
        else if(id == R.id.action_save)
        {

        }

        return super.onOptionsItemSelected(item);
    }
}
