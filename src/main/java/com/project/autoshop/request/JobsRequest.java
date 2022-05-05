package com.project.autoshop.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@AllArgsConstructor
@Getter
@Setter
public class JobsRequest {
    @NotNull(message = "make cannot be null")
    @NotBlank(message = "make cannot be blank")
    private String make;
    @NotNull(message = "model cannot be null")
    @NotBlank(message = "model cannot be blank")
    private String model;
    @Min(value = 1950, message = "year cannot be less then 1950")
    @Max(value = 2050, message = "year cannot be greater than 2050")
    @NotNull(message = "year cannot be null")
    private Integer year;
    @NotNull(message = "description cannot be null")
    @NotBlank(message = "description cannot be blank")
    private String description;
    @NotNull(message = "labor cannot be null")
    @Min(value = 1, message = "labor cannot be less than 1")
    private Double labor;
    @NotNull(message = "client_id cannot be null")
    private Integer client_id;
}
