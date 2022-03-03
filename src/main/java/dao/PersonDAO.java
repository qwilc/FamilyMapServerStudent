package dao;

import model.Event;
import model.Model;
import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The PersonDAO class handles connecting to the database in order to query or modify the person table
 */
public class PersonDAO extends DAO{

    /**
     * Creates a PersonDAO object
     *
     * @param conn the database connection
     */
    public PersonDAO(Connection conn) {
        super(conn);
    }

    /**
     * Inserts the specified person into the table
     *
     * @param person the person to insert
     * @throws DataAccessException if an SQLException is caught
     */
    @Override
    public void insert(Model person) throws DataAccessException {
        String sql = "insert into person(personID, associatedUsername, firstName, lastName, gender, " +
                "fatherID, motherID, spouseID) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ((Person) person).getPersonID());
            stmt.setString(2, ((Person) person).getAssociatedUsername());
            stmt.setString(3, ((Person) person).getFirstName());
            stmt.setString(4, ((Person) person).getLastName());
            stmt.setString(5, ((Person) person).getGender());
            stmt.setString(6, ((Person) person).getFatherID());
            stmt.setString(7, ((Person) person).getMotherID());
            stmt.setString(8, ((Person) person).getSpouseID());

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

    public Person[] queryByUser(String username) throws DataAccessException {
        Person person;
        ArrayList<Person> people = new ArrayList<>();
        ResultSet rs;

        String sql = "select * from person where associatedUsername = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            int i = 0;
            while(rs.next()) {
                person = new Person(
                        rs.getString("personID"),
                        rs.getString("associatedUsername"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("gender"),
                        rs.getString("fatherID"),
                        rs.getString("motherID"),
                        rs.getString("spouseID") );
                people.add(person);
                i++;
            }

            if(i == 0) {
                return null;
            }
            else {
                return people.toArray(new Person[0]);
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

    public void deleteUserPeople() throws DataAccessException {}

    /**
     * Clears all rows of the person table in the database
     * @throws DataAccessException if an SQLException is caught
     */
    @Override
    public void clear() throws DataAccessException {
        super.clear("person");
    }
}
