package com.esiea.integrationplatform.infrastructure.security;

import com.esiea.integrationplatform.domain.port.out.PasswordEncoderPort;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Implémentation TEMPORAIRE du PasswordEncoderPort
 * Utilise SHA-256 en attendant l'intégration de Spring Security avec BCrypt
 * 
 * ⚠️ ATTENTION : Cette implémentation sera remplacée par BCryptPasswordEncoder
 * lors de l'ajout de Spring Security
 */
@Component
public class SimplePasswordEncoderAdapter implements PasswordEncoderPort {

    @Override
    public String encode(String rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawPassword.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur lors du hashage du mot de passe", e);
        }
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        String hashedRaw = encode(rawPassword);
        return hashedRaw.equals(encodedPassword);
    }
}
