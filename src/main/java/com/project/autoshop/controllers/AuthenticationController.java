package com.project.autoshop.controllers;

import com.project.autoshop.request.AuthenticationRequest;
import com.project.autoshop.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(path = "/login")
    public ResponseEntity login(@RequestBody @Validated AuthenticationRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.login(request));
    }
}
