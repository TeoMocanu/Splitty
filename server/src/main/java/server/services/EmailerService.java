package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Properties;

@Service
public class EmailerService {
    @Autowired
    public JavaMailSender javaMailSender;

    public String getSender() {
        Properties properties = new Properties();
        InputStream input = EmailerService.class.getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String from = properties.getProperty("spring.mail.username");
        return from;
    }

    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        String from = getSender();
        message.setFrom(from);
        message.setCc(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

//        JavaMailSender ja

        System.out.println("Sending mail to: " + to);
        javaMailSender.send(message);
        System.out.println("Mail sent to: " + to);
    }
}
