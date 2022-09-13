package com.ismael.autorepair.services;

import com.ismael.autorepair.exceptions.AlreadyExists;
import com.ismael.autorepair.models.User;
import com.ismael.autorepair.repositories.UserRepository;
import com.ismael.autorepair.requests.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

//service for managing users
@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //method for creating a new user
    public Map<String, Object> signUp(UserRequest request){
        Optional<User> user = userRepository.findUserByUsername(request.getUsername());
        if(user.isPresent()){
            logger.error("User Already Exists Thrown For: " + request.getUsername() );
            throw new AlreadyExists("user with username: " + user.get().getUsername() + " already exists");
        }
        User newUser = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()));
        userRepository.save(newUser);
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", 201);
        body.put("path", "/api/v1/auth/signup");
        body.put("message", "new user created");
        logger.info(request.getUsername() + " Created");
        return body;
    }

}
