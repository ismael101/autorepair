package com.project.autoshop.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CustomerRequest {
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
    @NotNull(message = "phone cannot be null", groups = Create.class)
    @Min(message = "invalid phone number", value = 1000000000)
    @Min(message = "invalid phone number", value = 9999999999l)
    private Long phone;
    @NotNull(message = "job required")
    private Integer job;
}
