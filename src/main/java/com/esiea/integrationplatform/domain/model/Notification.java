package com.esiea.integrationplatform.domain.model;

import java.time.LocalDateTime;

/**
 * Entité métier représentant une notification
 * AUCUNE ANNOTATION SPRING/JPA - Pure Java
 */
public class Notification {

    private Long id;
    private Long userId;
    private String type; // USER_ACTION, EVENT_STATUS_CHANGE
    private String titre;
    private String message;
    private String statut; // NON_LUE, LUE
    private LocalDateTime dateCreation;
    private LocalDateTime dateLecture;
    
    // Métadonnées optionnelles
    private Long evenementId; // Pour les notifications liées à un événement
    private String action; // Pour les notifications d'actions utilisateur (CREATION, MODIFICATION, SUPPRESSION, etc.)

    // Constructeur vide
    public Notification() {
    }

    // Constructeur complet
    public Notification(Long id, Long userId, String type, String titre, String message,
                       String statut, LocalDateTime dateCreation, LocalDateTime dateLecture,
                       Long evenementId, String action) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.titre = titre;
        this.message = message;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateLecture = dateLecture;
        this.evenementId = evenementId;
        this.action = action;
    }

    // Méthodes métier
    public boolean isLue() {
        return "LUE".equals(this.statut);
    }

    public void marquerCommeLue() {
        this.statut = "LUE";
        this.dateLecture = LocalDateTime.now();
    }

    public boolean isNotificationEvenement() {
        return "EVENT_STATUS_CHANGE".equals(this.type);
    }

    public boolean isNotificationAction() {
        return "USER_ACTION".equals(this.type);
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateLecture() {
        return dateLecture;
    }

    public void setDateLecture(LocalDateTime dateLecture) {
        this.dateLecture = dateLecture;
    }

    public Long getEvenementId() {
        return evenementId;
    }

    public void setEvenementId(Long evenementId) {
        this.evenementId = evenementId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", userId=" + userId +
                ", type='" + type + '\'' +
                ", titre='" + titre + '\'' +
                ", message='" + message + '\'' +
                ", statut='" + statut + '\'' +
                ", dateCreation=" + dateCreation +
                ", dateLecture=" + dateLecture +
                ", evenementId=" + evenementId +
                ", action='" + action + '\'' +
                '}';
    }
}
