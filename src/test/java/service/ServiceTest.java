package service;

import org.junit.jupiter.api.BeforeEach;

public class ServiceTest { //TODO: These tests mess with what is supposed to be the actual database, right? Would not be good IRL...
    protected ClearService clearService = new ClearService();

    @BeforeEach
    public void setUp() {
        clearService.clear();
    }
}
