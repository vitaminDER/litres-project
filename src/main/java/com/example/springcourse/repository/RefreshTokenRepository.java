//package com.example.springcourse.repository;
//import com.example.springcourse.entity.token.RefreshToken;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.time.Instant;
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
//
//    // Найти токен по его хешу
//    Optional<RefreshToken> findByTokenHash(String tokenHash);
//
//    // Найти все активные токены пользователя
//    @Query("SELECT rt FROM RefreshToken rt WHERE rt.person.id = :personId AND rt.isRevoked = false AND rt.expiresAt > :now")
//    List<RefreshToken> findActiveTokensByPersonId(Long personId, Instant now);
//
//    // Проверить существование активного токена
//    boolean existsByTokenHashAndIsRevokedFalseAndExpiresAtAfter(String tokenHash, Instant now);
//
//    // Отозвать все токены пользователя
//    @Modifying
//    @Query("UPDATE RefreshToken rt SET rt.isRevoked = true WHERE rt.person.id = :personId")
//    void revokeAllByPersonId(Long personId);
//
//    // Отозвать конкретный токен
//    @Modifying
//    @Query("UPDATE RefreshToken rt SET rt.isRevoked = true WHERE rt.tokenHash = :tokenHash")
//    void revokeByTokenHash(String tokenHash);
//
//    // Удалить все истёкшие токены
//    @Modifying
//    @Query("DELETE FROM RefreshToken rt WHERE rt.expiresAt < :now")
//    void deleteExpiredTokens(Instant now);
//
//    // Обновить токен (при refresh)
//    @Modifying
//    @Query("UPDATE RefreshToken rt SET rt.tokenHash = :newHash, rt.expiresAt = :newExpiry, rt.isRevoked = false WHERE rt.id = :id")
//    void updateToken(Long id, String newHash, Instant newExpiry);
//}
