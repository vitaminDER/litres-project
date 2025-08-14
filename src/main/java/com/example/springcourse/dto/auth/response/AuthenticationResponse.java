package com.example.springcourse.dto.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {

    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String userName;

    private String login;

    private String role;

    private String token;

    @JsonProperty("isAuth")
    private boolean isAuth;
}
