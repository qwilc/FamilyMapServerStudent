package dao;

import model.User;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The UserDAO class handles connecting to the database in order to query or modify the user table
 */
public class UserDAO {
    /**
     * The connection to the database
     */
    private final Connection conn;

    /**
     * Creates a UserDAO object
     *
     * @param conn the database connection
     */
    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts the specified user into the table
     *
     * @param user the user to insert
     * @throws DataAccessException if an SQLException is caught
     */
    public void insert(User user) throws DataAccessException {
        String sql = "insert into user (username, password, email, firstName, lastName, gender, personID) " +
                "values (?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException();
        }
    }

    /**
     * Returns the user with the specified username
     *
     * @param username the username
     * @return a User object representing the user
     * @throws DataAccessException if an SQLException is caught
     */
    public User query(String username) throws DataAccessException {
        User user;
        ResultSet rs;

        String sql = "select * from user where username = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if(rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"),
                        rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("personID"));
                return user;
            }
            else {
                return null;
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException();
        }
    }

    /**
     * Deletes the user with the specified username
     *
     * @param username the username
     * @throws DataAccessException if an SQLException is caught
     */
    public void delete(String username) throws DataAccessException {}

    /**
     * Clears all rows of the user table in the database
     * @throws DataAccessException if an SQLException is caught
     */
    public void clear() throws DataAccessException {
        String sql = "delete from user";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException();
        }
    }
}
