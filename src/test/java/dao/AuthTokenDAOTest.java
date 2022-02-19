package dao;

import model.AuthToken;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthTokenDAOTest extends DAOTest {
    private String testToken = "xxx";

    @Override
    protected void setModel() {
        model = new AuthToken(testToken, "username");
    }

    @Override
    protected void setDAO(Connection conn) {
        dao = new AuthTokenDAO(conn);
    }

    @Test
    public void testInsert() throws DataAccessException {
        super.testInsert(testToken);
    }

    @Test
    public void testQuery() throws DataAccessException {
        super.testQuery(testToken);
    }

    @Test
    public void testQueryFail() throws DataAccessException {
        super.testQueryFail(testToken);
    }

    @Test
    public void testClear() throws DataAccessException {
        super.testClear(testToken);
    }
}
