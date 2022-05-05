package com.project.autoshop.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class StatusRequest {
    private Boolean rejected;
    private Boolean approved;
    private Boolean ordered;
    private Boolean progress;
    private Boolean complete;
}
