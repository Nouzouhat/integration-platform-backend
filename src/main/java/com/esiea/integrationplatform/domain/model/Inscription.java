package com.esiea.integrationplatform.domain.model;

import java.time.LocalDateTime;

/**
 * Entité métier représentant une inscription d'un étudiant à un événement
 * AUCUNE ANNOTATION SPRING/JPA - Pure Java
 */
public class Inscription {

    private Long id;
    private Long etudiantId;
    private Long evenementId;
    private String statut;
    private LocalDateTime dateInscription;
    private String commentaire;

    // Constructeur vide
    public Inscription() {
    }

    // Constructeur complet
    public Inscription(Long id, Long etudiantId, Long evenementId,
                       String statut, LocalDateTime dateInscription,
                       String commentaire) {
        this.id = id;
        this.etudiantId = etudiantId;
        this.evenementId = evenementId;
        this.statut = statut;
        this.dateInscription = dateInscription;
        this.commentaire = commentaire;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(Long etudiantId) {
        this.etudiantId = etudiantId;
    }

    public Long getEvenementId() {
        return evenementId;
    }

    public void setEvenementId(Long evenementId) {
        this.evenementId = evenementId;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDateTime dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public String toString() {
        return "Inscription{" +
                "id=" + id +
                ", etudiantId=" + etudiantId +
                ", evenementId=" + evenementId +
                ", statut='" + statut + '\'' +
                ", dateInscription=" + dateInscription +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }
}

