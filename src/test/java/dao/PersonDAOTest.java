package dao;

import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDAOTest {
    private Database db;
    private Person person;
    private PersonDAO dao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        person = new Person("ID", "username", "first", "last", "f", "father", "mother", "spouse");
        Connection conn = db.getConnection();
        dao = new PersonDAO(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void testInsert() throws DataAccessException {
        dao.insert(person);

        Person compareTest = dao.query("ID");

        assertNotNull(compareTest);

        assertEquals(compareTest, person);
    }

    @Test
    public void testInsertFail() throws DataAccessException {
        dao.insert(person);

        assertThrows(DataAccessException.class, ()-> dao.insert(person));
    }

    @Test
    public void testQuery() throws DataAccessException {
        dao.insert(person);

        Person compareTest = dao.query("ID");

        assertNotNull(compareTest);

        assertEquals(compareTest, person);
    }

    @Test
    public void testQueryFail() throws DataAccessException {
        assertNull(dao.query("ID"));
    }

    @Test
    public void testClear() throws DataAccessException {
        dao.insert(person);
        dao.clear();
        assertNull(dao.query("ID"));
    }
}
