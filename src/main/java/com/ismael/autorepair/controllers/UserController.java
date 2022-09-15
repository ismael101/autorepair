package com.ismael.autorepair.controllers;

import com.ismael.autorepair.requests.UserRequest;
import com.ismael.autorepair.requests.groups.Create;
import com.ismael.autorepair.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//controller for handling authentication
@RestController
@RequestMapping(path = "/api/v1/auth")
public class UserController {
    private UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //controller method for creating a new user
    @PostMapping(path = "/signup")
    public ResponseEntity signUp(@RequestBody @Validated(Create.class) UserRequest request) {
        logger.info("Signup Method Accessed By: " + request.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(request));
    }
}
