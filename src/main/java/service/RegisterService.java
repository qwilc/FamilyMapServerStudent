package service;

import dao.AuthTokenDAO;
import dao.DataAccessException;
import dao.Database;
import dao.UserDAO;
import model.AuthToken;
import model.User;
import request.RegisterRequest;
import result.LoginRegisterResult;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

/**
 * The RegisterService class performs the functionality for register requests
 */
public class RegisterService {
    /**
     * Registers the user and provides an authentication token if the inputted information is valid
     *
     * @param request the request containing the inputted information
     * @return a LoginResult with the outcome of the register request
     */
    public LoginRegisterResult register(RegisterRequest request) {
        Database database = new Database();
        LoginRegisterResult result = new LoginRegisterResult(null, false, null, null, null);

        try(Connection conn = database.openConnection()) {
            String username = request.getUsername();
            String password = request.getPassword();
            String email = request.getEmail();
            String firstName = request.getFirstName();
            String lastName = request.getLastName();
            String gender = request.getGender();

            UserDAO dao = new UserDAO(conn);

            if(dao.query(username) != null) {
                result.setMessage("Error: Registration was unsuccessful");
                result.setSuccess(false);
            }
            else {
                String personID = UUID.randomUUID().toString();
                result.setPersonID(personID);

                User user = new User(username, password, email, firstName, lastName, gender, personID);
                dao.insert(user);

                //TODO: These 6 lines are in both register and login services
                result.setUsername(username);
                result.setSuccess(true);

                String authToken = UUID.randomUUID().toString();
                new AuthTokenDAO(conn).insert(new AuthToken(authToken, username));
                result.setAuthtoken(authToken);

                database.closeConnection(true);

                //TODO: generate family stuff
            }
        }
        catch (SQLException | DataAccessException ex) {
            result.setMessage("Error: Registration was unsuccessful");
            result.setSuccess(false);
            ex.printStackTrace();
        }
        return result;
    }

}
