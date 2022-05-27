package com.project.autoshop.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class AuthenticationResponse {
    public String token;
    public LocalDateTime localDateTime = LocalDateTime.now();
}
