package service;

import dao.*;
import result.Result;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The ClearService class performs the functionality for clear requests
 */
public class ClearService {
    /**
     * Clears all data from the database
     *
     * @return a Result object with the outcome of the request
     */
    public Result clear() {
        Database database = new Database();
        Result result;

        try(Connection conn = database.openConnection()) {
            new AuthTokenDAO(conn).clear();
            new EventDAO(conn).clear();
            new PersonDAO(conn).clear();
            new UserDAO(conn).clear();

            result = new Result("Clear succeeded.", true);
            database.closeConnection(true);
        }
        catch(DataAccessException | SQLException ex) {
            result = new Result("Error:Clear service was unsuccessful", false);
            ex.printStackTrace();
        }

        return result;
    }
}
