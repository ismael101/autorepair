package com.project.autoshop.request;

import com.project.autoshop.request.groups.Create;
import com.project.autoshop.request.groups.Update;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressRequest {
    @NotNull(message = "city cannot be null", groups = Create.class)
    @NotBlank(message = "city cannot be blank", groups = {Create.class, Update.class})
    public String city;
    @NotNull(message = "city cannot be null", groups = Create.class)
    @NotBlank(message = "city cannot be blank", groups = {Create.class, Update.class})
    public String state;
    @NotNull(message = "street cannot be null", groups = Create.class)
    @NotBlank(message = "street cannot be blank", groups = {Create.class, Update.class})
    public String street;
    @NotNull(message = "message cannot be null", groups = Create.class)
    @Min(message = "zipcode is invalid", value = 10000, groups = {Create.class, Update.class})
    @Max(message = "zipcode is invalid", value = 99999, groups = {Create.class, Update.class})
    public Integer zipcode;
    @NotNull(message = "job required", groups = Create.class)
    public Integer job;
}
