package com.esiea.integrationplatform.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entité JPA représentant une inscription dans la base de données
 * Cette classe contient les annotations JPA
 */
@Entity
@Table(name = "inscriptions",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"etudiant_id", "evenement_id"})
        },
        indexes = {
                @Index(name = "idx_etudiant", columnList = "etudiant_id"),
                @Index(name = "idx_evenement", columnList = "evenement_id")
        })
public class InscriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "etudiant_id", nullable = false)
    private Long etudiantId;

    @Column(name = "evenement_id", nullable = false)
    private Long evenementId;

    @Column(nullable = false, length = 20)
    private String statut;

    @Column(name = "date_inscription", nullable = false)
    private LocalDateTime dateInscription;

    @Column(length = 500)
    private String commentaire;

    // Constructeurs
    public InscriptionEntity() {
    }

    public InscriptionEntity(Long id, Long etudiantId, Long evenementId,
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
}
