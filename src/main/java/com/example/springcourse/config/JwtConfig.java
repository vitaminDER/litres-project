package com.example.springcourse.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("jwt")
public class JwtConfig {
    private long accessTokenExpiration; // e.g. 900000 (15 мин)
    private long refreshTokenExpiration; // e.g. 604800000 (7 дней)
}
