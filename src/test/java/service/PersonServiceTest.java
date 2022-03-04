package service;

import dao.*;
import model.AuthToken;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.PersonResult;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest extends ServiceTest {
    private final PersonService service = new PersonService();
    private final String username = "username";
    private final String authTokenString = "token";
    private final String testID = "ID1";

    @BeforeEach
    public void insertUser() throws DataAccessException {
        clearService.clear();
        Database db = new Database();

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
    public void testPersonRequestPass() {
        PersonResult result = service.person(authTokenString, testID);

        assertNotNull(result);
        assertNull(result.getMessage());
        assertTrue(result.isSuccess());
        assertEquals(username, result.getAssociatedUsername());
        assertEquals(testID, result.getPersonID());
    }

    @Test
    public void testPersonRequestInvalidAuthToken() {
        PersonResult result = service.person("invalid authtoken", testID);

        assertNotNull(result);
        assertEquals("Error: Invalid authtoken", result.getMessage());
        assertFalse(result.isSuccess());
    }

    @Test
    public void testPersonRequestInvalidID() {
        PersonResult result = service.person(authTokenString, "invalid ID");

        assertNotNull(result);
        assertEquals("Error: Invalid ID", result.getMessage());
        assertFalse(result.isSuccess());
    }

    @Test
    public void testPersonRequestInvalidUsername() {
        PersonResult result = service.person(authTokenString, "ID3");

        assertNotNull(result);
        assertEquals("Error: This user is not associated with the requested data", result.getMessage());
        assertFalse(result.isSuccess());
    }
}
