package dao;

import model.Event;
import model.Model;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public abstract class DAOTest {
    protected Database db;
    protected Model model;
    protected DAO dao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        this.setModel();
        Connection conn = db.getConnection();
        this.setDAO(conn);
    }

    protected abstract void setModel();

    protected abstract void setDAO(Connection conn);

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    public void testInsert(String id) throws DataAccessException {
        dao.insert(model);

        Model compareTest = dao.query(id);

        assertNotNull(compareTest);

        assertEquals(model, compareTest);
    }

    public void testInsertFail() throws DataAccessException {
        dao.insert(model);

        assertThrows(DataAccessException.class, ()-> dao.insert(model));
    }

    public void testQuery(String id) throws DataAccessException {
        dao.insert(model);

        Model compareTest = dao.query(id);

        assertNotNull(compareTest);

        assertEquals(model, compareTest);
    }

    public void testQueryFail(String id) throws DataAccessException {
        assertNull(dao.query(id));
    }

    public void testClear(String id) throws DataAccessException {
        dao.insert(model);
        dao.clear();
        assertNull(dao.query("id"));
    }
}
