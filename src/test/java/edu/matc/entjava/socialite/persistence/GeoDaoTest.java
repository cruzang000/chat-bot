package edu.matc.entjava.socialite.persistence;

import edu.matc.entjava.socialite.entity.Search;
import edu.matc.entjava.socialite.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

/**
 * The type Geo dao test.
 */
class GeoDaoTest {

    /**
     * Gets geo locations by zipcode.
     */
    @Test
    void getGeoLocationsByZipcode() {

        GeoDao geoDao = new GeoDao();

        User user = (User) new GenericDao(User.class).getById(608);

        ArrayList<Search> searches = geoDao.getGeoLocationsByZipcode(53511, 1, user);

        user.getSearches().addAll(searches);

        assertEquals(1, searches.size());
        assertEquals("Beloit", searches.get(0).getCity());
    }
}