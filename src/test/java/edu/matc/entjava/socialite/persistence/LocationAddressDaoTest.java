package edu.matc.entjava.socialite.persistence;

import edu.matc.entjava.socialite.entity.Location;
import edu.matc.entjava.socialite.entity.LocationAddress;
import edu.matc.entjava.socialite.testUtils.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type LocationAddress dao test.
 */
class LocationAddressDaoTest {

    GenericDao dao;
    private final Logger logger = LogManager.getLogger(this.getClass());


    /**
     * Creating the dao.
     */
    @BeforeEach
    void setUp() {
        dao = new GenericDao(LocationAddress.class);

        Database database = Database.getInstance();
        database.runSQL("cleanLocationAddressTable.sql");
    }

    /**
     * Verifies gets all locationAddresses successfully.
     */
    @Test
    void getAllLocationsSuccess() {
        List<LocationAddress> locationAddresses = dao.getAllEntities();
        assertEquals(5, locationAddresses.size());
    }

    /**
     * testing get id
     */
    @Test
    void getById() {
        LocationAddress locationCategoryRetrieved = (LocationAddress) dao.getById(1);
        assertNotNull(locationCategoryRetrieved);
        assertEquals(1, locationCategoryRetrieved.getId());
    }

    /**
     * Verify successful insert of a locationAddress
     */
    @Test
    void insertSuccess() {

        int id = 0;

        LocationAddress newLocationAddress = new LocationAddress(
                "n blue st",
                "fitchburg",
                "wi",
                53718,
                "",
                "",
                (Location) new GenericDao(Location.class).getById(8)
        );

        id = dao.insert(newLocationAddress);

        assertNotEquals(0, id);

        LocationAddress insertedLocation = (LocationAddress) dao.getById(id);

        assertEquals(newLocationAddress, insertedLocation);
    }

    /**
     * Verify successful delete of locationAddress
     */
    @Test
    void deleteSuccess() {
        dao.delete(dao.getById(3));
        assertNull(dao.getById(3));
    }

    /**
     * Verify successful update of locationAddress
     */
    @Test
    void updateSuccess() {
        String newStreet = "s red st";

        LocationAddress locationAddressToUpdate = (LocationAddress) dao.getById(3);

        locationAddressToUpdate.setStreet(newStreet);

        dao.saveOrUpdate(locationAddressToUpdate);

        LocationAddress retrievedLocationAddress = (LocationAddress) dao.getById(3);

        assertEquals(locationAddressToUpdate, retrievedLocationAddress);
    }

    /**
     * Verify successful get by property (equal match)
     */
    @Test
    void getByPropertyValueSuccess() {
        List<LocationAddress> locationAddresses = dao.getByPropertyValue("street", "n blue st");
        assertEquals(3, locationAddresses.size());
        assertEquals(3, locationAddresses.get(0).getId());
    }

    /**
     * Verify successful get by property (like match)
     */
    @Test
    void getByPropertyLikeSuccess() {
        List<LocationAddress> locationAddresses = dao.getByPropertyLike("street", "th");
        assertEquals(2, locationAddresses.size());
    }
}