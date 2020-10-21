package edu.matc.entjava.socialite.persistence;

import edu.matc.entjava.socialite.entity.Location;
import edu.matc.entjava.socialite.entity.User;
import edu.matc.entjava.socialite.entity.UserPlan;
import edu.matc.entjava.socialite.testUtils.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type UserPlan dao test.
 */
class UserPlanDaoTest {

    GenericDao dao;
    private final Logger logger = LogManager.getLogger(this.getClass());


    /**
     * Creating the dao.
     */
    @BeforeEach
    void setUp() {
        dao = new GenericDao(UserPlan.class);

        Database database = Database.getInstance();
        database.runSQL("cleanUserPlansTable.sql");
    }

    /**
     * Verifies gets all userPlans successfully.
     */
    @Test
    void getAllUserPlansSuccess() {
        List<UserPlan> userPlans = dao.getAllEntities();
        assertEquals(2, userPlans.size());
    }

    /**
     * testing get id
     */
    @Test
    void getById() {
        UserPlan userPlanRetrieved = (UserPlan) dao.getById(2);
        assertNotNull(userPlanRetrieved);
        assertEquals(2, userPlanRetrieved.getId());
    }

    /**
     * Verify successful insert of a userPlan
     */
    @Test
    void insertSuccess() {

        int id = 0;

        User user = (User) new GenericDao(User.class).getById(608);

        Location location = (Location) new GenericDao(Location.class).getById(5);

        UserPlan newUserPlan = new UserPlan(false, user, location);

        id = dao.insert(newUserPlan);

        assertNotEquals(0, id);

        UserPlan insertedUserPlan = (UserPlan) dao.getById(id);

        assertEquals(newUserPlan, insertedUserPlan);

        //assert user and location
        assertEquals(insertedUserPlan.getUser(), user);
        assertEquals(insertedUserPlan.getLocation(), location);

    }

    /**
     * Verify successful delete of userPlan
     */
    @Test
    void deleteSuccess() {
        dao.delete(dao.getById(2));
        assertNull(dao.getById(2));
    }

    /**
     * Verify successful update of userPlan
     */
    @Test
    void updateSuccess() {
        User user = (User) new GenericDao(User.class).getById(609);

        UserPlan userPlanToUpdate = (UserPlan) dao.getById(2);

        userPlanToUpdate.setUser(user);

        dao.saveOrUpdate(userPlanToUpdate);

        UserPlan retrievedUserPlan = (UserPlan) dao.getById(2);

        assertEquals(retrievedUserPlan.getUser(), user);
    }

    /**
     * Verify successful get by property (equal match)
     */
    @Test
    void getByPropertyValueSuccess() {
        List<UserPlan> userPlans = dao.getByPropertyValue("removed", 0);
        assertEquals(2, userPlans.size());
        assertEquals(2, userPlans.get(0).getId());
    }
}