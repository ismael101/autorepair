package com.project.autorepair.security;

import com.project.autorepair.models.AppUser;
import com.project.autorepair.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//service for fetching user by username and then mapping it to userdetails for authentication
@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username).get();
        UserDetails userDetails = new AppUserDetails(appUser);
        return userDetails;
    }
}
