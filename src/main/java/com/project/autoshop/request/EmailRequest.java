package com.project.autoshop.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmailRequest {
    @NotNull(message = "to cannot be null")
    @NotBlank(message = "to cannot be blank")
    private String to;
    @NotNull(message = "body cannot be null")
    @NotBlank(message = "body cannot be blank")
    private String body;
    @NotNull(message = "subject cannot be null")
    @NotBlank(message = "subject cannot be blank")
    private String subject;

}

