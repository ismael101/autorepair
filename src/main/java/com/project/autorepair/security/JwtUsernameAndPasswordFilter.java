package com.project.autorepair.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.autorepair.request.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


//filter for allowing users login and get a token
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class JwtUsernameAndPasswordFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private Logger logger = LoggerFactory.getLogger(JwtUsernameAndPasswordFilter.class);

    //method for user authentication
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthenticationRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), AuthenticationRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );

            Authentication authenticate = authenticationManager.authenticate(authentication);
            logger.info("user " + authenticationRequest.getUsername() + " is authenticated");
            return authenticate;

        } catch (IOException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now().toString());
            error.put("status", 400);
            error.put("path", request.getRequestURI().toString());
            error.put("error", e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(), error);
            logger.error("io exception caused by " + e.getMessage());
            return null;
        }
    }

    //method that returns token when authentication is successful
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //generates token with secret
        Algorithm algorithm = Algorithm.HMAC256(System.getenv("SECRET").getBytes(StandardCharsets.UTF_8));
        String token = JWT.create()
                .withSubject(authResult.getName())
                .withClaim("authority", authResult.getAuthorities().stream().toList().get(0).toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000000000l))
                .sign(algorithm);
        Map<String, Object> message = new HashMap<>();
        message.put("token", token);
        message.put("timestamp", LocalDateTime.now().toString());
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), message);
        logger.info("jwt token created");
    }

    //method that returns error message when authentication is unsuccessful
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", 400);
        error.put("path", request.getRequestURI().toString());
        error.put("error", failed.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), error);
        logger.error("authentication error caused by " + failed.getMessage());
    }
}
