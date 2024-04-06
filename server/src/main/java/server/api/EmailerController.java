package server.api;

import commons.emails.Invitation;
import commons.emails.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
                "You have been invited to join our '"+invitation.getEvent()+"' event. Please use the following code to join: " + invitation.getCode() +
                "\n\nBest wishes,\n" +
                "Splitty - group 04";

        emailerService.sendMail(invitation.getEmail(), subject, text);
    }

    @PutMapping("/sendReminder")
    public void sendMail(@RequestBody Reminder reminder){
        String subject = "Reminder to pay";
        String text = "Good evening, \n\n" +
                "You have to pay "+reminder.getAmount()+" to "+reminder.getDebtor()+" for the '"+reminder.getEvent()+"' event. Please pay as soon as possible. \n\n" +
                "Best wishes,\n" +
                "Splitty - group 04";

        emailerService.sendMail(reminder.getEmail(), subject, text);
    }
}
