package com.project.autoshop.request;

import com.project.autoshop.request.groups.Create;
import com.project.autoshop.request.groups.Update;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@Builder
public class JobRequest {
    @NotNull(message = "description cannot be null", groups = Create.class)
    @NotBlank(message = "description cannot be blank" , groups = {Create.class, Update.class})
    private String description;
    @NotBlank(message = "complete cannot be null", groups = Create.class)
    private Boolean complete;
}
