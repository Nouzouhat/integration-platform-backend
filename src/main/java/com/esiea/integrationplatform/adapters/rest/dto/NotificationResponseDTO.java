package com.esiea.integrationplatform.adapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * DTO de réponse pour une notification
 */
@Schema(description = "Réponse contenant les informations d'une notification")
public class NotificationResponseDTO {

    @Schema(description = "ID de la notification", example = "1")
    private Long id;

    @Schema(description = "ID de l'utilisateur destinataire", example = "1")
    private Long userId;

    @Schema(description = "Type de notification", example = "USER_ACTION")
    private String type;

    @Schema(description = "Titre de la notification", example = "Nouvelle action")
    private String titre;

    @Schema(description = "Message de la notification", example = "Vous avez effectué une action")
    private String message;

    @Schema(description = "Statut de la notification", example = "NON_LUE")
    private String statut;

    @Schema(description = "Date de création", example = "2026-01-18T13:00:00")
    private LocalDateTime dateCreation;

    @Schema(description = "Date de lecture", example = "2026-01-18T14:00:00")
    private LocalDateTime dateLecture;

    @Schema(description = "ID de l'événement (optionnel)", example = "1")
    private Long evenementId;

    @Schema(description = "Action effectuée (optionnel)", example = "CREATION")
    private String action;

    // Constructeur vide
    public NotificationResponseDTO() {
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
}
