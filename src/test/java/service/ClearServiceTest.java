package service;

import dao.AuthTokenDAO;
import dao.DataAccessException;
import dao.Database;
import model.AuthToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.Result;

import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTest {
    private ClearService service;

    @BeforeEach
    public void setUp() {
        service = new ClearService();
    }

    @Test
    public void testClearDatabase() throws DataAccessException {
        Database db = new Database();
        AuthToken authToken = new AuthToken("username", "string");
        new AuthTokenDAO(db.getConnection()).insert(authToken);

        Result result = service.clear();

        assertNotNull(result);
        assertEquals("Clear succeeded.", result.getMessage());
        assertTrue(result.isSuccess());
    }

    @Test
    public void testClearEmptyDatabase() {
        Result result = service.clear();

        assertNotNull(result);
        assertEquals("Clear succeeded.", result.getMessage());
        assertTrue(result.isSuccess());
    }
}
