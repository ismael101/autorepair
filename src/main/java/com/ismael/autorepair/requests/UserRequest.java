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
public class UserRequest {
    @NotBlank(message = "username cannot be blank", groups = Create.class)
    @Size(min = 8, max = 20, message = "username has a min length of 8 and max length of 20 characters", groups = {Create.class, Update.class})
    private String username;
    @NotBlank(message = "password cannot be blank", groups = Create.class)
    @Size(min = 8, max = 20, message = "password has a min length of 8 and max length of 20 characters", groups = {Create.class, Update.class})
    private String password;
}
