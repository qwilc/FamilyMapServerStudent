package dao;

import model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class EventDAOTest extends PersonAndEventTest {
    Event testEvent = new Event("ID1", "username", "personID1", 1, 2, "country",
            "city", "type", 1900);

    Event testEvent2 = new Event("ID2", "username", "personID2", 1, 2, "country",
            "city", "type", 1900);
    Event testEvent3 = new Event("ID3", "username", "personID1", 1, 2, "country",
            "city", "death", 1900);

    @Override
    protected void initializeInstanceVariables(Connection conn) {
        this.primaryKey = "ID";
        this.model = new Event(primaryKey, "testUsername", "personID1", 1, 2, "country",
                "city", "type", 1900);
        this.dao = new EventDAO(conn);
    }

    @BeforeEach
    public void eventSetUp() throws DataAccessException {
        clearService.clear();

        dao.insert(testEvent);
        dao.insert(testEvent2);
        dao.insert(testEvent3);
    }

    @Test
    public void testQueryByPerson() throws DataAccessException {
        Event[] compareTest = ((EventDAO)dao).queryByPerson("personID1");

        assertEquals(2, compareTest.length);
        assertNotNull(compareTest[0]);
        assertNotNull(compareTest[1]);

        assertEquals("ID1", compareTest[0].getEventID());
        assertEquals("ID3", compareTest[1].getEventID());
    }

    @Test
    public void testQueryByInvalidPerson() throws DataAccessException {
        Event[] compareTest = ((EventDAO)dao).queryByPerson("invalidID");

        assertNull(compareTest);
    }

    @Test
    public void testQueryByPersonAndType() throws DataAccessException {
        Event[] compareTest = ((EventDAO)dao).queryByPersonAndType("personID1", "death");

        assertEquals(1, compareTest.length);
        assertNotNull(compareTest[0]);

        assertEquals("ID3", compareTest[0].getEventID());
    }

    @Test
    public void testQueryByPersonAndType_InvalidType() throws DataAccessException {
        Event[] compareTest = ((EventDAO)dao).queryByPersonAndType("personID1", "mawwiage");
        assertNull(compareTest);
    }

    @Test
    public void testQueryByPersonAndType_InvalidPersonID() throws DataAccessException {
        Event[] compareTest = ((EventDAO)dao).queryByPersonAndType("badID", "death");
        assertNull(compareTest);
    }
}
