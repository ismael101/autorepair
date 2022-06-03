package com.project.autorepair.request;

import com.project.autorepair.request.groups.Create;
import com.project.autorepair.request.groups.Update;
import lombok.*;

import javax.validation.constraints.*;

//object for mapping and validating customer requests for creating and updating
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    @NotNull(message = "first cannot be null", groups = Create.class)
    @NotBlank(message = "first cannot be blank", groups = {Create.class, Update.class})
    public String first;
    @NotNull(message = "last cannot be null", groups = Create.class)
    @NotBlank(message = "last cannot be blank", groups = {Create.class, Update.class})
    public String last;
    @NotNull(message = "email cannot be null", groups = Create.class)
    @NotBlank(message = "email cannot be blank", groups = {Create.class, Update.class})
    @Email(groups = {Create.class, Update.class}, message = "email is invalid")
    public String email;
    @NotNull(message = "phone cannot be null", groups = Create.class)
    @NotBlank(message = "phone cannot be blank", groups = {Create.class, Update.class})
    public String phone;
    @NotNull(message = "job required", groups = Create.class)
    public Integer job;
}
