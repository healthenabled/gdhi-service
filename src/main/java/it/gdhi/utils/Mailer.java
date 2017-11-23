package it.gdhi.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class Mailer {

    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sourceEmailAddress;

    public Mailer(JavaMailSender sender) {
        this.javaMailSender = sender;
    }

    public void send(String destinationAddress, String subject, String body) {
        this.javaMailSender.send((MimeMessage newMessage) -> {
            newMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(destinationAddress));
            newMessage.setFrom(sourceEmailAddress);
            newMessage.setSubject(subject);
            newMessage.setText(body);
        });
    }
}
