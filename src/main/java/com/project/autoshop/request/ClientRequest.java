package com.project.autoshop.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ClientRequest {
    @NotNull(message = "first cannot be null", groups = Create.class)
    @NotBlank(message = "first cannot be blank", groups = {Create.class, Update.class})
    private String first;
    @NotNull(message = "last cannot be null", groups = Create.class)
    @NotBlank(message = "last cannot be blank", groups = {Create.class, Update.class})
    private String last;
    @NotNull(message = "email cannot be null", groups = Create.class)
    @NotBlank(message = "email cannot be blank", groups = {Create.class, Update.class})
    @Email(groups = {Create.class, Update.class})
    private String email;
}
