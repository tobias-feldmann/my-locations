package com.mylocations.ratings;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.mylocations.places.PlacesUtil;

import java.util.ArrayList;

/**
 * Created by Tobias Feldmann on 26.12.14.
 */
public class RatingDataModel {

    private String id;
    private String name;
    private String address;
    private String placeTypes;
    private String phoneNumber;
    private int priceLevel;
    private float rating;
    private String website;

    private long timestamp;

    private double southWestLatitude;
    private double southWestLongitude;
    private double northEastLatitude;
    private double northEastLongitude;

    private double latitude;
    private double longitude;

    private int privateRating;
    private String comment;

    public RatingDataModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlaceTypes() {
        return placeTypes;
    }

    public void setPlaceTypes(String placeTypes) {
        this.placeTypes = placeTypes;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(int priceLevel) {
        this.priceLevel = priceLevel;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public double getSouthWestLatitude() {
        return southWestLatitude;
    }

    public void setSouthWestLatitude(double southWestLatitude) {
        this.southWestLatitude = southWestLatitude;
    }

    public double getSouthWestLongitude() {
        return southWestLongitude;
    }

    public void setSouthWestLongitude(double southWestLongitude) {
        this.southWestLongitude = southWestLongitude;
    }

    public double getNorthEastLatitude() {
        return northEastLatitude;
    }

    public void setNorthEastLatitude(double northEastLatitude) {
        this.northEastLatitude = northEastLatitude;
    }

    public double getNorthEastLongitude() {
        return northEastLongitude;
    }

    public void setNorthEastLongitude(double northEastLongitude) {
        this.northEastLongitude = northEastLongitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getPrivateRating() {
        return privateRating;
    }

    public void setPrivateRating(int privateRating) {
        this.privateRating = privateRating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LatLngBounds getBounds()
    {
        return PlacesUtil.createBoundsWithMinDiagonal((new LatLng(getLatitude(), getLongitude())));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingDataModel model = (RatingDataModel) o;

        if (priceLevel != model.priceLevel) return false;
        if (Float.compare(model.rating, rating) != 0) return false;
        if (timestamp != model.timestamp) return false;
        if (Double.compare(model.southWestLatitude, southWestLatitude) != 0) return false;
        if (Double.compare(model.southWestLongitude, southWestLongitude) != 0) return false;
        if (Double.compare(model.northEastLatitude, northEastLatitude) != 0) return false;
        if (Double.compare(model.northEastLongitude, northEastLongitude) != 0) return false;
        if (Double.compare(model.latitude, latitude) != 0) return false;
        if (Double.compare(model.longitude, longitude) != 0) return false;
        if (privateRating != model.privateRating) return false;
        if (id != null ? !id.equals(model.id) : model.id != null) return false;
        if (name != null ? !name.equals(model.name) : model.name != null) return false;
        if (address != null ? !address.equals(model.address) : model.address != null) return false;
        if (placeTypes != null ? !placeTypes.equals(model.placeTypes) : model.placeTypes != null)
            return false;
        if (phoneNumber != null ? !phoneNumber.equals(model.phoneNumber) : model.phoneNumber != null)
            return false;
        if (website != null ? !website.equals(model.website) : model.website != null) return false;
        return !(comment != null ? !comment.equals(model.comment) : model.comment != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (placeTypes != null ? placeTypes.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + priceLevel;
        result = 31 * result + (rating != +0.0f ? Float.floatToIntBits(rating) : 0);
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        temp = Double.doubleToLongBits(southWestLatitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(southWestLongitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(northEastLatitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(northEastLongitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + privateRating;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
