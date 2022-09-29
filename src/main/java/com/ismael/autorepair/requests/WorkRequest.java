package com.ismael.autorepair.requests;

import com.ismael.autorepair.requests.groups.Create;
import com.ismael.autorepair.requests.groups.Update;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkRequest {
    @NotBlank(message = "title cannot be blank", groups = Create.class)
    @Size(min = 1, max = 20, message = "title has a min length of 1 and max length of 20 characters", groups = {Create.class, Update.class})
    private String title;
    @NotBlank(message = "description cannot be blank", groups = Create.class)
    @Size(min = 10, max = 100, message = "description has a min length of 10 and max length of 100 characters", groups = {Create.class, Update.class})
    private String description;
    @NotBlank(message = "complete cannot be blank", groups = Create.class)
    @Pattern(regexp = "^true$|^false$", message = "allowed input: true or false", groups = {Create.class, Update.class})
    private String complete;
}
