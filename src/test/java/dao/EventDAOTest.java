package dao;

import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class EventDAOTest {
    private Database db;
    private Event event;
    private EventDAO dao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        event = new Event("ID", "username", "personID", 2, 1, "country", "city", "type", 1900);
        Connection conn = db.getConnection();
        dao = new EventDAO(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void testInsert() throws DataAccessException {
        dao.insert(event);

        Event compareTest = dao.query("ID");

        assertNotNull(compareTest);

        assertEquals(compareTest, event);
    }

    @Test
    public void testInsertFail() throws DataAccessException {
        dao.insert(event);

        assertThrows(DataAccessException.class, ()-> dao.insert(event));
    }

    @Test
    public void testQuery() throws DataAccessException {
        dao.insert(event);

        Event compareTest = dao.query("ID");

        assertNotNull(compareTest);

        assertEquals(compareTest, event);
    }

    @Test
    public void testQueryFail() throws DataAccessException {
        assertNull(dao.query("ID"));
    }

    @Test
    public void testClear() throws DataAccessException {
        dao.insert(event);
        dao.clear();
        assertNull(dao.query("ID"));
    }
}
