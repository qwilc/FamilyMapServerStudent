package service;

import dao.*;
import model.Event;
import model.Model;
import result.AllEventsResult;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The AllEventsService class performs the functionality for event requests without a specified ID
 */
public class AllEventsService {
    /**
     * Finds and returns data for all the events associated with the user indicated by the authentication token
     *
     * @param authtoken the authentication token
     * @return an AllEventsResult object with the outcome of the request
     */
    public AllEventsResult events(String authtoken, String username) {
        Database database = new Database();
        AllEventsResult result = new AllEventsResult(null, false, null);

        try(Connection conn = database.getConnection()) {
            AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);

            if(authTokenDAO.isValidAuthToken(authtoken, username)) {
                EventDAO eventDAO = new EventDAO(conn);

                Model[] array = eventDAO.queryByUser(username);
                Event[] events = new Event[array.length];

                for(int i = 0; i < array.length; i++) {
                    events[i] = (Event) array[i];
                }

                result.setData(events);
                result.setSuccess(true);
            }
            else {
                result.setMessage("Error: Invalid authtoken");
                result.setSuccess(false);
            }
            database.closeConnection(true);
        } catch (SQLException | DataAccessException ex) {
            result.setMessage("Error: Could not get all people associated with user");
            result.setSuccess(false);
            ex.printStackTrace();
        }

        return result;
    }
}
