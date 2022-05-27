package com.project.autoshop.controllers;

import com.project.autoshop.request.AuthenticationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/auth")
public class AuthenticationController {

    @PostMapping
    public ResponseEntity login(@RequestBody @Validated AuthenticationRequest request){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body()
    }
}
