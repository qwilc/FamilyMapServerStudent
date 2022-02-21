package dao;

import model.AuthToken;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDAOTest extends DAOTest {
    @Override
    protected void initializeInstanceVariables(Connection conn) {
        this.primaryKey = "ID";
        this.model = new Person(primaryKey, "username", "firstName", "lastName", "f", "father", "mother", "spouse");
        this.dao = new PersonDAO(conn);
    }
}
