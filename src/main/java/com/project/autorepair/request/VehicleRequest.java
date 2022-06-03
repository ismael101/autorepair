package com.project.autorepair.request;

import com.project.autorepair.request.groups.Create;
import com.project.autorepair.request.groups.Update;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//object for mapping and validating vehicle requests for creating and updating
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequest {
    @NotNull(message = "make cannot be null", groups = Create.class)
    @NotBlank(message = "make cannot be blank", groups = {Create.class, Update.class})
    public String make;
    @NotNull(message = "model cannot be null", groups = Create.class)
    @NotBlank(message = "model cannot be blank", groups = {Create.class, Update.class})
    public String model;
    @NotNull(message = "year cannot be null", groups = Create.class)
    @Min(value = 1950, message = "year cannot be less then 1950", groups = {Create.class, Update.class} )
    @Max(value = 2050, message = "year cannot be more then 2050", groups = {Create.class, Update.class})
    public Integer year;
    @NotNull(message = "vin cannot be null", groups = Create.class)
    @NotBlank(message = "vin cannot be blank", groups = {Create.class, Update.class})
    public String vin;
    @NotNull(message = "job required", groups = Create.class)
    public Integer job;
}
