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
    @NotNull(message = "policy cannot be null", groups = Create.class)
    @Min(value = 1000000000l ,message = "policy number is invalid", groups = {Create.class, Update.class})
    @Max(value = 9999999999l ,message = "policy number is invalid", groups = {Create.class, Update.class})
    private Long policy;
    @NotNull(message = "vin cannot be null", groups = Create.class)
    @NotBlank(message = "vin cannot be blank", groups = {Create.class, Update.class})
    private String vin;
    @NotNull(message = "work cannot be null", groups = Create.class)
    @Pattern(message = "invalid uuid", regexp = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$", groups = {Create.class, Update.class})
    private String work;
}
