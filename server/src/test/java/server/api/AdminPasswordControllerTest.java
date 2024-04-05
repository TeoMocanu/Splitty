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

    @Test
    void zeroLength() {
        int length = 0;
        String password = adminPasswordController.getAdminPassword(length);
        assertEquals(0, password.length(), "Password with length 0 should be empty.");
    }

    @Test
    void maxLength() {
        //Check for maximum length based on the byte array used in encoding
        int maxLength = 32; //Maximum length for base64 encoded string from 24 byte array
        String password = adminPasswordController.getAdminPassword(maxLength);
        assertEquals(maxLength, password.length(), "Password should have the maximum possible length.");
    }
}
