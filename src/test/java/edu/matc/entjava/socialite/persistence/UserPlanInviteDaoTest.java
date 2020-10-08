package edu.matc.entjava.socialite.persistence;

import edu.matc.entjava.socialite.entity.Location;
import edu.matc.entjava.socialite.entity.User;
import edu.matc.entjava.socialite.entity.UserPlan;
import edu.matc.entjava.socialite.entity.UserPlanInvite;
import edu.matc.entjava.socialite.testUtils.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type UserPlanInvite dao test.
 */
class UserPlanInviteDaoTest {

    GenericDao dao;
    private final Logger logger = LogManager.getLogger(this.getClass());


    /**
     * Creating the dao.
     */
    @BeforeEach
    void setUp() {
        dao = new GenericDao(UserPlanInvite.class);

        Database database = Database.getInstance();
        database.runSQL("cleanUserPlanInvitesTable.sql");
    }

    /**
     * Verifies gets all userPlans successfully.
     */
    @Test
    void getAllUserPlansSuccess() {
        List<UserPlanInvite> userPlans = dao.getAllEntities();
        assertEquals(2, userPlans.size());
    }

    /**
     * testing get id
     */
    @Test
    void getById() {
        UserPlanInvite userPlanRetrieved = (UserPlanInvite) dao.getById(2);
        assertNotNull(userPlanRetrieved);
        assertEquals(2, userPlanRetrieved.getId());
    }

    /**
     * Verify successful insert of a userPlanInvite
     */
    @Test
    void insertSuccess() {

        int id = 0;

        User user = (User) new GenericDao(User.class).getById(620);

        UserPlan userPlan = (UserPlan) new GenericDao(UserPlan.class).getById(3);

        UserPlanInvite newUserPlanInvite = new UserPlanInvite(false, false, "cant make it!",
                user, userPlan);

        id = dao.insert(newUserPlanInvite);

        assertNotEquals(0, id);

        UserPlanInvite insertedUserPlan = (UserPlanInvite) dao.getById(id);

        assertEquals(newUserPlanInvite, insertedUserPlan);

        //assert user and user plan
        assertEquals(insertedUserPlan.getUser(), user);
        assertEquals(insertedUserPlan.getUserPlan(), userPlan);

    }

    /**
     * Verify successful delete of userPlanInvite
     */
    @Test
    void deleteSuccess() {
        dao.delete(dao.getById(2));
        assertNull(dao.getById(2));
    }

    /**
     * Verify successful update of userPlanInvite
     */
    @Test
    void updateSuccess() {
        User user = (User) new GenericDao(User.class).getById(650);

        UserPlanInvite userPlanToUpdate = (UserPlanInvite) dao.getById(2);

        userPlanToUpdate.setUser(user);

        dao.saveOrUpdate(userPlanToUpdate);

        UserPlanInvite retrievedUserPlan = (UserPlanInvite) dao.getById(2);

        assertEquals(retrievedUserPlan.getUser(), user);
    }

    /**
     * Verify successful get by property (equal match)
     */
    @Test
    void getByPropertyValueSuccess() {
        List<UserPlanInvite> userPlans = dao.getByPropertyValue("closed", false);
        assertEquals(2, userPlans.size());
        assertEquals(2, userPlans.get(0).getId());
    }
}