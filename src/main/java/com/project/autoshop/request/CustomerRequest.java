package com.project.autoshop.request;

import com.project.autoshop.request.groups.Create;
import com.project.autoshop.request.groups.Update;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class CustomerRequest {
    @NotNull(message = "first cannot be null", groups = Create.class)
    @NotBlank(message = "first cannot be blank", groups = {Create.class, Update.class})
    public String first;
    @NotNull(message = "last cannot be null", groups = Create.class)
    @NotBlank(message = "last cannot be blank", groups = {Create.class, Update.class})
    public String last;
    @NotNull(message = "email cannot be null", groups = Create.class)
    @NotBlank(message = "email cannot be blank", groups = {Create.class, Update.class})
    @Email(groups = {Create.class, Update.class})
    public String email;
    @NotNull(message = "phone cannot be null", groups = Create.class)
    @Min(message = "invalid phone number", value = 1000000000l)
    @Min(message = "invalid phone number", value = 9999999999l)
    public Long phone;
    @NotNull(message = "job required")
    public Integer job;
}
