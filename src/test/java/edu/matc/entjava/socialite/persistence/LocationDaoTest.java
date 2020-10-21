package edu.matc.entjava.socialite.persistence;

import edu.matc.entjava.socialite.entity.Location;
import edu.matc.entjava.socialite.testUtils.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Location dao test.
 */
class LocationDaoTest {

    GenericDao dao;
    private final Logger logger = LogManager.getLogger(this.getClass());


    /**
     * Creating the dao.
     */
    @BeforeEach
    void setUp() {
        dao = new GenericDao(Location.class);

        Database database = Database.getInstance();
        database.runSQL("cleanLocationsTable.sql");
    }

    /**
     * Verifies gets all locations successfully.
     */
    @Test
    void getAllLocationsSuccess() {
        List<Location> locations = dao.getAllEntities();
        assertEquals(102, locations.size());
    }

    /**
     * testing get id
     */
    @Test
    void getById() {
        Location locationRetrieved = (Location) dao.getById(10);
        assertNotNull(locationRetrieved);
        assertEquals(10, locationRetrieved.getId());
    }

    /**
     * Verify successful insert of a location
     */
    @Test
    void insertSuccess() {

        int id = 0;

        Location newLocation = new Location(
                "Orci Consectetuer Euismod Institute",
                "www.yelp.com",
                "18E33DA9-2526-676E-DBC8-0236B4F3722B",
                "$",
                4,
                "www.yelp.com/test",
                "6081234567",
                803863873,
                false
        );

        id = dao.insert(newLocation);

        assertNotEquals(0, id);

        Location insertedLocation = (Location) dao.getById(id);

        assertEquals(newLocation, insertedLocation);
    }

    /**
     * Verify successful delete of location
     */
    @Test
    void deleteSuccess() {
        dao.delete(dao.getById(10));
        assertNull(dao.getById(10));
    }

    /**
     * Verify successful update of location
     */
    @Test
    void updateSuccess() {
        String newName = "The U";

        Location locationToUpdate = (Location) dao.getById(10);

        locationToUpdate.setName(newName);

        dao.saveOrUpdate(locationToUpdate);

        Location retrievedLocation = (Location) dao.getById(10);

        assertEquals(locationToUpdate, retrievedLocation);
    }

    /**
     * Verify successful get by property (equal match)
     */
    @Test
    void getByPropertyValueSuccess() {
        List<Location> locations = dao.getByPropertyValue("name", "Aenean Company");
        assertEquals(1, locations.size());
        assertEquals(3, locations.get(0).getId());
    }

    /**
     * Verify successful get by property (like match)
     */
    @Test
    void getByPropertyLikeSuccess() {
        List<Location> locations = dao.getByPropertyLike("name", "Aliquet");
        assertEquals(2, locations.size());
    }
}