package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailerService {
    @Autowired
    public JavaMailSender emailSender;

    public void sendTestMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("group4.oopp@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        System.out.println("Sending mail to: " + to);
        emailSender.send(message);
        System.out.println("Mail sent to: " + to);
    }

}
