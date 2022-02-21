package dao;

import model.AuthToken;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthTokenDAOTest extends DAOTest {
    @Override
    protected void initializeInstanceVariables(Connection conn) {
        this.primaryKey = "token";
        this.model = new AuthToken(primaryKey, "username");
        this.dao = new AuthTokenDAO(conn);
    }
}
