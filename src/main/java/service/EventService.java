package service;

import dao.*;
import model.AuthToken;
import model.Event;
import result.EventResult;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The EventService class performs the functionality for event requests with a specified ID
 */
public class EventService {
    /**
     * Finds and returns data for the event with the given event ID if that event exists and is associated with the user indicated by the authentication token
     *
     * @param authtoken the authentication token
     * @param eventID the event identification
     * @return an EventResult with the outcome of the request
     */
    public EventResult event(String authtoken, String eventID) {
        Database database = new Database();
        EventResult result = new EventResult(null, false, null, null, null, 0, 0, null, null, null, 0);

        try(Connection conn = database.getConnection()) {
            AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);

            AuthToken token = authTokenDAO.query(authtoken);

            if(token != null) {
                String username = token.getUsername();
                EventDAO dao = new EventDAO(conn);
                Event event = dao.query(eventID);

                if(event == null) {
                    result.setMessage("Error: Invalid event ID");
                    result.setSuccess(false);
                }
                else if(event.getAssociatedUsername().equals(username)) {
                    result.setDataFromEvent(event);
                    result.setSuccess(true);
                }
                else {
                    result.setMessage("Error: This event is not associated with this user");
                    result.setSuccess(false);
                }
            }
            else {
                result.setMessage("Error: Invalid authtoken");
                result.setSuccess(false);
            }
            database.closeConnection(true);
        }
        catch (SQLException | DataAccessException ex) {
            result.setMessage("Error: Could not get event data");
            result.setSuccess(false);
            ex.printStackTrace();
        }

        return result;
    }
}
