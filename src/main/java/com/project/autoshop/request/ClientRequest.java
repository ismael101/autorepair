package com.project.autoshop.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClientRequest {
    @NotNull(message = "first cannot be null")
    @NotBlank(message = "last cannot be blank")
    private String first;
    @NotNull(message = "last cannot be null")
    @NotBlank(message = "last cannot be blank")
    private String last;
    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email cannot be blank")
    @Email
    private String email;
}
