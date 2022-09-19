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
    @NotNull(message = "task cannot be null", groups = Create.class)
    @NotBlank(message = "task cannot be blank", groups = {Create.class, Update.class})
    private String task;
    @NotNull(message = "location cannot be null", groups = Create.class)
    @NotBlank(message = "location cannot be blank", groups = {Create.class, Update.class})
    private String location;
    @NotNull(message = "cost cannot be null", groups = Create.class)
    @Min(value = 0, message = "cost is invalid", groups = {Create.class, Update.class})
    private Double cost;
    @NotNull(message = "complete cannot be null", groups = Create.class)
    @Pattern(regexp = "^true$|^false$", message = "allowed input: true or false", groups = {Create.class, Update.class})
    private String complete;
    @NotNull(message = "work cannot be null", groups = Create.class)
    @Pattern(message = "invalid uuid", regexp = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$", groups = {Create.class, Update.class})
    private String work;
}
