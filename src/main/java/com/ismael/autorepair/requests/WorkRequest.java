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
    @NotNull(message = "title cannot be null", groups = Create.class)
    @NotBlank(message = "title cannot be blank", groups = {Create.class, Update.class})
    private String title;
    @NotNull(message = "description cannot be null", groups = Create.class)
    @NotBlank(message = "description cannot be blank", groups = {Create.class, Update.class})
    private String description;
}
