package com.project.autoshop.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final AppUserDetailsService appUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            if(SecurityContextHolder.getContext().getAuthentication() != null){
                filterChain.doFilter(request, response);
            }else{
                String header = request.getHeader("Authorization");
                if(header == null || header.startsWith("Bearer ")){
                    Map<String, Object> error = new HashMap<>();
                    error.put("timestamp", LocalDateTime.now().toString());
                    error.put("status", 500);
                    error.put("path", request.getRequestURI().toString());
                    error.put("error", "Authorization header is not present or does not contain token");
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(response.getWriter(), error);
                }else{
                    String token = header.substring(7);
                    Algorithm algorithm = Algorithm.HMAC256(System.getenv("SECRET"));
                    JWTVerifier verifier = JWT.require(algorithm)
                            .build();
                    DecodedJWT jwt = verifier.verify(token);
                    UserDetails userDetails = appUserDetailsService.loadUserByUsername(jwt.getSubject());
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                }
            }

        }catch (JWTVerificationException j){
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now().toString());
            error.put("status", 500);
            error.put("path", request.getRequestURI().toString());
            error.put("error", j.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(), error);
        }
    }
}
