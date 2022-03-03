package service;

import dao.DataAccessException;
import dao.Database;
import dao.UserDAO;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;
import result.LoginRegisterResult;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {
    private final RegisterService service = new RegisterService();

    @BeforeEach //TODO: I literally just copied this from the login request test
    public void insertUsers() throws DataAccessException {
        Database db = new Database();
        new ClearService().clear();

        User user = new User("username", "password", "email", "firstName", "lastName", "f", "ID");
        new UserDAO(db.getConnection()).insert(user);

        User user1 = new User("username1", "abcdefg", "email", "firstName", "lastName", "f", "ID1");
        new UserDAO(db.getConnection()).insert(user1);

        User user2 = new User("username2", "p4ssw0rd", "email", "firstName", "lastName", "f", "ID2");
        new UserDAO(db.getConnection()).insert(user2);

        db.closeConnection(true);
    }

    @Test
    public void testRegister() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("testName");
        request.setLastName("testSurname");
        request.setEmail("something@something.com");
        request.setGender("f");
        request.setPassword("pass_word");
        request.setUsername("uniqueUsername");

        LoginRegisterResult result = service.register(request);
        assertNotNull(result);
        assertNotNull(result.getAuthtoken());
        assertNotNull(result.getPersonID());
        assertEquals("uniqueUsername", result.getUsername());
    }

    @Test
    public void testDuplicateUsername() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("testName");
        request.setLastName("testSurname");
        request.setEmail("something@something.com");
        request.setGender("f");
        request.setPassword("pass_word");
        request.setUsername("username1");

        LoginRegisterResult result = service.register(request);
        assertNotNull(result);
        assertEquals("Error: This username is not available", result.getMessage());
        assertFalse(result.isSuccess());
    }

    @Test
    public void testEmptyEmail() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("testName");
        request.setLastName("testSurname");
        request.setEmail("");
        request.setGender("f");
        request.setPassword("pass_word");
        request.setUsername("username1");

        LoginRegisterResult result = service.register(request);
        assertNotNull(result);
        assertEquals("Error: Invalid or empty field", result.getMessage());
        assertFalse(result.isSuccess());
    }
}
