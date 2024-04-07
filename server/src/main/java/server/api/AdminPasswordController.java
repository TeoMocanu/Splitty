package server.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.Base64;

@RestController
@RequestMapping("/api/admin")
public class AdminPasswordController {
    @GetMapping("/generate-password")
    public String getAdminPassword(@RequestParam("length") int length) {
        if(length < 0)
        {
            throw new IllegalArgumentException("Length must be non-negative");
        }
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[24];
        random.nextBytes(bytes);
        String result = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        return result.substring(0, length);
    }
}