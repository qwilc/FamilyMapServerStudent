package service;

import dao.DataAccessException;
import dao.Database;
import dao.UserDAO;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import result.LoginRegisterResult;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest extends ServiceTest {
    private LoginService service = new LoginService();

    @BeforeEach
    public void insertUsers() throws DataAccessException {
        clearService.clear();

        Database db = new Database();

        User user = new User("username", "password", "email", "firstName", "lastName", "f", "ID");
        new UserDAO(db.getConnection()).insert(user);

        User user1 = new User("username1", "abcdefg", "email", "firstName", "lastName", "f", "ID1");
        new UserDAO(db.getConnection()).insert(user1);

        User user2 = new User("username2", "p4ssw0rd", "email", "firstName", "lastName", "f", "ID2");
        new UserDAO(db.getConnection()).insert(user2);

        db.closeConnection(true);
    }

    @AfterEach
    public void tearDown() {
        clearService.clear();
    }

    @Test
    public void testLogin() throws DataAccessException {
        //testing first login
        LoginRequest request = new LoginRequest();
        request.setUsername("username1");
        request.setPassword("abcdefg");

        LoginRegisterResult result = service.login(request);

        assertNotNull(result);
        assertNotNull(result.getAuthtoken());
        assertEquals("username1", result.getUsername());
        assertEquals("ID1", result.getPersonID());

        //testing second login
        request.setUsername("username2");
        request.setPassword("p4ssw0rd");

        result = service.login(request);

        assertNotNull(result);
        assertNotNull(result.getAuthtoken());
        assertEquals("username2", result.getUsername());
        assertEquals("ID2", result.getPersonID());
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());
    }

    @Test
    public void testInvalidUsername() {
        LoginRequest request = new LoginRequest();
        request.setUsername("user");
        request.setPassword("password");

        LoginRegisterResult result = service.login(request);

        assertNotNull(result);
        assertEquals("Error: Invalid username", result.getMessage());
        assertFalse(result.isSuccess());
        assertNull(result.getUsername());
        assertNull(result.getPersonID());
        assertNull(result.getAuthtoken());
    }

    @Test
    public void testInvalidPassword() {
        LoginRequest request = new LoginRequest();
        request.setUsername("username");
        request.setPassword("12345");

        LoginRegisterResult result = service.login(request);

        assertNotNull(result);
        assertEquals("Error: Invalid password", result.getMessage());
        assertFalse(result.isSuccess());
        assertNull(result.getUsername());
        assertNull(result.getPersonID());
        assertNull(result.getAuthtoken());

    }

    @Test
    public void testLoginEmptyDatabase() {
        clearService.clear();

        LoginRequest request = new LoginRequest();
        request.setUsername("username");
        request.setPassword("12345");

        LoginRegisterResult result = service.login(request);

        assertNotNull(result);
        assertEquals("Error: Invalid username", result.getMessage());
        assertFalse(result.isSuccess());
        assertNull(result.getUsername());
        assertNull(result.getPersonID());
        assertNull(result.getAuthtoken());
    }
}
