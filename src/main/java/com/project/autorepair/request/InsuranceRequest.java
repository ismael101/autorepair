package com.project.autorepair.request;

import com.project.autorepair.request.groups.Create;
import com.project.autorepair.request.groups.Update;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//object for mapping and validating insurance requests for creating and updating
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
