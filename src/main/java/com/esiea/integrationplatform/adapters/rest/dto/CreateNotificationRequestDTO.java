package com.esiea.integrationplatform.adapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO pour créer une notification
 */
@Schema(description = "Requête pour créer une notification")
public class CreateNotificationRequestDTO {

    @Schema(description = "ID de l'utilisateur destinataire", example = "1", required = true)
    private Long userId;

    @Schema(description = "Type de notification", example = "USER_ACTION", allowableValues = {"USER_ACTION", "EVENT_STATUS_CHANGE"}, required = true)
    private String type;

    @Schema(description = "Titre de la notification", example = "Nouvelle action", required = true)
    private String titre;

    @Schema(description = "Message de la notification", example = "Vous avez effectué une action", required = true)
    private String message;

    @Schema(description = "ID de l'événement (optionnel)", example = "1")
    private Long evenementId;

    @Schema(description = "Action effectuée (optionnel)", example = "CREATION")
    private String action;

    // Constructeur vide
    public CreateNotificationRequestDTO() {
    }

    // Getters et Setters
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
