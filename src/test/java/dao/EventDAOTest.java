package dao;

import model.Event;
import org.junit.jupiter.api.Test;
import service.ClearService;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class EventDAOTest extends DAOTest { //TODO: Apparently decimal latitude/longitude don't pass some of the tests?
    Event testEvent = new Event("ID1", "username", "personID1", 1, 2, "country",
            "city", "type", 1900);

    Event testEvent2 = new Event("ID2", "username", "personID2", 1, 2, "country",
            "city", "type", 1900);
    Event testEvent3 = new Event("ID3", "username", "personID1", 1, 2, "country",
            "city", "death", 1900);

    @Override
    protected void initializeInstanceVariables(Connection conn) {
        this.primaryKey = "ID";
        this.model = new Event(primaryKey, "username", "personID1", 1, 2, "country",
                "city", "type", 1900);
        this.dao = new EventDAO(conn);
    }

    @Test
    public void testQueryByPerson() throws DataAccessException {
        ClearService service = new ClearService();
        service.clear();

        dao.insert(testEvent);
        dao.insert(testEvent2);
        dao.insert(testEvent3);

        Event[] compareTest = ((EventDAO)dao).queryByPerson("personID1");

        assertEquals(2, compareTest.length);
        assertNotNull(compareTest[0]);
        assertNotNull(compareTest[1]);

        assertEquals("ID1", compareTest[0].getEventID());
        assertEquals("ID3", compareTest[1].getEventID());
    }

    @Test
    public void testQueryByPersonAndType() throws DataAccessException {
        ClearService service = new ClearService();
        service.clear();

        dao.insert(testEvent);
        dao.insert(testEvent2);
        dao.insert(testEvent3);

        Event[] compareTest = ((EventDAO)dao).queryByPersonAndType("personID1", "death");

        assertEquals(1, compareTest.length);
        assertNotNull(compareTest[0]);

        assertEquals("ID3", compareTest[0].getEventID());
    }
}
