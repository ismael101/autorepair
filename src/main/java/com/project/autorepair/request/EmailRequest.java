package com.project.autorepair.request;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//object for mapping and validating email requests for creating and updating
@Getter
@Setter
public class EmailRequest {
    @NotNull(message = "to cannot be null")
    @NotBlank(message = "to cannot be blank")
    private String to;
    @NotNull(message = "body cannot be null")
    @NotBlank(message = "body cannot be blank")
    private String body;
    @NotNull(message = "subject cannot be null")
    @NotBlank(message = "subject cannot be blank")
    private String subject;

}

