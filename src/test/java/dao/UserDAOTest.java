package dao;

import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    private Database db;
    private User user;
    private UserDAO dao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        user = new User("username", "password", "email", "first", "last", "f", "ID");
        Connection conn = db.getConnection();
        //db.clearTables();
        dao = new UserDAO(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void testInsert() throws DataAccessException {
        dao.insert(user);

        User compareTest = dao.query("username");

        assertNotNull(compareTest);

        assertEquals(compareTest, user);
    }

    @Test
    public void testInsertFail() throws DataAccessException {
        dao.insert(user);

        assertThrows(DataAccessException.class, ()-> dao.insert(user));
    }

    @Test
    public void testQuery() throws DataAccessException {
        dao.insert(user);

        User compareTest = dao.query("username");

        assertNotNull(compareTest);

        assertEquals(compareTest, user);
    }

    @Test
    public void testQueryFail() throws DataAccessException {
        assertNull(dao.query("username"));
    }

    @Test
    public void testClear() throws DataAccessException {
        dao.insert(user);
        dao.clear();
        assertNull(dao.query("username"));
    }
}
