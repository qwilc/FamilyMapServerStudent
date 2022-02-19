package dao;

import model.Event;
import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The PersonDAO class handles connecting to the database in order to query or modify the person table
 */
public class PersonDAO {
    /**
     * The connection to the database
     */
    private final Connection conn;

    /**
     * Creates a PersonDAO object
     *
     * @param conn the database connection
     */
    public PersonDAO(Connection conn) { this.conn = conn; }

    /**
     * Inserts the specified person into the table
     *
     * @param person the person to insert
     * @throws DataAccessException if an SQLException is caught
     */
    public void insert(Person person) throws DataAccessException {
        String sql = "insert into person(personID, associatedUsername, firstName, lastName, gender, " +
                "fatherID, motherID, spouseID) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException();
        }
    }

    /**
     * Returns the person with the specified ID
     *
     * @param personID the ID
     * @return a Person object representing the person
     * @throws DataAccessException if an SQLException is caught
     */
    public Person query(String personID) throws DataAccessException {
        Person person;
        ResultSet rs;

        String sql = "select * from person where personID = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if(rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("fatherID"),
                        rs.getString("motherID"), rs.getString("spouseID"));
                return person;
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
     * Updates the person with the specified person ID
     *
     * @param person the Person object with the new data
     * @throws DataAccessException if an SQLException is caught
     */
    public void update(Person person) throws DataAccessException {}

    /**
     * Deletes the person with the specified ID
     *
     * @param personID the ID
     * @throws DataAccessException if an SQLException is caught
     */
    public void delete(String personID) throws DataAccessException {}

    /**
     * Clears all rows of the person table in the database
     * @throws DataAccessException if an SQLException is caught
     */
    public void clear() throws DataAccessException {
        String sql = "delete from person";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException();
        }
    }
}
