package dao;

import model.AuthToken;
import model.Model;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDAOTest extends DAOTest {
    @Override
    protected void initializeInstanceVariables(Connection conn) {
        this.primaryKey = "ID";
        this.model = new Person(primaryKey, "username", "firstName", "lastName", "f", "father", "mother", "spouse");
        this.dao = new PersonDAO(conn);
    }

    @Test
    public void testQueryByUserPass() throws DataAccessException {
        String username = "U";
        Person person1 = new Person("ID1", username, "name", "name", "f", null, null, null);
        Person person2 = new Person("ID2", username, "name", "name", "f", null, null, null);
        Person person3 = new Person("ID3", "notUsername", "name", "name", "f", null, null, null);

        PersonDAO dao = new PersonDAO(db.getConnection());
        dao.insert(person1);
        dao.insert(person2);
        dao.insert(person3);

        Model[] people = dao.queryByUser(username);

        assertNotNull(people);
        assertEquals(2, people.length);
        assertNotNull( ( (Person) people[0]).getPersonID());
    }
}
