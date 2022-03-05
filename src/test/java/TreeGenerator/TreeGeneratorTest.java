package TreeGenerator;

import com.sun.source.tree.Tree;
import dao.DataAccessException;
import dao.Database;
import dao.EventDAO;
import dao.PersonDAO;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import treeGenerator.TreeGenerator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class TreeGeneratorTest {
    private static final Logger logger = Logger.getLogger("TreeGeneratorTest");
    private Database db;
    private final String associatedUsername = "username";
    private User testUser = new User(
            associatedUsername,
            "password",
            "email",
            "firstName",
            "lastName",
            "f",
            "personID");
    TreeGenerator generator;

    @BeforeAll
    public static void setLoggerLevel() {
        Level level = Level.FINE;
        logger.setLevel(level);

        logger.setUseParentHandlers(false);
        Handler handler = new ConsoleHandler();
        handler.setLevel(level);
        logger.addHandler(handler);
    }

    @BeforeEach
    public void setUp() throws DataAccessException, SQLException {
        db = new Database();
        Connection conn = db.getConnection();
        generator = new TreeGenerator(conn, testUser);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void testGetBirthYear() throws DataAccessException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        EventDAO eventDao = new EventDAO(db.getConnection());
        String personID = "abc";
        int expectedYear = 1900;

        Event testEvent = new Event(associatedUsername, personID, 1, 1, "USA", "New York",
                "birth", expectedYear);
        eventDao.insert(testEvent);

        Method methodCall = TreeGenerator.class.getDeclaredMethod("getBirthYear", String.class);
        methodCall.setAccessible(true);
        int compareYear = (int) methodCall.invoke(generator, personID);

        assertEquals(expectedYear, compareYear);
    }

    @Test
    public void testSetRandomName() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String gender = "m";
        Person person = new Person(associatedUsername, null, null, gender, null, null, null);

        Method methodCall = TreeGenerator.class.getDeclaredMethod("setRandomName", Person.class);
        methodCall.setAccessible(true);
        methodCall.invoke(generator, person);

        String firstName = person.getFirstName();
        String lastName = person.getLastName();

        assertNotNull(firstName);
        assertNotNull(lastName);

        logger.fine("First Name: " + firstName + ", Last Name: " + lastName);
    }

}