package com.esiea.integrationplatform.adapters.rest.dto;

/**
 * DTO pour la requête de connexion (login)
 */
public class LoginRequestDTO {

    private String email;
    private String motDePasse;

    // Constructeurs
    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String email, String motDePasse) {
        this.email = email;
        this.motDePasse = motDePasse;
    }

    // Getters et Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
