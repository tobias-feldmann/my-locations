package com.mylocations.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mylocations.ratings.RatingDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tobias Feldmann on 04.05.15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ratingsdb";

    // Contacts table name
    private static final String TABLE_RATINGS = "ratings";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_PLACE_TYPES = "place_types";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_PRICE_LEVEL = "price_level";
    private static final String KEY_RATING = "rating";
    private static final String KEY_WEBSITE = "website";

    private static final String KEY_TIMESTAMP = "timestamp";

    private static final String KEY_SOUTHWEST_LATITUDE = "southwest_latitude";
    private static final String KEY_SOUTHWEST_LONGITUDE = "southwest_longitude";
    private static final String KEY_NORTHEAST_LATITUDE = "northeast_latitude";
    private static final String KEY_NORTHEAST_LONGITUDE = "northeast_longitude";

    private static final String KEY_PRIVATE_RATING = "private_rating";
    private static final String KEY_COMMENT = "comment";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_RATINGS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_ADDRESS + " TEXT," + KEY_PLACE_TYPES + " TEXT," + KEY_PHONE_NUMBER + " TEXT,"
                + KEY_PRICE_LEVEL + " INTEGER," + KEY_RATING + " REAL," + KEY_WEBSITE + " TEXT,"
                + KEY_TIMESTAMP + " INTEGER," + KEY_SOUTHWEST_LATITUDE + " REAL," + KEY_SOUTHWEST_LONGITUDE + " REAL,"
                + KEY_NORTHEAST_LATITUDE + " REAL," + KEY_NORTHEAST_LONGITUDE + " REAL,"
                + KEY_PRIVATE_RATING + " INTEGER," + KEY_COMMENT + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATINGS);

        // Create tables again
        onCreate(db);
    }

    public void addRating(RatingDataModel rating) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Inserting Row
        db.insert(TABLE_RATINGS, null, getContentFromRating(rating));
        db.close(); // Closing database connection
    }

    public RatingDataModel getRating(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RATINGS, new String[] { KEY_ID,
                        KEY_NAME, KEY_ADDRESS, KEY_PLACE_TYPES, KEY_PHONE_NUMBER, KEY_PRICE_LEVEL,
                        KEY_RATING, KEY_WEBSITE, KEY_TIMESTAMP, KEY_SOUTHWEST_LATITUDE, KEY_SOUTHWEST_LONGITUDE,
                        KEY_NORTHEAST_LATITUDE, KEY_NORTHEAST_LONGITUDE, KEY_PRIVATE_RATING, KEY_COMMENT}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        return getRatingFromCursor(cursor);
    }

    public List<RatingDataModel> getAllRatings() {
        List<RatingDataModel> ratingList = new ArrayList<RatingDataModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RATINGS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Adding rating to list
                ratingList.add(getRatingFromCursor(cursor));
            } while (cursor.moveToNext());
        }

        // return contact list
        return ratingList;
    }

    public int updateRating(RatingDataModel rating) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = getContentFromRating(rating);

        // updating row
        return db.update(TABLE_RATINGS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(rating.getId()) });
    }

    public void deleteRating(RatingDataModel rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RATINGS, KEY_ID + " = ?",
                new String[] { String.valueOf(rating.getId()) });
        db.close();
    }

    private ContentValues getContentFromRating(RatingDataModel rating)
    {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, rating.getName());
        values.put(KEY_ADDRESS, rating.getAddress());
        values.put(KEY_PLACE_TYPES, typeListToString(rating.getPlaceTypes()));
        values.put(KEY_PHONE_NUMBER, rating.getName());
        values.put(KEY_PRICE_LEVEL, rating.getName());
        values.put(KEY_RATING, rating.getRating());
        values.put(KEY_WEBSITE, rating.getWebsite());
        values.put(KEY_TIMESTAMP, rating.getTimestamp()/1000);
        values.put(KEY_SOUTHWEST_LATITUDE, rating.getSouthWestLatitude());
        values.put(KEY_SOUTHWEST_LONGITUDE, rating.getSouthWestLongitude());
        values.put(KEY_NORTHEAST_LATITUDE, rating.getNorthEastLatitude());
        values.put(KEY_NORTHEAST_LONGITUDE, rating.getNorthEastLongitude());
        values.put(KEY_PRIVATE_RATING, rating.getPrivateRating());
        values.put(KEY_COMMENT, rating.getComment());
        return values;
    }


    private RatingDataModel getRatingFromCursor(Cursor cursor)
    {
        RatingDataModel rating = new RatingDataModel();
        rating.setId(Integer.parseInt(cursor.getString(0)));
        rating.setName(cursor.getString(1));
        rating.setPlaceTypes(typeStringToList(cursor.getString(2)));
        rating.setPhoneNumber(cursor.getString(3));
        rating.setPriceLevel(Integer.parseInt(cursor.getString(4)));
        rating.setRating(Float.parseFloat(cursor.getString(5)));
        rating.setWebsite(cursor.getString(6));
        rating.setTimestamp(Integer.parseInt(cursor.getString(7)));
        rating.setSouthWestLatitude(Double.parseDouble(cursor.getString(8)));
        rating.setSouthWestLongitude(Double.parseDouble(cursor.getString(9)));
        rating.setNorthEastLatitude(Double.parseDouble(cursor.getString(10)));
        rating.setNorthEastLongitude(Double.parseDouble(cursor.getString(11)));
        rating.setPrivateRating(Integer.parseInt(cursor.getString(12)));
        rating.setComment(cursor.getString(13));
        return rating;
    }

    public String typeListToString(ArrayList<Integer> types)
    {
        try {
            JSONObject json = new JSONObject();
            json.put("typeList", new JSONArray(types));
            String arrayList = json.toString();
            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Integer> typeStringToList(String types)
    {
        try {
            ArrayList<Integer> itemList = new ArrayList<>();
            JSONObject json = new JSONObject(types);
            JSONArray items = json.optJSONArray("typeList");
            for (int i = 0; i < items.length(); i++) {
                itemList.add(items.getInt(i));
            }
            return itemList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
