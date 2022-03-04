package service;

import org.junit.jupiter.api.BeforeEach;

public class ServiceTest {
    protected static ClearService clearService = new ClearService();

    public void setUp() {
        clearService.clear();
    }
}
