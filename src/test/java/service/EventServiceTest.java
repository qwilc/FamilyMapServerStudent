package service;

import dao.AuthTokenDAO;
import dao.DataAccessException;
import dao.Database;
import dao.EventDAO;
import model.AuthToken;
import model.Event;
import org.junit.jupiter.api.Test;
import result.EventResult;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class EventServiceTest extends ServiceTest {
    private EventService service = new EventService();

    @Test
    public void testEventPass() throws DataAccessException {
        clearService.clear();

        String authToken = "token";
        String username = "username";
        String eventID = "ID";

        Event testEvent = new Event(
                eventID,
                username,
                "personID",
                0,
                0,
                "country",
                "city",
                "type",
                2000 );

        Database db = new Database();
        Connection conn = db.getConnection();

        AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);
        authTokenDAO.insert(new AuthToken(authToken, username));

        EventDAO eventDAO = new EventDAO(conn);
        eventDAO.insert(testEvent);
        db.closeConnection(true);

        EventResult result = service.event(authToken, eventID);

        assertNotNull(result);
        assertNull(result.getMessage());
        assertTrue(result.isSuccess());
    }
}
