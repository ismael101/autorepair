package com.project.autoshop.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.project.autoshop.exceptions.AuthenticationErrorException;
import com.project.autoshop.repositories.UserRepository;
import com.project.autoshop.request.AuthenticationRequest;
import com.project.autoshop.response.AuthenticationResponse;
import com.project.autoshop.security.AppUserDetail;
import com.project.autoshop.security.AppUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailService appUserDetailService;

    public AuthenticationResponse login(AuthenticationRequest request){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
            AppUserDetail userDetails = (AppUserDetail) appUserDetailService.loadUserByUsername(request.getUsername());
            Algorithm algorithm = Algorithm.HMAC512(System.getenv("SECRET").getBytes());
            String token = JWT.create()
                    .withSubject(userDetails.getUsername())
                    .withClaim("authority", userDetails.getAuthorities().stream().toList().get(0).toString())
                    .withExpiresAt(new Date(System.currentTimeMillis() + Integer.parseInt(System.getenv("EXPIRE"))))
                    .sign(algorithm);
            AuthenticationResponse response = AuthenticationResponse.builder().token(token).build();
            return response;

        }catch(BadCredentialsException b){
            throw new AuthenticationErrorException(b.getMessage());

        }catch(JWTCreationException j){
            throw new RuntimeException(j.getMessage());
        }
    }
}
