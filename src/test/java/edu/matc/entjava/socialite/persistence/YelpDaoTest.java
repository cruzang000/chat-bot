package edu.matc.entjava.socialite.persistence;

import edu.matc.entjava.socialite.entity.Search;
import edu.matc.entjava.socialite.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Yelp dao test.
 */
class YelpDaoTest {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Gets locations by geo and type.
     */
    @Test
    void getLocationByGeoAndType() {

        GeoDao geoDao = new GeoDao();

        User user = (User) new GenericDao(User.class).getById(608);

        Search[] searches = geoDao.getGeoLocationsByZipcode(53511, 1, user);
        logger.info(searches);
        user.getSearches().addAll(Arrays.asList(searches));

        double lat = searches[0].getLatitude();
        double lng = searches[0].getLongitude();

        YelpDao yelpDao = new YelpDao();

        assertEquals(20, yelpDao.getLocationByGeoAndType(lat, lng, "drinks").length);
    }
}