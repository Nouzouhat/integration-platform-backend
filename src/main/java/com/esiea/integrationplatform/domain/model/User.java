package com.esiea.integrationplatform.domain.model;

import java.time.LocalDateTime;

/**
 * Entité métier représentant un utilisateur de la plateforme
 * Un User peut être un ETUDIANT ou un ADMIN
 * AUCUNE ANNOTATION SPRING/JPA - Pure Java
 */
public class User {

    private Long id;
    private String email;
    private String motDePasse; // Stocké hashé (BCrypt)
    private String nom;
    private String prenom;
    private String role; // ETUDIANT, ADMIN
    private Boolean actif;
    private LocalDateTime dateCreation;
    
    // Champs optionnels spécifiques aux étudiants
    private String promotion; // Ex: "2024-2025"
    private String numeroEtudiant; // Ex: "E2024001"
    private String filiere; // Ex: "Informatique"

    // Constructeur vide
    public User() {
    }

    // Constructeur complet
    public User(Long id, String email, String motDePasse, String nom, String prenom,
                String role, Boolean actif, LocalDateTime dateCreation,
                String promotion, String numeroEtudiant, String filiere) {
        this.id = id;
        this.email = email;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
        this.actif = actif;
        this.dateCreation = dateCreation;
        this.promotion = promotion;
        this.numeroEtudiant = numeroEtudiant;
        this.filiere = filiere;
    }

    // Méthodes métier
    public boolean isAdmin() {
        return "ADMIN".equals(this.role);
    }

    public boolean isEtudiant() {
        return "ETUDIANT".equals(this.role);
    }

    public String getNomComplet() {
        return this.prenom + " " + this.nom;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", role='" + role + '\'' +
                ", actif=" + actif +
                ", dateCreation=" + dateCreation +
                ", promotion='" + promotion + '\'' +
                ", numeroEtudiant='" + numeroEtudiant + '\'' +
                ", filiere='" + filiere + '\'' +
                '}';
    }
}
