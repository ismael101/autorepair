package com.project.autoshop.request;

import com.project.autoshop.request.groups.Create;
import com.project.autoshop.request.groups.Update;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class InsuranceRequest {
    @NotNull(message = "policy cannot be null", groups = Create.class)
    @NotBlank(message = "policy cannot be blank", groups = {Create.class, Update.class})
    public String policy;
    @NotNull(message = "policy cannot be null", groups = Create.class)
    @NotBlank(message = "policy cannot be blank", groups = {Create.class, Update.class})
    public String provider;
    @NotNull(message = "policy cannot be null", groups = Create.class)
    @NotBlank(message = "policy cannot be blank", groups = {Create.class, Update.class})
    public String vin;
    @NotNull(message = "policy cannot be null", groups = Create.class)
    public Integer job;
}
