package com.project.autoshop.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.project.autoshop.exceptions.AuthenticationErrorException;
import com.project.autoshop.exceptions.ServerErrorException;
import com.project.autoshop.request.AuthenticationRequest;
import com.project.autoshop.response.AuthenticationResponse;
import com.project.autoshop.security.AppUserDetails;
import com.project.autoshop.security.AppUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService appUserDetailsService;

    public AuthenticationResponse login(AuthenticationRequest request){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
            AppUserDetails userDetails = (AppUserDetails) appUserDetailsService.loadUserByUsername(request.getUsername());
            Algorithm algorithm = Algorithm.HMAC256(System.getenv("SECRET").getBytes(StandardCharsets.UTF_8));
            String token = JWT.create()
                    .withSubject(userDetails.getUsername())
                    .withClaim("authority", userDetails.getAuthorities().stream().toList().get(0).toString())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1000000))
                    .sign(algorithm);
            AuthenticationResponse response = AuthenticationResponse
                    .builder()
                    .token(token)
                    .timeStamp(LocalDateTime.now()).build();
            return response;
        }catch (BadCredentialsException b){
                throw new AuthenticationErrorException(b.getMessage());
        }catch(JWTCreationException j){
                throw new ServerErrorException(j.getMessage());
        }catch(Exception e){
                throw new ServerErrorException(e.getMessage());
        }
    }
}
