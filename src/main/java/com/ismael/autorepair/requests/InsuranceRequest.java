package com.ismael.autorepair.requests;

import com.ismael.autorepair.requests.groups.Create;
import com.ismael.autorepair.requests.groups.Update;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InsuranceRequest {
    @NotNull(message = "provider cannot be null", groups = Create.class)
    @NotBlank(message = "provider cannot be blank", groups = {Create.class, Update.class})
    private String provider;
    @NotNull(message = "license cannot be null", groups = Create.class)
    @NotBlank(message = "license cannot be blank", groups = {Create.class, Update.class})
    private String license;
    @NotNull(message = "policy cannot be null", groups = Create.class)
    @NotBlank(message = "policy cannot be blank", groups = {Create.class, Update.class})
    private String policy;
    @NotNull(message = "vin cannot be null", groups = Create.class)
    @NotBlank(message = "vin cannot be blank", groups = {Create.class, Update.class})
    private String vin;
    @NotNull(message = "work cannot be null", groups = Create.class)
    @Pattern(message = "invalid uuid", regexp = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$", groups = {Create.class, Update.class})
    private String work;
}
