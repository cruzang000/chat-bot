package edu.matc.entjava.socialite.persistence;

import edu.matc.entjava.socialite.entity.Location;
import edu.matc.entjava.socialite.entity.LocationCategory;
import edu.matc.entjava.socialite.testUtils.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type LocationCategory dao test.
 */
class LocationCategoryDaoTest {

    GenericDao dao;
    private final Logger logger = LogManager.getLogger(this.getClass());


    /**
     * Creating the dao.
     */
    @BeforeEach
    void setUp() {
        dao = new GenericDao(LocationCategory.class);

        Database database = Database.getInstance();
        database.runSQL("cleanLocationCategoryTable.sql");
    }

    /**
     * Verifies gets all locationCategories successfully.
     */
    @Test
    void getAllLocationsSuccess() {
        List<LocationCategory> locationCategories = dao.getAllEntities();
        assertEquals(6, locationCategories.size());
    }

    /**
     * testing get id
     */
    @Test
    void getById() {
        LocationCategory locationCategoryRetrieved = (LocationCategory) dao.getById(5);
        assertNotNull(locationCategoryRetrieved);
        assertEquals(5, locationCategoryRetrieved.getId());
    }

    /**
     * Verify successful insert of a locationCategory
     */
    @Test
    void insertSuccess() {

        int id = 0;

        LocationCategory newLocation = new LocationCategory("bar", (Location) new GenericDao(Location.class).getById(5));

        id = dao.insert(newLocation);

        assertNotEquals(0, id);

        LocationCategory insertedLocation = (LocationCategory) dao.getById(id);

        assertEquals(newLocation, insertedLocation);
    }

    /**
     * Verify successful delete of locationCategory
     */
    @Test
    void deleteSuccess() {
        dao.delete(dao.getById(6));
        assertNull(dao.getById(6));
    }

    /**
     * Verify successful update of locationCategory
     */
    @Test
    void updateSuccess() {
        String newName = "bar";

        LocationCategory locationToUpdate = (LocationCategory) dao.getById(5);

        locationToUpdate.setName(newName);

        dao.saveOrUpdate(locationToUpdate);

        LocationCategory retrievedLocation = (LocationCategory) dao.getById(5);

        assertEquals(locationToUpdate, retrievedLocation);
    }

    /**
     * Verify successful get by property (equal match)
     */
    @Test
    void getByPropertyValueSuccess() {
        List<LocationCategory> locationCategories = dao.getByPropertyValue("name", "club");
        assertEquals(3, locationCategories.size());
        assertEquals(5, locationCategories.get(0).getId());
    }

    /**
     * Verify successful get by property (like match)
     */
    @Test
    void getByPropertyLikeSuccess() {
        List<LocationCategory> locationCategories = dao.getByPropertyLike("name", "ar");
        assertEquals(3, locationCategories.size());
    }
}