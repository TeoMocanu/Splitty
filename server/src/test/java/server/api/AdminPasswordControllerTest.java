package server.api;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdminPasswordControllerTest {

    private final AdminPasswordController adminPasswordController = new AdminPasswordController();

    @Test
    void correctLength() {
        int length = 8;
        String password = adminPasswordController.getAdminPassword(length);
        assertEquals(length, password.length(), "Password should have the specified length.");
    }

    @Test
    void nonEmpty() {
        int length = 10;
        String password = adminPasswordController.getAdminPassword(length);
        assertNotNull(password, "Password should not be null.");
        assertFalse(password.isEmpty(), "Password should not be empty.");
    }

}
