package com.ismael.autorepair.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ismael.autorepair.security.details.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenFilter extends OncePerRequestFilter {
    private CustomUserDetailsService customUserDetailsService;

    public JwtTokenFilter(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String header = request.getHeader("Authorization");
            if(header != null && header.startsWith("Bearer ")){
                String token = header.substring(7);
                Algorithm algorithm = Algorithm.HMAC256(System.getenv("SECRET"));
                JWTVerifier verifier = JWT.require(algorithm)
                        .build();
                DecodedJWT jwt = verifier.verify(token);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwt.getSubject());
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request,response);
                return;
            }
            filterChain.doFilter(request,response);
        }catch (JWTVerificationException j){
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now().toString());
            error.put("status", 401);
            error.put("path", request.getRequestURI().toString());
            error.put("error", j.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(), error);
            filterChain.doFilter(request,response);
        }catch (UsernameNotFoundException u){
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now().toString());
            error.put("status", 401);
            error.put("path", request.getRequestURI().toString());
            error.put("error", u.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(), error);
            filterChain.doFilter(request,response);
        }
    }
}
