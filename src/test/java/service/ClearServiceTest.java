package service;

import dao.*;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.Result;

import java.security.Provider;

import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTest {
    private ClearService service = new ClearService();

    @Test
    public void testClearDatabase() throws DataAccessException {
        Database db = new Database();

        AuthToken authToken = new AuthToken("username", "string");
        new AuthTokenDAO(db.getConnection()).insert(authToken);

        Event event = new Event("eventID", "username", "personID", 1, 2, "country",
                "city", "type", 1900);
        new EventDAO(db.getConnection()).insert(event);

        Person person = new Person("testID", "username", "firstName", "lastName", "f", "father", "mother", "spouse");
        new PersonDAO(db.getConnection()).insert(person);

        User user = new User("username", "password", "email", "firstName", "lastName", "f", "ID");
        new UserDAO(db.getConnection()).insert(user);
        db.closeConnection(true);

        Result result = service.clear();

        assertNotNull(result);
        assertEquals("Clear succeeded.", result.getMessage());
        assertTrue(result.isSuccess());
        assertNull(new UserDAO(db.getConnection()).query("username"));
        assertNull(new PersonDAO(db.getConnection()).query("testID"));
        assertNull(new EventDAO(db.getConnection()).query("eventID"));
        db.closeConnection(true);
    }

    @Test
    public void testClearEmptyDatabase() {
        Result result = service.clear();

        assertNotNull(result);
        assertEquals("Clear succeeded.", result.getMessage());
        assertTrue(result.isSuccess());
    }
}
