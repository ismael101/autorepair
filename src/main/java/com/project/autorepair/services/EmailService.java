package com.project.autorepair.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

//service for sending emails to customers
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    private Logger logger = LoggerFactory.getLogger(EmailService.class);

    //method for sending email
    @Async
    public void sendEmail(String email, String subject, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(body);
        javaMailSender.send(message);
        logger.info("email: " + email + " with message " + body + " sent");
    }
}
