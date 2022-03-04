package service;

import dao.*;
import model.AuthToken;
import model.Model;
import model.Person;
import result.AllPeopleResult;
import result.Result;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The AllPeopleService class performs the functionality for person requests without a specified ID
 */
public class AllPeopleService extends GetDataService {
    /**
     * Finds and returns data for all the people associated with the username associated with the given authentication token
     *
     * @param authtoken the authentication token
     * @return an AllPeopleResult object with the outcome of the request
     */
    public AllPeopleResult people(String authtoken) {
        return (AllPeopleResult) super.getData(authtoken);
//        Database database = new Database();
//        AllPeopleResult result = new AllPeopleResult(null, false, null);
//
//        try(Connection conn = database.getConnection()) {
//            AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);
//
//            AuthToken token = authTokenDAO.query(authtoken);
//
//            if(token != null) {
//                String username = token.getUsername();
//                PersonDAO personDAO = new PersonDAO(conn);
//                Model[] array = personDAO.queryByUser(username);
//                database.closeConnection(true);
//
//                Person[] people = new Person[array.length];
//                for(int i = 0; i < array.length; i++) {
//                    people[i] = (Person) array[i];
//                }
//
//                result.setData(people);
//                result.setSuccess(true);
//            }
//            else {
//                result.setMessage("Error: Invalid authtoken");
//                result.setSuccess(false);
//            }
//        }
//        catch (SQLException | DataAccessException ex) {
//            result.setMessage("Error: Could not get all people associated with user");
//            result.setSuccess(false);
//            ex.printStackTrace();
//        }
//
//        return result;
    }

    @Override
    protected void initializeResult() {
        result = new AllPeopleResult(null, false, null);
    }

    @Override
    protected DAO initializeDAO(Connection conn) {
        return new PersonDAO(conn);
    }

    @Override
    protected void setResultData(Model[] array, Result result) {
        Person[] people = new Person[array.length];
        for(int i = 0; i < array.length; i++) {
            people[i] = (Person) array[i];
        }

        ((AllPeopleResult) result).setData(people);
    }
}
