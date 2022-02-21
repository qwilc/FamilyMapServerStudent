package dao;

import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest extends DAOTest {

    @Override
    protected void initializeInstanceVariables(Connection conn) {
        this.primaryKey = "username";
        this.model = new User(primaryKey, "password", "email", "first", "last", "f", "ID");
        this.dao = new UserDAO(conn);
    }
}
