package dao;

import model.Event;
import model.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The EventDAO class handles connecting to the database in order to query or modify the event table
 */
public class EventDAO extends DAO{
    /**
     * Creates an EventDAO object
     *
     * @param conn the database connection
     */
    public EventDAO(Connection conn) {
        super(conn);
    }

    /**
     * Inserts the specified event into the table
     *
     * @param event the event to insert
     * @throws DataAccessException if an SQLException is caught
     */
    @Override
    public void insert(Model event) throws DataAccessException {
        String sql = "insert into event (eventID, associatedUsername, personID, latitude, longitude, " +
                "country, city, eventType, year) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ((Event)event).getEventID());
            stmt.setString(2, ((Event) event).getAssociatedUsername());
            stmt.setString(3, ((Event) event).getPersonID());
            stmt.setDouble(4, ((Event) event).getLatitude());
            stmt.setDouble(5, ((Event) event).getLongitude());
            stmt.setString(6, ((Event) event).getCountry());
            stmt.setString(7, ((Event) event).getCity());
            stmt.setString(8, ((Event) event).getEventType());
            stmt.setInt(9, ((Event) event).getYear());

            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException();
        }
    }

    /**
     * Returns the event with the specified ID
     *
     * @param eventID the ID
     * @return an Event object representing the event
     * @throws DataAccessException if an SQLException is caught
     */
    public Event query(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs;

        String sql = "select * from event where eventID = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("eventID"), rs.getString("associatedUsername"),
                        rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                return event;
            }
            else {
                return null;
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataAccessException();
        }
    }

    /**
     * Deletes the event with the specified ID
     *
     * @param eventID the ID
     * @throws DataAccessException if an SQLException is caught
     */
    public void delete(String eventID) throws DataAccessException {}

    public void deleteUserEvents() throws DataAccessException {}

    /**
     * Clears all rows of the event table in the database
     * @throws DataAccessException if an SQLException is caught
     */
    @Override
    public void clear() throws DataAccessException {
        super.clear("event");
    }
}
