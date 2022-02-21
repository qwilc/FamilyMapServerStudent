package dao;

import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class EventDAOTest extends DAOTest { //TODO: Apparently decimal latitude/longitude don't pass some of the tests?
    Event testEvent = new Event(primaryKey, "username", "personID", 1, 2, "country",
            "city", "type", 1900);

    @Override
    protected void initializeInstanceVariables(Connection conn) {
        this.primaryKey = "ID";
        this.model = testEvent;
        this.dao = new EventDAO(conn);
    }
}
