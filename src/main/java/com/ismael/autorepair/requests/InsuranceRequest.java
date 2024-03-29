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
    @NotBlank(message = "provider cannot be blank", groups = Create.class)
    @Size(min = 1, max = 15, message = "provider has a min length of 1 and max length of 15", groups = {Create.class, Update.class})
    private String provider;
    @NotBlank(message = "policy cannot be blank", groups = Create.class)
    @Size(min = 8, max = 10, message = "policy has a min length of 8 and max length of 10", groups = {Create.class, Update.class})
    private String policy;
    @NotBlank(message = "vin cannot be blank", groups = Create.class)
    @Size(min = 17, max = 17, message = "vin has to have 17 characters", groups = {Create.class, Update.class})
    private String vin;
    @NotBlank(message = "work cannot be blank", groups = Create.class)
    @Pattern(message = "invalid uuid", regexp = "^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$", groups = {Create.class, Update.class})
    private String work;
}
