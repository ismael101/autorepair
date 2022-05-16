package com.project.autoshop.request;

import com.project.autoshop.request.groups.Create;
import com.project.autoshop.request.groups.Update;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PartRequest {
    @NotNull(message = "name cannot be null", groups = Create.class)
    @NotBlank(message = "name cannot be blank", groups = {Create.class, Update.class})
    public String name;
    @NotNull(message = "location cannot be null", groups = Create.class)
    @NotBlank(message = "location cannot be blank", groups = {Create.class, Update.class})
    public String location;
    @NotNull(message = "description cannot be null", groups = Create.class)
    @NotBlank(message = "description cannot be blank", groups = {Create.class, Update.class})
    public String description;
    @NotNull(message = "website cannot be null", groups = Create.class)
    @NotBlank(message = "website cannot be blank", groups = {Create.class, Update.class})
    @URL(message = "website is not valid")
    public String website;
    @NotNull(message = "cost cannot be null", groups = Create.class)
    @NotBlank(message = "cost cannot be blank", groups = {Create.class, Update.class})
    @Min(message = "cost cannot be less then 0", value = 0, groups = {Create.class, Update.class})
    public Double cost;
    @NotNull(message = "ordered cannot be null", groups = Create.class)
    public Boolean ordered;
    @NotNull(message = "notes cannot be null", groups = Create.class)
    public String notes;
    @NotNull(message = "job cannot be null", groups = Create.class)
    public Integer job;
}
