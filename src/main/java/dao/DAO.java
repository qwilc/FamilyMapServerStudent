package dao;

import model.AuthToken;
import model.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DAO {
    protected final Connection conn;

    public DAO(Connection conn) {
        this.conn = conn;
    }

    protected abstract void insert(Model model) throws DataAccessException;

    public Model query(String identifier) throws DataAccessException {
        return null;
    }

    public void clear(String table) throws DataAccessException {
        String sql = "delete from " + table;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException();
        }
    }

    public void clear() throws DataAccessException {}
}
