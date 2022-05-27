package com.project.autoshop.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    @NotNull(message = "username cannot be null")
    @NotBlank(message = "username cannot be blank")
    public String username;
    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password cannot be blank")
    public String password;
}
