package com.esiea.integrationplatform.adapters.kafka;

import java.time.LocalDateTime;

/**
 * Classe représentant l'événement Kafka publié quand le statut d'un événement change
 */
public class EventStatusChangeEvent {

    private Long evenementId;
    private String titre;
    private String ancienStatut;
    private String nouveauStatut;
    private String message;
    private LocalDateTime timestamp;

    public EventStatusChangeEvent() {
    }

    public EventStatusChangeEvent(Long evenementId, String titre, String ancienStatut, 
                                  String nouveauStatut, String message, LocalDateTime timestamp) {
        this.evenementId = evenementId;
        this.titre = titre;
        this.ancienStatut = ancienStatut;
        this.nouveauStatut = nouveauStatut;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Long getEvenementId() {
        return evenementId;
    }

    public void setEvenementId(Long evenementId) {
        this.evenementId = evenementId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAncienStatut() {
        return ancienStatut;
    }

    public void setAncienStatut(String ancienStatut) {
        this.ancienStatut = ancienStatut;
    }

    public String getNouveauStatut() {
        return nouveauStatut;
    }

    public void setNouveauStatut(String nouveauStatut) {
        this.nouveauStatut = nouveauStatut;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "EventStatusChangeEvent{" +
                "evenementId=" + evenementId +
                ", titre='" + titre + '\'' +
                ", ancienStatut='" + ancienStatut + '\'' +
                ", nouveauStatut='" + nouveauStatut + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
