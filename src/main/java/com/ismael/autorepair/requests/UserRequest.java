package com.ismael.autorepair.requests;

import com.ismael.autorepair.requests.groups.Create;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequest {
    @NotNull(message = "username cannot be null", groups = Create.class)
    @NotBlank(message = "username cannot be blank", groups = Create.class)
    private String username;
    @NotNull(message = "password cannot be null", groups = Create.class)
    @NotBlank(message = "password cannot be blank", groups = Create.class)
    private String password;
}
