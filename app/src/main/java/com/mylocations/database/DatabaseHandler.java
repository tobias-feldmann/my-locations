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

    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_RATINGS + "("
                + KEY_ID + " TEXT PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_ADDRESS + " TEXT," + KEY_PLACE_TYPES + " TEXT," + KEY_PHONE_NUMBER + " TEXT,"
                + KEY_PRICE_LEVEL + " INTEGER," + KEY_RATING + " REAL," + KEY_WEBSITE + " TEXT,"
                + KEY_TIMESTAMP + " TEXT," + KEY_SOUTHWEST_LATITUDE + " REAL," + KEY_SOUTHWEST_LONGITUDE + " REAL,"
                + KEY_NORTHEAST_LATITUDE + " REAL," + KEY_NORTHEAST_LONGITUDE + " REAL,"
                + KEY_PRIVATE_RATING + " INTEGER," + KEY_COMMENT + " TEXT," +  KEY_LATITUDE + " REAL," + KEY_LONGITUDE + " REAL" + ")";
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

    public RatingDataModel getRating(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RATINGS, new String[] { KEY_ID,
                        KEY_NAME, KEY_ADDRESS, KEY_PLACE_TYPES, KEY_PHONE_NUMBER, KEY_PRICE_LEVEL,
                        KEY_RATING, KEY_WEBSITE, KEY_TIMESTAMP, KEY_SOUTHWEST_LATITUDE, KEY_SOUTHWEST_LONGITUDE,
                        KEY_NORTHEAST_LATITUDE, KEY_NORTHEAST_LONGITUDE, KEY_PRIVATE_RATING, KEY_COMMENT, KEY_LATITUDE, KEY_LONGITUDE}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if(cursor == null || cursor.getCount() == 0 )
            return null;

        if (cursor != null)
            cursor.moveToFirst();

        return getRatingFromCursor(cursor);
    }

    public boolean existRating(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RATINGS, new String[] { KEY_ID,
                        KEY_NAME, KEY_ADDRESS, KEY_PLACE_TYPES, KEY_PHONE_NUMBER, KEY_PRICE_LEVEL,
                        KEY_RATING, KEY_WEBSITE, KEY_TIMESTAMP, KEY_SOUTHWEST_LATITUDE, KEY_SOUTHWEST_LONGITUDE,
                        KEY_NORTHEAST_LATITUDE, KEY_NORTHEAST_LONGITUDE, KEY_PRIVATE_RATING, KEY_COMMENT, KEY_LATITUDE, KEY_LONGITUDE}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if(cursor == null || cursor.getCount() == 0 )
            return false;
        else
            return true;
    }

    public boolean isEmpty() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RATINGS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor == null || cursor.getCount() == 0 )
            return true;
        else
            return false;
    }

    public ArrayList<RatingDataModel> getAllRatings() {
        ArrayList<RatingDataModel> ratingList = new ArrayList<RatingDataModel>();
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

    public void deleteRating(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RATINGS, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    private ContentValues getContentFromRating(RatingDataModel rating)
    {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, rating.getId());
        values.put(KEY_NAME, rating.getName());
        values.put(KEY_ADDRESS, rating.getAddress());
        values.put(KEY_PLACE_TYPES, rating.getPlaceTypes());
        values.put(KEY_PHONE_NUMBER, rating.getPhoneNumber());
        values.put(KEY_PRICE_LEVEL, rating.getPriceLevel());
        values.put(KEY_RATING, rating.getRating());
        values.put(KEY_WEBSITE, rating.getWebsite());
        values.put(KEY_TIMESTAMP, String.valueOf(rating.getTimestamp()));
        values.put(KEY_SOUTHWEST_LATITUDE, rating.getSouthWestLatitude());
        values.put(KEY_SOUTHWEST_LONGITUDE, rating.getSouthWestLongitude());
        values.put(KEY_NORTHEAST_LATITUDE, rating.getNorthEastLatitude());
        values.put(KEY_NORTHEAST_LONGITUDE, rating.getNorthEastLongitude());
        values.put(KEY_PRIVATE_RATING, rating.getPrivateRating());
        values.put(KEY_COMMENT, rating.getComment());
        values.put(KEY_LATITUDE, rating.getLatitude());
        values.put(KEY_LONGITUDE, rating.getLongitude());
        return values;
    }

    private RatingDataModel getRatingFromCursor(Cursor cursor)
    {
        RatingDataModel rating = new RatingDataModel();
        rating.setId(cursor.getString(0));
        rating.setName(cursor.getString(1));
        rating.setAddress(cursor.getString(2));
        rating.setPlaceTypes(cursor.getString(3));
        rating.setPhoneNumber(cursor.getString(4));
        rating.setPriceLevel(Integer.parseInt(cursor.getString(5)));
        rating.setRating(Float.parseFloat(cursor.getString(6)));
        rating.setWebsite(cursor.getString(7));
        rating.setTimestamp(Long.valueOf(cursor.getString(8)));
        rating.setSouthWestLatitude(Double.parseDouble(cursor.getString(9)));
        rating.setSouthWestLongitude(Double.parseDouble(cursor.getString(10)));
        rating.setNorthEastLatitude(Double.parseDouble(cursor.getString(11)));
        rating.setNorthEastLongitude(Double.parseDouble(cursor.getString(12)));
        rating.setPrivateRating(Integer.parseInt(cursor.getString(13)));
        rating.setComment(cursor.getString(14));
        rating.setLatitude(Double.parseDouble(cursor.getString(15)));
        rating.setLongitude(Double.parseDouble(cursor.getString(16)));
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
