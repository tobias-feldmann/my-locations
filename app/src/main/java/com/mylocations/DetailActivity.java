package com.mylocations;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.mylocations.database.DatabaseHandler;
import com.mylocations.places.PlacesController;
import com.mylocations.places.PlacesUtil;
import com.mylocations.ratings.RatingDataAdapter;
import com.mylocations.ratings.RatingDataModel;
import com.mylocations.utils.LocationTypes;

import java.util.List;


public class DetailActivity extends ActionBarActivity {

    private PlacesController placesController;
    private Place currentPlace;
    private RatingDataModel ratingDataModel;

    private TextView name;
    private TextView type;
    private TextView address;
    private TextView website;
    private TextView phone;

    private EditText commentEdit;
    private Spinner spinner;
    private ImageView ratingImage;
    private DatabaseHandler databaseHandler;
    private boolean isFromMain;

    public static String RATING_ID = "rating_id";

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

        databaseHandler = new DatabaseHandler(this);
        placesController = PlacesController.getInstance();
        currentPlace = placesController.getPlace();

        String id = null;
        Intent intent = getIntent();
        if(intent != null)
          id = intent.getStringExtra(RATING_ID);

        if(id != null)
        {
            ratingDataModel = databaseHandler.getRating(id);
            isFromMain = true;
        }
        else
        {
            ratingDataModel = databaseHandler.getRating(currentPlace.getId());
            isFromMain = false;
        }


        if(ratingDataModel == null)
        {
            ratingDataModel = new RatingDataModel();
            ratingDataModel.setId(currentPlace.getId());
            ratingDataModel.setName(currentPlace.getName().toString());
            ratingDataModel.setAddress(currentPlace.getAddress().toString());
            ratingDataModel.setPhoneNumber(currentPlace.getPhoneNumber().toString());
            ratingDataModel.setPlaceTypes(typesToString(currentPlace.getPlaceTypes()));
            Uri websiteUri = currentPlace.getWebsiteUri();
            ratingDataModel.setWebsite(websiteUri == null ? "" : websiteUri.toString());
            ratingDataModel.setNorthEastLatitude(placesController.getBounds().northeast.latitude);
            ratingDataModel.setNorthEastLongitude(placesController.getBounds().northeast.longitude);
            ratingDataModel.setSouthWestLatitude(placesController.getBounds().southwest.latitude);
            ratingDataModel.setSouthWestLongitude(placesController.getBounds().southwest.longitude);
            ratingDataModel.setLongitude(currentPlace.getLatLng().longitude);
            ratingDataModel.setLatitude(currentPlace.getLatLng().latitude);
            ratingDataModel.setPrivateRating(0);
            ratingDataModel.setComment("");
        }

        name = (TextView) findViewById(R.id.detail_name);
        name.setText(ratingDataModel.getName());

        address = (TextView) findViewById(R.id.detail_address);
        address.setText(ratingDataModel.getAddress());

        phone = (TextView) findViewById(R.id.detail_phone);
        phone.setText(ratingDataModel.getPhoneNumber());

        type = (TextView) findViewById(R.id.detail_type);
        type.setText(ratingDataModel.getPlaceTypes());

        website = (TextView) findViewById(R.id.detail_website);
        website.setText(ratingDataModel.getWebsite());

        ratingImage = (ImageView) findViewById(R.id.imageView);
        ratingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
            }
        });
        commentEdit = (EditText) findViewById(R.id.commentEdit);
        commentEdit.setText(ratingDataModel.getComment());

        spinner = (Spinner) findViewById(R.id.rating_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.rating_values, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        ratingImage.setImageResource(R.drawable.ampel_green);
                        break;
                    case 1:
                        ratingImage.setImageResource(R.drawable.ampel_yellow);
                        break;
                    case 2:
                        ratingImage.setImageResource(R.drawable.ampel_red);
                        break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setSelection(ratingDataModel.getPrivateRating(), true);

        LinearLayout detailLayout = (LinearLayout) findViewById(R.id.detailLayout);
        detailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableCommentEdit();
            }
        });

        CardView cardView = (CardView) findViewById(R.id.card_details);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableCommentEdit();
            }
        });

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

    private void saveObject()
    {
        ratingDataModel.setComment(commentEdit.getText().toString());
        ratingDataModel.setPrivateRating(spinner.getSelectedItemPosition());
        ratingDataModel.setTimestamp(System.currentTimeMillis());

        if(databaseHandler.existRating(ratingDataModel.getId()))
        {
            databaseHandler.updateRating(ratingDataModel);
        }
        else
        {
            databaseHandler.addRating(ratingDataModel);
        }

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
            if(isFromMain)
            {
                Intent intent = new Intent(this, RatingActivity.class);
                startActivity(intent);
            }
            else
            {
                PlacesUtil.showPlacesPicker(ratingDataModel.getBounds());
                finish();
            }
            return true;
        }
        else if(id == R.id.action_save)
        {
            saveObject();
            Toast.makeText(this, "Bewertung gespeichert", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void disableCommentEdit()
    {
        commentEdit.clearFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(commentEdit.getWindowToken(), 0);
    }
}
