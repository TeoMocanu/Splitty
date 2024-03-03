package server.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.Base64;

@RestController
public class AdminPasswordController {

    private String generateSecurePassword() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[24]; // Adjust the size based on your security needs
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    @GetMapping("/api/admin/generate-password")
    public String getAdminPassword() {
        String adminPassword = generateSecurePassword();
        // Log the password for server-side visibility (use with caution)
        System.out.println("Generated Admin Password: " + adminPassword);
        // Return the password. Be mindful of security implications.
        return adminPassword;
    }
}