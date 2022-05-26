package com.project.autoshop.request;

import com.project.autoshop.request.groups.Create;
import com.project.autoshop.request.groups.Update;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceRequest {
    @NotNull(message = "policy cannot be null", groups = Create.class)
    @NotBlank(message = "policy cannot be blank", groups = {Create.class, Update.class})
    public String policy;
    @NotNull(message = "provider cannot be null", groups = Create.class)
    @NotBlank(message = "provider cannot be blank", groups = {Create.class, Update.class})
    public String provider;
    @NotNull(message = "vin cannot be null", groups = Create.class)
    @NotBlank(message = "vin cannot be blank", groups = {Create.class, Update.class})
    public String vin;
    @NotNull(message = "job cannot be null", groups = Create.class)
    public Integer job;
}
