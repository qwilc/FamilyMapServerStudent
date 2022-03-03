package service;

import dao.AuthTokenDAO;
import dao.DataAccessException;
import dao.Database;
import dao.PersonDAO;
import model.Person;
import result.AllPeopleResult;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The AllPeopleService class performs the functionality for person requests without a specified ID
 */
public class AllPeopleService {
    /**
     * Finds and returns data for all the people associated with the username associated with the given authentication token
     *
     * @param authtoken the authentication token
     * @return an AllPeopleResult object with the outcome of the request
     */
    public AllPeopleResult people(String authtoken, String username) {
        Database database = new Database();
        AllPeopleResult result = new AllPeopleResult(null, false, null);

        try(Connection conn = database.getConnection()) {
            AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);
            if(authTokenDAO.isValidAuthToken(authtoken, username)) {
                PersonDAO personDAO = new PersonDAO(conn);
                Person[] people = personDAO.queryByUser(username);
                result.setData(people);
                result.setSuccess(true);
            }
            else {
                result.setMessage("Error: Invalid authtoken");
                result.setSuccess(false);
            }
        } catch (SQLException | DataAccessException ex) {
            result.setMessage("Error: Could not get all people associated with user");
            result.setSuccess(false);
            ex.printStackTrace();
        }

        return result;
    }
}
