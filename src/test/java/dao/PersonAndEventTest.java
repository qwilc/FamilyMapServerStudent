package dao;

import model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class PersonAndEventTest extends DAOTest {
    @Test
    public void testQueryByUser() throws DataAccessException {
        dao.insert(model);

        Model[] compareTest = dao.queryByUser(model.getAssociatedUsername());

        assertNotNull(compareTest);

        Model[] array = {model};

        assertEquals(array[0], compareTest[0]);
    }

    @Test
    public void testQueryByUserFail() throws DataAccessException {
        assertNull(dao.queryByUser(model.getAssociatedUsername()));
    }
}
