package edu.matc.entjava.socialite.persistence;

import edu.matc.entjava.socialite.entity.Friend;
import edu.matc.entjava.socialite.entity.User;
import edu.matc.entjava.socialite.testUtils.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Friend dao test.
 */
class FriendDaoTest {

    GenericDao dao;
    private final Logger logger = LogManager.getLogger(this.getClass());


    /**
     * Creating the dao.
     */
    @BeforeEach
    void setUp() {
        dao = new GenericDao(Friend.class);

        Database database = Database.getInstance();
        database.runSQL("cleanFriendTable.sql");
    }

    /**
     * Verifies gets all friends successfully.
     */
    @Test
    void getAllFriendsSuccess() {
        List<Friend> friends = dao.getAllEntities();
        assertEquals(5, friends.size());
    }

    /**
     * testing get id
     */
    @Test
    void getById() {
        Friend friendRetrieved = (Friend) dao.getById(2);
        assertNotNull(friendRetrieved);
        assertEquals(2, friendRetrieved.getId());
    }

    /**
     * Verify successful insert of a friend
     */
    @Test
    void insertSuccess() {

        int id = 0;

        User requester = (User) new GenericDao(User.class).getById(617);
        User requested = (User) new GenericDao(User.class).getById(620);

        Friend newFriend = new Friend(requester, requested, false);

        id = dao.insert(newFriend);

        assertNotEquals(0, id);

        Friend insertedFriend = (Friend) dao.getById(id);

        assertEquals(newFriend, insertedFriend);
    }


    /**
     * Verify successful delete of friend
     */
    @Test
    void deleteSuccess() {
        dao.delete(dao.getById(1));
        assertNull(dao.getById(1));
    }

    /**
     * Verify successful update of friend
     */
    @Test
    void updateSuccess() {
        Boolean accepted = true;

        Friend friendToUpdate = (Friend) dao.getById(1);

        friendToUpdate.setAccepted(accepted);

        dao.saveOrUpdate(friendToUpdate);

        Friend retrievedFriend = (Friend) dao.getById(1);

        assertEquals(friendToUpdate, retrievedFriend);
    }

    /**
     * Verify successful get by property (equal match)
     */
    @Test
    void getByPropertyValueSuccess() {
        List<Friend> friends = dao.getByPropertyValue("requester_user", 628);
        assertEquals(2, friends.size());
        assertEquals(3, friends.get(0).getId());
    }

}