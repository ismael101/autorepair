package com.project.autoshop.request;

import com.project.autoshop.request.groups.Create;
import com.project.autoshop.request.groups.Update;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LaborRequest {
    @NotNull(message = "task cannot be null", groups = Create.class)
    @NotBlank(message = "task cannot be blank", groups = {Create.class, Update.class})
    public String task;
    @NotNull(message = "location cannot be null", groups = Create.class)
    @NotBlank(message = "location cannot be blank", groups = {Create.class, Update.class})
    public String location;
    @NotNull(message = "description cannot be null", groups = Create.class)
    @NotBlank(message = "description cannot be blank", groups = {Create.class, Update.class})
    public String description;
    @NotNull(message = "cost cannot be null", groups = Create.class)
    @Min(message = "cost cannot be less then 0", value = 0, groups = {Create.class, Update.class})
    public Double cost;
    @NotNull(message = "notes cannot be null", groups = Create.class)
    @NotBlank(message = "notes cannot be blank", groups = {Create.class, Update.class})
    public String notes;
    @NotNull(message = "job cannot be null", groups = Create.class)
    public Integer job;
}
