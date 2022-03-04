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

public class RegisterServiceTest extends ServiceTest {
    private final RegisterService service = new RegisterService();

    @BeforeEach //TODO: Reduce shared code with login request test
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

    @Test
    public void testRegister() throws DataAccessException {
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

        Database db = new Database();
        UserDAO dao = new UserDAO(db.getConnection());
        User testUser = dao.query("uniqueUsername");
        db.closeConnection(false);
        assertNotNull(testUser);
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
