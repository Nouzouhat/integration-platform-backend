package com.esiea.integrationplatform.domain.port.out;

/**
 * Port de sortie pour l'encodage des mots de passe
 * Abstraction pour ne pas dépendre de Spring Security dans le domaine
 */
public interface PasswordEncoderPort {

    /**
     * Encode (hash) un mot de passe en clair
     */
    String encode(String rawPassword);

    /**
     * Vérifie si un mot de passe en clair correspond au hash
     */
    boolean matches(String rawPassword, String encodedPassword);
}
