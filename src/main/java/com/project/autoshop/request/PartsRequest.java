package com.project.autoshop.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
public class PartsRequest {
    @NotNull(message = "name cannot be null")
    @NotBlank(message = "name cannot be blank")
    private String name;
    @NotNull(message = "website cannot be null")
    @NotBlank(message = "website cannot be blank")
    @URL
    private String website;
    @NotNull(message = "price cannot be null")
    @NotBlank(message = "price cannot be blank")
    private Double price;
    @NotNull(message = "job_id cannot be null")
    private Integer job_id;
}
