package com.mylocations.utils;

import com.mylocations.ratings.RatingDataModel;

import java.util.Comparator;

/**
 * Created by Tobias Feldmann on 20.05.15.
 */
public class RatingComparators {


    public static Comparator<RatingDataModel> RatingValueComparator = new Comparator<RatingDataModel>() {

        public int compare(RatingDataModel s1, RatingDataModel s2) {
            int value1 = s1.getPrivateRating();
            int value2 = s2.getPrivateRating();

            if (value1 < value2)
            {
                return -1;
            } else {
                return 1;
            }
        }};


    public static Comparator<RatingDataModel> RatingNameComparator = new Comparator<RatingDataModel>() {

        public int compare(RatingDataModel s1, RatingDataModel s2) {
            String value1 = s1.getName();
            String value2 = s2.getName();

            return value1.compareTo(value2);
        }};


    public static Comparator<RatingDataModel> RatingTypComparator = new Comparator<RatingDataModel>() {

        public int compare(RatingDataModel s1, RatingDataModel s2) {
            String value1 = s1.getPlaceTypes();
            String value2 = s2.getPlaceTypes();

            return value1.compareTo(value2);
        }};

}
