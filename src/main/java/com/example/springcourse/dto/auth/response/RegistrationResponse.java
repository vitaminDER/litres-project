package com.example.springcourse.dto.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponse {
    @JsonProperty("isRegistered")
    private boolean isRegistered = true;
}
