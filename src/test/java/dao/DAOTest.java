package dao;

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
    protected String primaryKey;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        this.initializeInstanceVariables(conn);
    }

    protected abstract void initializeInstanceVariables(Connection conn);

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void testInsert() throws DataAccessException {
        dao.insert(model);

        Model compareTest = dao.query(primaryKey);

        assertNotNull(compareTest);

        assertEquals(model, compareTest);
    }

    @Test
    public void testInsertFail() throws DataAccessException {
        dao.insert(model);

        assertThrows(DataAccessException.class, ()-> dao.insert(model));
    }

    @Test
    public void testQuery() throws DataAccessException {
        dao.insert(model);

        Model compareTest = dao.query(primaryKey);

        assertNotNull(compareTest);

        assertEquals(model, compareTest);
    }

    @Test
    public void testQueryFail() throws DataAccessException {
        assertNull(dao.query(primaryKey));
    }

    @Test
    public void testClear() throws DataAccessException {
        dao.insert(model);
        dao.clear();
        assertNull(dao.query(primaryKey));
    }
}
