package com.project.autorepair.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


//filter for verifying jwt token on secured endpoints
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final AppUserDetailsService appUserDetailsService;
    private Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String header = request.getHeader("Authorization");
            //checks if header is null or doesn't contain token then passes to other filter
            if(header == null || !header.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
            }
            //if header contains token it verifies it authenticity
            String token = header.substring(7);
            Algorithm algorithm = Algorithm.HMAC256(System.getenv("SECRET"));
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            UserDetails userDetails = appUserDetailsService.loadUserByUsername(jwt.getSubject());
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            //sets user for context of the requests
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("authentication context set for " + authentication.getName());
            //throws error message for various issues regarding the token
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
            logger.error("jwt verification exception caused by " + j.getMessage());
        }
        filterChain.doFilter(request,response);
    }
}
