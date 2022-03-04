package service;

import org.junit.jupiter.api.BeforeEach;

public class ServiceTest { //TODO: These tests mess with what is supposed to be the actual database, right? Would not be good IRL...
    protected static ClearService clearService = new ClearService();

    public void setUp() {
        clearService.clear();
    }
}
