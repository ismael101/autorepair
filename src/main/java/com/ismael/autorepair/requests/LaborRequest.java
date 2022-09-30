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
public class LaborRequest {
    @NotBlank(message = "task cannot be blank", groups = Create.class)
    @Size(min = 1, max = 30, message = "task has a min length of 1 and max length of 20 characters", groups = {Create.class, Update.class})
    private String task;
    @NotBlank(message = "location cannot be blank", groups = Create.class)
    @Size(min = 1, max = 20, message = "location has a min length of 1 and max length of 20 characters", groups = {Create.class, Update.class})
    private String location;
    @NotNull(message = "cost cannot be null", groups = Create.class)
    @Min(value = 0, message = "cost is invalid", groups = {Create.class, Update.class})
    private Double cost;
    @NotBlank(message = "complete cannot be blank", groups = Create.class)
    @Pattern(regexp = "^true$|^false$", message = "allowed input: true or false", groups = {Create.class, Update.class})
    private String complete;
    @NotBlank(message = "work cannot be blank", groups = Create.class)
    @Pattern(message = "invalid uuid", regexp = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$", groups = {Create.class, Update.class})
    private String work;
}
