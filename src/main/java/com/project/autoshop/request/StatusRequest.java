package com.project.autoshop.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class StatusRequest {
    @NotBlank(message = "rejected cannot be blank", groups = Update.class)
    private Boolean rejected;
    @NotBlank(message = "approved cannot be blank", groups = Update.class)
    private Boolean approved;
    @NotBlank(message = "ordered cannot be blank", groups = Update.class)
    private Boolean ordered;
    @NotBlank(message = "progress cannot be blank", groups = Update.class)
    private Boolean progress;
    @NotBlank(message = "complete cannot be blank", groups = Update.class)
    private Boolean complete;
}
