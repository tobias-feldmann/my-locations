package com.mylocations;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.mylocations.database.DatabaseHandler;
import com.mylocations.ratings.RatingDataModel;

/**
 * Testklasse für Datenbanktests
 *
 * Created by Tobias Feldmann on 17.06.15.
 */
public class DBTesting extends AndroidTestCase {

    private DatabaseHandler databaseHandler;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        databaseHandler = new DatabaseHandler(context);
    }


    /**
     * Test, welcher das Speichern und Laden von Bewertungsobjekten testet
     */
    public void testInsertData()
    {
        RatingDataModel testModel = new RatingDataModel();
        testModel.setId("4778adsf77");
        testModel.setName("testName");
        testModel.setAddress("Musterstraße 1");
        testModel.setPlaceTypes("BANK");
        testModel.setPhoneNumber("087165533");
        testModel.setPriceLevel(2);
        testModel.setRating(4);
        testModel.setWebsite("http://www.testwebsite.de");
        testModel.setTimestamp(System.currentTimeMillis());
        testModel.setSouthWestLatitude(12.8);
        testModel.setSouthWestLongitude(11.15);
        testModel.setNorthEastLatitude(43.18);
        testModel.setNorthEastLongitude(32.76);
        testModel.setPrivateRating(1);
        testModel.setComment("ich bin ein kommentar");
        testModel.setLatitude(37.44);
        testModel.setLongitude(24.88);
        databaseHandler.addRating(testModel);

        RatingDataModel model = databaseHandler.getRating(testModel.getId());

        assertEquals(true, model.equals(testModel));


    }
}
