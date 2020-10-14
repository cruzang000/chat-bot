package edu.matc.entjava.socialite.persistence;

import edu.matc.entjava.socialite.entity.GeoSearch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class YelpDaoTest {

    @Test
    void getLocationByGeo() {

        GeoDao geoDao = new GeoDao();
        GeoSearch geoLocation = geoDao.getGeoLocationByZipcode(53511);
        double lat = geoLocation.getLat();
        double lng = geoLocation.getLng();

        //yelp dao
        YelpDao yelpDao = new YelpDao();
        YelpLocation yelpLocations = yelpDao.getLocationByGeo(lat, lng);

        assertEquals("Beloit", yelpLocations.length);

    }
}