package service;

import dao.*;
import model.AuthToken;
import model.Event;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.AllEventsResult;
import result.AllPeopleResult;

import static org.junit.jupiter.api.Assertions.*;

public class AllEventsServiceTest extends ServiceTest{
    private Database db;
    private AllEventsService service = new AllEventsService();
    private final String username = "username";
    private final String authTokenString = "token";

    @BeforeEach
    public void insertUser() throws DataAccessException {
        clearService.clear();
        db = new Database();

        User user = new User(username, "password", "email", "firstName", "lastName", "f", "ID");
        new UserDAO(db.getConnection()).insert(user);
        AuthToken authToken = new AuthToken(authTokenString, username);
        new AuthTokenDAO(db.getConnection()).insert(authToken);

        Event event = new Event("ID1", "username", "personID1", 1, 2, "country",
                "city", "type", 1900);
        Event event1 = new Event("ID2", "username", "personID2", 1, 2, "country",
                "city", "type", 1900);
        Event event2 = new Event("ID3", "notUsername", "personID1", 1, 2, "country",
                "city", "death", 1900);

        EventDAO dao = new EventDAO(db.getConnection());
        dao.insert(event);
        dao.insert(event1);
        dao.insert(event2);

        db.closeConnection(true);
    }

    @Test
    public void testGetAllEvents() throws DataAccessException {
        AllEventsResult result = service.events(authTokenString);

        assertNotNull(result);
        assertNull(result.getMessage());
        assertTrue(result.isSuccess());
        assertNotNull(result.getData());
        assertEquals(2, result.getData().length);
    }

    @Test
    public void testInvalidAuthtoken() {
        AllEventsResult result = service.events("wrongAuthtoken");

        assertNotNull(result);
        assertEquals("Error: Invalid authtoken", result.getMessage());
        assertFalse(result.isSuccess());
    }

}
