package server.api;

import commons.emails.Invitation;
import commons.emails.MockEmail;
import commons.emails.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.services.EmailerService;

@RestController
@RequestMapping("/api/mail")
public class EmailerController {
    private EmailerService emailerService;

    @Autowired
    public EmailerController(EmailerService emailerService) {
        this.emailerService = emailerService;
    }
    @PutMapping("/sendInvitation")
    public void sendMail(@RequestBody Invitation invitation){
        String subject = "Invitation to join event";
        String text = "Good evening, \n\n" +
                "You have been invited to join our '" + invitation.getEvent() +
                "' event. Please use the following code to join: " + invitation.getCode() +
                "\n\nBest wishes,\n" +
                "Splitty - group 04";

        emailerService.sendMail(invitation.getEmail(), subject, text);
    }

    @PutMapping("/sendReminder")
    public void sendMail(@RequestBody Reminder reminder){
        String subject = "Reminder to pay";
        String text = "Good evening, \n\n" +
                "You have to pay " + reminder.getAmount() + " to " + reminder.getDebtor() +
                " for the '" + reminder.getEvent() + "' event. Please pay as soon as possible. \n\n" +
                "Best wishes,\n" +
                "Splitty - group 04";

        emailerService.sendMail(reminder.getEmail(), subject, text);
    }

    @PutMapping("/sendTest")
    public void sendTestMail(@RequestBody MockEmail mail){
        String to = emailerService.getSender();
        String subject = "Test email";
        String text = "Good evening, \n\n" +
                "This is a test email, from the "+mail.getEvent()+" event! If you received this, it means your email service is correctly configured.\n\n" +
                "Best wishes,\n" +
                "Splitty - group 04";

        emailerService.sendMail(to, subject, text);
    }

    @GetMapping("/configured")
    public boolean isConfigured(){
        return !emailerService.getSender().isBlank();
    }
}
