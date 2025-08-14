package com.example.springcourse.dto.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Map;

public class JsonAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/auth/signin", "POST"));
    }
    /*
     *
     * ,F,,F*/
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {

        try {
            // 1. Парсим JSON
            Map<String, String> authData = objectMapper.readValue(
                    request.getInputStream(),
                    new TypeReference<Map<String, String>>() {}
            );

            // 2. Извлекаем логин и пароль
            String username = authData.get("login");
            String password = authData.get("password");

            if (username == null || password == null) {
                throw new AuthenticationServiceException("Login and password must be provided");
            }

            // 3. Создаём токен аутентификации
            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(username, password);

            // 4. Аутентифицируем (здесь используется AuthenticationManager)
            return this.getAuthenticationManager().authenticate(authRequest);

        } catch (IOException e) {
            throw new AuthenticationServiceException("Authentication failed", e);
        }
    }
}
