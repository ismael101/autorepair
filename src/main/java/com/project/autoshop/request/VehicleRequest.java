package com.project.autoshop.request;

import com.project.autoshop.request.groups.Create;
import com.project.autoshop.request.groups.Update;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class VehicleRequest {
    @NotNull(message = "make cannot be null", groups = Create.class)
    @NotBlank(message = "make cannot be blank", groups = {Create.class, Update.class})
    public String make;
    @NotNull(message = "make cannot be null", groups = Create.class)
    @NotBlank(message = "make cannot be blank")
    public String model;
    @NotNull(message = "make cannot be null", groups = Create.class)
    @Min(value = 1950, message = "year cannot be less then 1950", groups = {Create.class, Update.class} )
    @Max(value = 2050, message = "year cannot be more then 2050", groups = {Create.class, Update.class})
    public Integer year;
    @NotNull(message = "customer cannot be null", groups = Create.class)
    public Integer job;
}
