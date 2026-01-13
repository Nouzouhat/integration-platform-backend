package com.esiea.integrationplatform.domain.service;

import com.esiea.integrationplatform.domain.exception.InvalidUserException;
import com.esiea.integrationplatform.domain.model.User;

import java.util.regex.Pattern;

/**
 * Validateur pour les données utilisateur
 * Logique métier pure - pas de dépendances externes
 */
public class UserValidator {

    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_EMAIL_LENGTH = 255;
    private static final int MAX_NAME_LENGTH = 100;

    /**
     * Valide les données d'un utilisateur complet
     */
    public static void validate(User user) {
        if (user == null) {
            throw new InvalidUserException("L'utilisateur ne peut pas être null");
        }

        validateEmail(user.getEmail());
        validateNom(user.getNom());
        validatePrenom(user.getPrenom());
        validateRole(user.getRole());
    }

    /**
     * Valide les données pour la création d'un compte
     */
    public static void validateForCreation(String email, String motDePasse, 
                                          String nom, String prenom) {
        validateEmail(email);
        validateMotDePasse(motDePasse);
        validateNom(nom);
        validatePrenom(prenom);
    }

    /**
     * Valide un email
     */
    public static void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidUserException("L'email est obligatoire");
        }

        if (email.length() > MAX_EMAIL_LENGTH) {
            throw new InvalidUserException("L'email ne peut pas dépasser " + MAX_EMAIL_LENGTH + " caractères");
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidUserException("Le format de l'email est invalide");
        }
    }

    /**
     * Valide un mot de passe (en clair)
     */
    public static void validateMotDePasse(String motDePasse) {
        if (motDePasse == null || motDePasse.isEmpty()) {
            throw new InvalidUserException("Le mot de passe est obligatoire");
        }

        if (motDePasse.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidUserException(
                "Le mot de passe doit contenir au moins " + MIN_PASSWORD_LENGTH + " caractères"
            );
        }

        // Vérifier qu'il contient au moins une lettre et un chiffre
        boolean hasLetter = motDePasse.matches(".*[a-zA-Z].*");
        boolean hasDigit = motDePasse.matches(".*\\d.*");

        if (!hasLetter || !hasDigit) {
            throw new InvalidUserException(
                "Le mot de passe doit contenir au moins une lettre et un chiffre"
            );
        }
    }

    /**
     * Valide un nom
     */
    public static void validateNom(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new InvalidUserException("Le nom est obligatoire");
        }

        if (nom.length() > MAX_NAME_LENGTH) {
            throw new InvalidUserException("Le nom ne peut pas dépasser " + MAX_NAME_LENGTH + " caractères");
        }
    }

    /**
     * Valide un prénom
     */
    public static void validatePrenom(String prenom) {
        if (prenom == null || prenom.trim().isEmpty()) {
            throw new InvalidUserException("Le prénom est obligatoire");
        }

        if (prenom.length() > MAX_NAME_LENGTH) {
            throw new InvalidUserException("Le prénom ne peut pas dépasser " + MAX_NAME_LENGTH + " caractères");
        }
    }

    /**
     * Valide un rôle
     */
    public static void validateRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            throw new InvalidUserException("Le rôle est obligatoire");
        }

        if (!role.equals("ETUDIANT") && !role.equals("ADMIN")) {
            throw new InvalidUserException("Le rôle doit être ETUDIANT ou ADMIN");
        }
    }
}
