package service;

import dao.*;
import model.AuthToken;
import model.Model;
import result.Result;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class GetDataService {
    Database database;
    Result result;

    protected Result getData(String authtoken) {
        Database database = new Database();
        initializeResult();

        try(Connection conn = database.getConnection()) {
            AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);

            AuthToken token = authTokenDAO.query(authtoken);

            if(token != null) {
                Model[] array = getDataArray(conn, token.getUsername());
                database.closeConnection(true);

                setResultData(array, result);
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

    protected abstract void initializeResult();

    protected Model[] getDataArray(Connection conn, String username) throws DataAccessException {
        DAO dao = initializeDAO(conn);
        Model[] array = dao.queryByUser(username);
        return array;
    }

    protected abstract DAO initializeDAO(Connection conn);

    protected abstract void setResultData(Model[] array, Result result);
}
