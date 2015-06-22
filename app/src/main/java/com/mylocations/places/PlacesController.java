package com.mylocations.places;

import android.app.Activity;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Controller welcher die aktuell gewählte Location verwaltet
 *
 * Created by Tobias Feldmann on 29.04.15.
 */
public class PlacesController {

    private Place place;
    private LatLngBounds bounds;
    private Activity mainActivity;

    private static final class InstanceHolder {
        static final PlacesController INSTANCE = new PlacesController();
    }

    private PlacesController () {}

    public static PlacesController getInstance () {
        return InstanceHolder.INSTANCE;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Place getPlace() {
        return place;
    }

    public LatLngBounds getBounds() {
        return bounds;
    }

    public void setBounds(LatLngBounds bounds) {
        this.bounds = bounds;
    }

    public Activity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(Activity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
