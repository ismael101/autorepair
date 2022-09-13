package com.ismael.autorepair.security.details;

import com.ismael.autorepair.models.User;
import com.ismael.autorepair.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username){
        User user = this.userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with "
                        + "user name "+ username + " not found"));
        UserDetails userDetails = new CustomUserDetails(user);
        return userDetails;
    }
}
