package com.example.springcourse.entity.jwtkey;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Encoders;

import javax.crypto.SecretKey;

public class JwtKeyGenerator {
    public static void main(String[] args) {
        // Генерация ключа в двух форматах
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // Base64 (стандартный)
        String base64Key = Encoders.BASE64.encode(key.getEncoded());

        // Base64URL (для JWT)
        String base64UrlKey = Encoders.BASE64URL.encode(key.getEncoded());

        System.out.println("=== Сгенерированные JWT ключи ===");
        System.out.println("Base64 (стандартный):\n" + base64Key);
        System.out.println("\nBase64URL (для JWT):\n" + base64UrlKey);
        System.out.println("\nДлина ключа: " + key.getEncoded().length * 8 + " бит");

        // Пример для application.properties
        System.out.println("\nПример для application.properties:");
        System.out.println("jwt.secret=" + base64UrlKey);
    }
}
