package com.project.autorepair.request;

import com.project.autorepair.request.groups.Create;
import com.project.autorepair.request.groups.Update;
import lombok.*;

import javax.validation.constraints.*;

//object for mapping and validating job requests for creating and updating
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobRequest {
    @NotNull(message = "description cannot be null", groups = Create.class)
    @NotBlank(message = "description cannot be blank" , groups = {Create.class, Update.class})
    private String description;
    @NotNull(message = "complete cannot be null", groups = Create.class)
    @Pattern(regexp = "^true$|^false$", message = "allowed input: true or false", groups = {Create.class, Update.class})
    private String complete;
}
