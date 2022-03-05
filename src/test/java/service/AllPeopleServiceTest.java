package service;

import dao.*;
import model.AuthToken;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.AllPeopleResult;

import static org.junit.jupiter.api.Assertions.*;

public class AllPeopleServiceTest extends ServiceTest {
    private Database db;
    private AllPeopleService service = new AllPeopleService();
    private final String username = "Boop";
    private final String authTokenString = "token";

    @BeforeEach
    public void insertData() throws DataAccessException {
        clearService.clear();
        db = new Database();

        User user = new User(username, "password", "email", "firstName", "lastName", "f", "ID");
        new UserDAO(db.getConnection()).insert(user);
        AuthToken authToken = new AuthToken(authTokenString, username);
        new AuthTokenDAO(db.getConnection()).insert(authToken);

        Person person1 = new Person("ID1", username, "name", "name", "f", null, null, null);
        Person person2 = new Person("ID2", username, "name", "name", "f", null, null, null);
        Person person3 = new Person("ID3", "notUsername", "name", "name", "f", null, null, null);

        PersonDAO dao = new PersonDAO(db.getConnection());
        dao.insert(person1);
        dao.insert(person2);
        dao.insert(person3);

        db.closeConnection(true);
    }

    @Test
    public void testPeople() {
        AllPeopleResult result = service.people(authTokenString);

        assertNotNull(result);
        assertNull(result.getMessage());
        assertTrue(result.isSuccess());
        assertNotNull(result.getData());
        assertEquals(2, result.getData().length);
    }

    @Test
    public void testInvalidAuthtoken() {
        AllPeopleResult result = service.people("wrongAuthtoken");

        assertNotNull(result);
        assertEquals("Error: Invalid authtoken", result.getMessage());
        assertFalse(result.isSuccess());
    }
}
