package com.esiea.integrationplatform.adapters.rest.dto;

/**
 * DTO pour la création d'un utilisateur
 */
public class CreateUserRequestDTO {

    private String email;
    private String motDePasse;
    private String nom;
    private String prenom;
    private String promotion;
    private String numeroEtudiant;
    private String filiere;

    // Constructeurs
    public CreateUserRequestDTO() {
    }

    public CreateUserRequestDTO(String email, String motDePasse, String nom, String prenom,
                                String promotion, String numeroEtudiant, String filiere) {
        this.email = email;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.promotion = promotion;
        this.numeroEtudiant = numeroEtudiant;
        this.filiere = filiere;
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getNumeroEtudiant() {
        return numeroEtudiant;
    }

    public void setNumeroEtudiant(String numeroEtudiant) {
        this.numeroEtudiant = numeroEtudiant;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }
}
