package edu.matc.entjava.socialite.persistence;

import edu.matc.entjava.socialite.entity.Search;
import edu.matc.entjava.socialite.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Yelp dao test.
 */
class YelpDaoTest {

    /**
     * Gets locations by geo and type.
     */
    @Test
    void getLocationByGeoAndType() {

        GeoDao geoDao = new GeoDao();

        User user = (User) new GenericDao(User.class).getById(608);

        Search search = geoDao.getGeoLocationsByZipcode(53511, 1, user).get(0);

        double lat = search.getLatitude();
        double lng = search.getLongitude();

        YelpDao yelpDao = new YelpDao();

        assertEquals(20, yelpDao.getLocationByGeoAndType(lat, lng, "drinks").size());
    }
}