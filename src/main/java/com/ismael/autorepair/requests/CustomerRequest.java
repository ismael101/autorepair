package com.ismael.autorepair.requests;

import com.ismael.autorepair.requests.groups.Create;
import com.ismael.autorepair.requests.groups.Update;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerRequest {
    @NotBlank(message = "first cannot be blank", groups = Create.class)
    @Size(min = 1, max = 20, message = "first has a min length of 1 and max length of 20 characters", groups = {Create.class, Update.class})
    private String first;
    @NotBlank(message = "last cannot be blank", groups = Create.class)
    @Size(min = 1, max = 20, message = "last has a min length of 1 and max length of 20 characters", groups = {Create.class, Update.class})
    private String last;
    @NotBlank(message = "email cannot be blank", groups = Create.class)
    @Email(message = "email is invalid", groups = {Create.class, Update.class})
    private String email;
    @NotNull(message = "phone cannot be null", groups = Create.class)
    @Min(value = 1000000000l ,message = "phone number is invalid", groups = {Create.class, Update.class})
    @Max(value = 9999999999l ,message = "phone number is invalid", groups = {Create.class, Update.class})
    private Long phone;
    @NotBlank(message = "work cannot be blank", groups = Create.class)
    @Pattern(message = "invalid uuid", regexp = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$", groups = {Create.class, Update.class})
    private String work;
}
