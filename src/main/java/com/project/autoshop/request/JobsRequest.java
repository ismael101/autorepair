package com.project.autoshop.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;


@AllArgsConstructor
@Getter
@Setter
@ToString
public class JobsRequest {
    @NotNull(message = "make cannot be null", groups = Create.class)
    @NotBlank(message = "make cannot be blank" , groups = {Create.class, Update.class})
    private String make;
    @NotNull(message = "model cannot be null", groups = Create.class)
    @NotBlank(message = "model cannot be blank", groups = {Create.class, Update.class})
    private String model;
    @Min(value = 1950, message = "year cannot be less then 1950", groups = {Create.class, Update.class})
    @Max(value = 2050, message = "year cannot be greater than 2050", groups = {Create.class, Update.class})
    @NotNull(message = "year cannot be null", groups = Create.class)
    private Integer year;
    @NotNull(message = "description cannot be null", groups = Create.class)
    @NotBlank(message = "description cannot be blank", groups = {Create.class, Update.class})
    private String description;
    @NotNull(message = "labor cannot be null", groups = Create.class)
    @Min(value = 1, message = "labor cannot be less than 1", groups = {Create.class, Update.class})
    private Double labor;
    @NotNull(message = "client_id cannot be null", groups = Create.class)
    private Integer client_id;
}
