package service;

import dao.*;
import model.AuthToken;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.AllPeopleResult;
import result.PersonResult;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest extends ServiceTest {
    private Database db;
    private PersonService service = new PersonService();
    private final String username = "username";
    private final String authTokenString = "token";
    private String testID = "ID1";
//
//    @BeforeEach
//    @Override
//    public void setUp() {
//        super.setUp();
//    }

    @BeforeEach
    public void insertUser() throws DataAccessException {
        clearService.clear();
        db = new Database();

        User user = new User(username, "password", "email", "firstName", "lastName", "f", "ID");
        new UserDAO(db.getConnection()).insert(user);
        AuthToken authToken = new AuthToken(authTokenString, username);
        new AuthTokenDAO(db.getConnection()).insert(authToken);

        Person person1 = new Person(testID, username, "name", "name", "f", null, null, null);
        Person person2 = new Person("ID2", username, "name", "name", "f", null, null, null);
        Person person3 = new Person("ID3", "notUsername", "name", "name", "f", null, null, null);

        PersonDAO dao = new PersonDAO(db.getConnection());
        dao.insert(person1);
        dao.insert(person2);
        dao.insert(person3);

        db.closeConnection(true);
    }

    @Test
    public void testPersonRequestPass() throws DataAccessException {
        PersonResult result = service.person(authTokenString, username, testID);

        assertNotNull(result);
        assertNull(result.getMessage());
        assertTrue(result.isSuccess());
        assertEquals(username, result.getAssociatedUsername());
        assertEquals(testID, result.getPersonID());
    }

    @Test
    public void testPersonRequestInvalidAuthToken() throws DataAccessException {
        PersonResult result = service.person("invalid authtoken", username, testID);

        assertNotNull(result);
        assertEquals("Error: Invalid authtoken", result.getMessage());
        assertFalse(result.isSuccess());
    }

    @Test
    public void testPersonRequestInvalidID() throws DataAccessException {
        PersonResult result = service.person(authTokenString, username, "invalid ID");

        assertNotNull(result);
        assertEquals("Error: Invalid person ID", result.getMessage());
        assertFalse(result.isSuccess());
    }

    @Test
    public void testPersonRequestInvalidUsername() throws DataAccessException {
        PersonResult result = service.person(authTokenString, username, "ID3");

        assertNotNull(result);
        assertEquals("Error: This person is not associated with this user", result.getMessage());
        assertFalse(result.isSuccess());
    }
}
