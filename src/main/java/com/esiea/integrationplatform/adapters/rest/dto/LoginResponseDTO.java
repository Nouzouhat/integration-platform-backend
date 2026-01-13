package com.esiea.integrationplatform.adapters.rest.dto;

/**
 * DTO pour la réponse de connexion (login)
 * Contient les informations de l'utilisateur
 */
public class LoginResponseDTO {

    private UserResponseDTO user;
    private String message;

    // Constructeurs
    public LoginResponseDTO() {
    }

    public LoginResponseDTO(UserResponseDTO user, String message) {
        this.user = user;
        this.message = message;
    }

    // Getters et Setters
    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
