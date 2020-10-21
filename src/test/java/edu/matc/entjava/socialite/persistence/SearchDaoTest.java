package edu.matc.entjava.socialite.persistence;

import edu.matc.entjava.socialite.entity.Search;
import edu.matc.entjava.socialite.entity.User;
import edu.matc.entjava.socialite.testUtils.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Search dao test.
 */
class SearchDaoTest {

    GenericDao dao;
    private final Logger logger = LogManager.getLogger(this.getClass());


    /**
     * Creating the dao.
     */
    @BeforeEach
    void setUp() {
        dao = new GenericDao(Search.class);

        Database database = Database.getInstance();
        database.runSQL("cleanSearchTable.sql");
    }

    /**
     * Verifies gets all searches successfully.
     */
    @Test
    void getAllSearchesSuccess() {
        List<Search> searches = dao.getAllEntities();
        assertEquals(4, searches.size());
    }

    /**
     * testing get id
     */
    @Test
    void getById() {
        Search searchRetrieved = (Search) dao.getById(19);
        assertNotNull(searchRetrieved);
        assertEquals(19, searchRetrieved.getId());
    }

    /**
     * Verify successful insert of a search
     */
    @Test
    void insertSuccess() {

        int id = 0;

        User user = (User) new GenericDao(User.class).getById(608);

        Search newSearch = new Search(
                53719,
                "Madison",
                "wi",
                92.232,
                91.323,
                user
        );

        id = dao.insert(newSearch);

        assertNotEquals(0, id);

        Search insertedSearch = (Search) dao.getById(id);

        assertEquals(newSearch, insertedSearch);
    }

    /**
     * Verify successful delete of search
     */
    @Test
    void deleteSuccess() {
        dao.delete(dao.getById(19));
        assertNull(dao.getById(19));
    }

    /**
     * Verify successful update of search
     */
    @Test
    void updateSuccess() {
        int newZipcode = 53563;

        Search searchToUpdate = (Search) dao.getById(19);

        searchToUpdate.setZipcode(newZipcode);

        dao.saveOrUpdate(searchToUpdate);

        Search retrievedSearch = (Search) dao.getById(19);

        assertEquals(searchToUpdate, retrievedSearch);
    }

    /**
     * Verify successful get by property (equal match)
     */
    @Test
    void getByPropertyValueSuccess() {
        List<Search> searches = dao.getByPropertyValue("state", "wi");
        assertEquals(4, searches.size());
        assertEquals(1, searches.get(0).getId());
    }

    /**
     * Verify successful get by property (like match)
     */
    @Test
    void getByPropertyLikeSuccess() {
        List<Search> searches = dao.getByPropertyLike("state", "w");
        assertEquals(4, searches.size());
    }
}