package com.example.springcourse.dto.auth.request;
import lombok.Data;

@Data
public class RefreshRequest {
    private String refreshToken;
}
