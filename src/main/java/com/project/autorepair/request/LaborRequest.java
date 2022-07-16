package com.project.autorepair.request;

import com.project.autorepair.request.groups.Create;
import com.project.autorepair.request.groups.Update;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//object for mapping and validating labor requests for creating and updating
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LaborRequest {
    @NotNull(message = "task cannot be null", groups = Create.class)
    @NotBlank(message = "task cannot be blank", groups = {Create.class, Update.class})
    public String task;
    @NotNull(message = "location cannot be null", groups = Create.class)
    @NotBlank(message = "location cannot be blank", groups = {Create.class, Update.class})
    public String location;
    @NotNull(message = "cost cannot be null", groups = Create.class)
    @Min(message = "cost cannot be less then 0", value = 0, groups = {Create.class, Update.class})
    public Double cost;
    @NotNull(message = "notes cannot be null", groups = Create.class)
    public String notes;
    @NotNull(message = "job required", groups = Create.class)
    public Integer job;
}
