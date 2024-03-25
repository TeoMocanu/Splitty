package server.api;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.services.EmailerService;

@RestController
@RequestMapping("/api/mail")
public class EmailerController {
    @PutMapping("/sendMail")
    public void sendMail(@RequestBody String to){
        System.out.println("Sending mail to: " + to);
        EmailerService emailerService = new EmailerService();
        emailerService.sendTestMail(to, "Test", "This is a test mail");
    }
}
