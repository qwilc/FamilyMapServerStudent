package service;

import dao.AuthTokenDAO;
import dao.DataAccessException;
import dao.Database;
import dao.PersonDAO;
import model.Person;
import result.PersonResult;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The PersonService class performs the functionality for person requests with a specified ID
 */
public class PersonService {
    /**
     * Finds and returns data for the person specified by the person ID if that person is associated with the user indicated by the authentication token
     *
     * @param authtoken the authentication token
     * @param personID the person ID
     * @return a PersonResult object with the outcome of the person request
     */
    public PersonResult person(String authtoken, String username, String personID) {
        Database database = new Database();
        PersonResult result = new PersonResult(null, false, null, null, null, null, null, null, null, null);

        try(Connection conn = database.getConnection()) {
            AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);

            if(authTokenDAO.isValidAuthToken(authtoken, username)) {
                PersonDAO dao = new PersonDAO(conn);
                Person person = dao.query(personID);

                if(person == null) {
                    result.setMessage("Error: Invalid person ID");
                    result.setSuccess(false);
                }
                else if(person.getAssociatedUsername().equals(username)) {
                    result.setDataFromPerson(person);
                    result.setSuccess(true);
                }
                else {
                    result.setMessage("Error: This person is not associated with this user");
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
            result.setMessage("Error: Could not get person data");
            result.setSuccess(false);
            ex.printStackTrace();
        }

        return result;
    }
}
