package cl.municipalidadchillan.dideco.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.municipalidadchillan.dideco.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
    Optional<PasswordResetToken> findByToken(String token);
}
