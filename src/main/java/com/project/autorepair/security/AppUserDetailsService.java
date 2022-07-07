package com.project.autorepair.security;

import com.project.autorepair.models.AppUser;
import com.project.autorepair.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//service for fetching user by username and then mapping it to userdetails for authentication
@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> appUser = appUserRepository.findByUsername(username);
        if(appUser.isEmpty()){
            throw new RuntimeException("user with username: " + username + " not found");
        }
        UserDetails userDetails = new AppUserDetails(appUser.get());
        return userDetails;
    }
}
