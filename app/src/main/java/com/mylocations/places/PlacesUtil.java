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
 * Helper-Klasse für den Google PlacesPicker
 *
 * Created by Tobias Feldmann on 29.04.15.
 */
public class PlacesUtil {

    private static final int PLACE_PICKER_REQUEST = 1;

    /**
     * Startet die PlacePicker-Activity
     *
     */
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

    /**
     * Startet die PlacePicker-Activity mit einer bestimmten Anfangsposition
     *
     */
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


    /**
     * Methode die das Ergebnis des PlacePickers (Wahl einer Location des Users) weiterverarbeitet und
     * die DetailActivity startet
     *
     * @param requestCode   requestCode
     * @param resultCode    resultCode
     * @param data          intent mit den benötigten Daten
     * @param activity      die activity
     */
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

    /**
     * Methode die ein LatLngBounds-Objekt (Rechteckiger-Bereich) mit dem übergebenen Punkt als
     * Mitte erstellt und einen bestimmten Zoomfaktor miteinberechnet.
     *
     * @param centerPoint Koordinaten des Mittelpunkts
     * @return das LatLngBounds-Objekt
     */
    public static LatLngBounds createBoundsWithMinDiagonal(LatLng centerPoint) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(centerPoint);

        LatLngBounds tmpBounds = builder.build();
        LatLng center = tmpBounds.getCenter();
        LatLng northEast = move(center, 300, 300);
        LatLng southWest = move(center, -300, -300);
        builder.include(southWest);
        builder.include(northEast);
        return builder.build();
    }

    private static final double EARTHRADIUS = 6366198;

    private static LatLng move(LatLng startLL, double toNorth, double toEast) {
        double lonDiff = meterToLongitude(toEast, startLL.latitude);
        double latDiff = meterToLatitude(toNorth);
        return new LatLng(startLL.latitude + latDiff, startLL.longitude
                + lonDiff);
    }

    private static double meterToLongitude(double meterToEast, double latitude) {
        double latArc = Math.toRadians(latitude);
        double radius = Math.cos(latArc) * EARTHRADIUS;
        double rad = meterToEast / radius;
        return Math.toDegrees(rad);
    }


    private static double meterToLatitude(double meterToNorth) {
        double rad = meterToNorth / EARTHRADIUS;
        return Math.toDegrees(rad);
    }

}
