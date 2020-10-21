package edu.matc.entjava.socialite.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import edu.matc.entjava.socialite.entity.User;
import edu.matc.entjava.socialite.testUtils.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type User dao test.
 */
class UserDaoTest {

    GenericDao dao;
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Creating the dao.
     */
    @BeforeEach
    void setUp() {
        dao = new GenericDao(User.class);

        Database database = Database.getInstance();
        database.runSQL("cleanUserTable.sql");
        database.runSQL("cleanUserPlansTable.sql");
        database.runSQL("cleanSearchTable.sql");
        database.runSQL("cleanFriendTable.sql");
        database.runSQL("cleanUserRolesTable.sql");
    }

    /**
     * Verifies gets all users successfully.
     */
    @Test
    void getAllUsersSuccess() {
        List<User> users = dao.getAllEntities();
        assertEquals(100, users.size());
    }

    /**
     * Verifies gets users by last name successfully.
     */
    @Test
    void getUsersByFirstNameSuccess() {
        List<User> users = dao.getByPropertyValue("firstName", "Alexander");
        assertEquals(1, users.size());
    }

    /**
     * Verifies gets users by last name successfully.
     */
    @Test
    void getUsersByLastNameSuccess() {
        List<User> users = dao.getByPropertyValue("lastName", "Fusco");
        assertEquals(1, users.size());
    }

    /**
     * testing get id
     */
    @Test
    void getById() {
        User userRetrieved = (User) dao.getById(610);
        assertNotNull(userRetrieved);
        assertEquals(610, userRetrieved.getId());
    }

    /**
     * Verify successful insert of a user
     */
    @Test
    void insertSuccess() {

        int id = 0;

        User newUser = new User("cruzang", "test", "cruz", "angel", "testInsert@madisoncollege.edu", true);

        id = dao.insert(newUser);

        assertNotEquals(0, id);

        User insertedUser = (User) dao.getById(id);

        assertEquals(newUser, insertedUser);
    }

    /**
     * Verify successful delete of user
     */
    @Test
    void deleteSuccess() {
        dao.delete(dao.getById(609));
        assertNull(dao.getById(609));
    }

    /**
     * Verify successful update of user
     */
    @Test
    void updateSuccess() {
        String newLastName = "angel";

        User userToUpdate = (User) dao.getById(608);

        userToUpdate.setLastName(newLastName);

        dao.saveOrUpdate(userToUpdate);

        User retrievedUser = (User) dao.getById(608);

        assertEquals(userToUpdate, retrievedUser);
    }

    /**
     * Verify successful get by property (equal match)
     */
    @Test
    void getByPropertyValueSuccess() {
        List<User> users = dao.getByPropertyValue("firstName", "hadley");
        assertEquals(1, users.size());
        assertEquals(610, users.get(0).getId());
    }

    /**
     * Verify successful get by property (like match)
     */
    @Test
    void getByPropertyLikeSuccess() {
        List<User> users = dao.getByPropertyLike("firstName", "h");
        assertEquals(17, users.size());
    }

    /**
     * testing user get friends
     */
    @Test
    void getFriends() {
        User userRetrieved = (User) dao.getById(615);

        //tests 2 accepted requests
        assertEquals(2, userRetrieved.getAcceptedFriends().size());
    }

    /**
     * testing get user friend notification
     */
    @Test
    void getFriendNotifications() {
        User userRetrieved = (User) dao.getById(620);

        //tests 2 accepted requests
        assertEquals(2, userRetrieved.getFriendNotifications().size());
    }

    /**
     * testing get user friend notification
     */
    @Test
    void getCurrentUserPlans() {
        User userRetrieved = (User) dao.getById(608);

        //tests 2 accepted requests
        assertEquals(1, userRetrieved.getCurrentPlans().size());
    }
}