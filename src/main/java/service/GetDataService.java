//package service;
//
//import dao.AuthTokenDAO;
//import dao.DataAccessException;
//import dao.Database;
//import dao.PersonDAO;
//import model.Person;
//import result.AllPeopleResult;
//import result.Result;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//public abstract class GetDataService {
//    Database database;
//    Result result;
//
//    protected Result getData(String authtoken, String username) {
//        Database database = new Database();
//        initializeResult();
//
//        try(Connection conn = database.getConnection()) {
//            AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);
//
//            if(authTokenDAO.isValidAuthToken(authtoken, username)) {
//                getData(conn);
//                result.setSuccess(true);
//            }
//            else {
//                result.setMessage("Error: Invalid authtoken");
//                result.setSuccess(false);
//            }
//        } catch (SQLException | DataAccessException ex) {
//            result.setMessage("Error: Could not get all people associated with user");
//            result.setSuccess(false);
//            ex.printStackTrace();
//        }
//
//        return result;
//    }
//
//    protected abstract void initializeResult();
//
//    protected abstract void getData(Connection conn, String username);
//}
