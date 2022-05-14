package com.project.autoshop.request;

import lombok.*;

import javax.validation.constraints.*;

@Data
public class JobsRequest {
    @NotNull(message = "description cannot be null", groups = Create.class)
    @NotBlank(message = "description cannot be blank" , groups = {Create.class, Update.class})
    private String description;
    @NotNull(message = "labor cannot be null", groups = Create.class)
    @Min(value = 0, groups = {Create.class, Update.class}, message = "labor cannot be less then 0")
    private Double labor;
}
