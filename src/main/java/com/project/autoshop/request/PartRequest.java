package com.project.autoshop.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class PartsRequest {
    @NotNull(message = "name cannot be null", groups = Create.class)
    @NotBlank(message = "name cannot be blank", groups = {Create.class, Update.class})
    private String name;
    @NotNull(message = "website cannot be null", groups = Create.class)
    @NotBlank(message = "website cannot be blank", groups = {Create.class, Update.class})
    @URL(message = "website is invalid", groups = {Create.class, Update.class})
    private String website;
    @NotNull(message = "price cannot be null", groups = Create.class)
    @Min(message = "price cannot be less then 0", value = 0, groups = {Create.class, Update.class})
    private Double price;
    @NotNull(message = "job_id cannot be null", groups = Create.class)
    private Integer job;
}
