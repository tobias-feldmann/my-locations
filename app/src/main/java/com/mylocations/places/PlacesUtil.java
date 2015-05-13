package com.mylocations.places;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.mylocations.DetailActivity;

/**
 * Created by Tobias Feldmann on 29.04.15.
 */
public class PlacesUtil {

    private static final int PLACE_PICKER_REQUEST = 1;

    public static void showPlacesPicker()
    {
        Activity activity = PlacesController.getInstance().getMainActivity();
        Context context = activity.getApplicationContext();
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            activity.startActivityForResult(builder.build(context), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public static void showPlacesPicker(LatLngBounds bounds)
    {
        Activity activity = PlacesController.getInstance().getMainActivity();
        Context context = activity.getApplicationContext();
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        builder.setLatLngBounds(bounds); //LatLngBounds.builder().include(point).build()
        try {
            activity.startActivityForResult(builder.build(context), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }


    public static void handlePlacesResult(int requestCode, int resultCode, Intent data, Activity activity)
    {
        Context context = activity.getApplicationContext();
        if (resultCode == activity.RESULT_OK) {
            Place place = PlacePicker.getPlace(data, context);
            LatLngBounds bounds = PlacePicker.getLatLngBounds(data);
            PlacesController.getInstance().setPlace(place);
            PlacesController.getInstance().setBounds(bounds);
            Intent intent = new Intent(activity, DetailActivity.class);
            activity.startActivity(intent);
        }

    }

}
