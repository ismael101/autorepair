package com.project.autoshop.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {
    @NotNull(message = "username cannot be null")
    @NotBlank(message = "username cannot be blank")
    private String username;
    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password cannot be blank")
    private String password;
}
