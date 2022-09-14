package com.ismael.autorepair.services;

import com.ismael.autorepair.exceptions.AlreadyExists;
import com.ismael.autorepair.models.User;
import com.ismael.autorepair.repositories.UserRepository;
import com.ismael.autorepair.requests.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Map;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    UserService underTest;

    @BeforeEach
    void setUp(){
        underTest = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void createUserMethodTests(){

        //section for testing successful user creation
        when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("hashed password");
        UserRequest request = new UserRequest("username", "password");
        Map<String, Object> response = underTest.signUp(request);
        verify(userRepository).findUserByUsername("username");
        verify(userRepository).save(any());
        assertEquals(response.get("message"), "new user created");
        assertEquals(response.get("path"), "/api/v1/auth/signup");
        assertEquals(response.get("status"), 201);

        //section for testing duplicate usernames
        when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.of(new User("username", "password")));
        Exception exception = assertThrows(AlreadyExists.class, () -> {
            underTest.signUp(request);
        });
        assertEquals(exception.getMessage(), "user with username: username already exists");


    }
}