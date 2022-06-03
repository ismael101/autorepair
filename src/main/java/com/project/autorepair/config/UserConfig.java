package com.project.autorepair.config;

import com.project.autorepair.models.AppUser;
import com.project.autorepair.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

//config file for seeding username and password
@Configuration
@RequiredArgsConstructor
public class UserConfig implements CommandLineRunner {
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    //seeds database with username and password from env
    public void run(String... args) throws Exception {
        String username = System.getenv("USERNAME");
        String password = System.getenv("PASSWORD");
        String role = "ROLE_ADMIN";
        AppUser user = AppUser
                .builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();
        userRepository.save(user);
    }
}
