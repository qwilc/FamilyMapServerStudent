package service;

import com.google.gson.Gson;
import dao.DataAccessException;
import dao.Database;
import dao.EventDAO;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoadRequest;
import result.Result;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class LoadServiceTest extends ServiceTest {
    private Logger logger = Logger.getLogger("LoadServiceTest");
    private LoadService service = new LoadService();

    @BeforeEach
    public void clear() throws DataAccessException {
        clearService.clear();
    }

    @Test
    public void testLoadPass() throws DataAccessException {
        User user = new User("username", "password", "email", "firstName", "lastName", "f", "ID");
        User user1 = new User("username1", "abcdefg", "email", "firstName", "lastName", "f", "ID1");
        User user2 = new User("username2", "p4ssw0rd", "email", "firstName", "lastName", "f", "ID2");

        User[] users = new User[3];
        users[0] = user;
        users[1] = user1;
        users[2] = user2;

        Person person = new Person("ID1", "username", "name", "name", "f", null, null, null);
        Person person1 = new Person("ID2", "username", "name", "name", "f", null, null, null);
        Person person2 = new Person("ID3", "notUsername", "name", "name", "f", null, null, null);

        Person[] people = new Person[3];
        people[0] = person;
        people[1] = person1;
        people[2] = person2;

        Event event = new Event("ID1", "username", "personID1", 1, 2, "country",
                "city", "type", 1900);
        Event event1 = new Event("ID2", "username", "personID2", 1, 2, "country",
                "city", "type", 1900);
        Event event2 = new Event("ID3", "username", "personID1", 1, 2, "country",
                "city", "death", 1900);

        Event[] events = new Event[3];
        events[0] = event;
        events[1] = event1;
        events[2] = event2;


        LoadRequest request = new LoadRequest();
        request.setEvents(events);
        request.setPersons(people);
        request.setUsers(users);

        Result result = service.load(request);

        assertNotNull(result);
        assertTrue(result.isSuccess());
        assertEquals("Successfully added 3 users, 3 persons, and 3 events to the database.", result.getMessage());

        Database db = new Database();
        EventDAO eventDAO = new EventDAO(db.getConnection());
        Event testEvent = eventDAO.query("ID2");
        db.closeConnection(false);
        assertNotNull(testEvent);
    }
}
