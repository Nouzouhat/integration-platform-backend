package com.esiea.integrationplatform.adapters.kafka;

/**
 * Classe représentant l'événement Kafka publié quand un événement est créé
 */
public class EvenementCreeEvent {

    private Long evenementId;
    private String titre;
    private String message;

    public EvenementCreeEvent() {
    }

    public EvenementCreeEvent(Long evenementId, String titre, String message) {
        this.evenementId = evenementId;
        this.titre = titre;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "EvenementCreeEvent{" +
                "evenementId=" + evenementId +
                ", titre='" + titre + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}