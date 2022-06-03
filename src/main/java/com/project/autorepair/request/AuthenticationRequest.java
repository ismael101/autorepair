package com.project.autorepair.request;

import lombok.*;

//object for mapping authentication requests for creating and updating
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    public String username;
    public String password;
}
