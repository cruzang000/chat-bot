package edu.matc.entjava.socialite.persistence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import edu.matc.entjava.socialite.entity.GeoSearch;

class GeoDaoTest {

    @Test
    void getGeoLocationByZipcode() {

        GeoDao geoDao = new GeoDao();

        assertEquals("Beloit", geoDao.getGeoLocationByZipcode(53511).getPlaceName());

    }
}