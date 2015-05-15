package com.mylocations.ratings;

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
}
