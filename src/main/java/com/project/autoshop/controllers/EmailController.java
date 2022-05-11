package com.project.autoshop.controllers;

import com.project.autoshop.request.EmailRequest;
import com.project.autoshop.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.mail.MessagingException;

@RestController
@RequestMapping(path = "api/v1/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    //method for sending custom email
    @PostMapping
    public ResponseEntity sendEmail(@RequestBody @Validated EmailRequest email) throws MessagingException {
        this.emailService.sendEmail(email.getTo(), email.getSubject(), email.getBody());
        return ResponseEntity.status(HttpStatus.OK).body("email successfully sent");
    }
}
