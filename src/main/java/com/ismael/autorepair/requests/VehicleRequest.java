package com.ismael.autorepair.requests;

import com.ismael.autorepair.requests.groups.Create;
import com.ismael.autorepair.requests.groups.Update;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VehicleRequest {
    @NotNull(message = "make cannot be null", groups = Create.class)
    @NotBlank(message = "make cannot be blank", groups = {Create.class, Update.class})
    private String make;
    @NotNull(message = "model cannot be null", groups = Create.class)
    @NotBlank(message = "model cannot be blank", groups = {Create.class, Update.class})
    private String model;
    @NotNull(message = "year cannot be null", groups = Create.class)
    @Min(value = 1950, message = "year is invalid", groups = {Create.class, Update.class})
    @Max(value = 2050, message = "year is invalid", groups = {Create.class, Update.class})
    private Integer year;
    @NotNull(message = "work cannot be null", groups = Create.class)
    @Pattern(message = "invalid uuid", regexp = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$", groups = {Create.class, Update.class})
    private String work;
}
