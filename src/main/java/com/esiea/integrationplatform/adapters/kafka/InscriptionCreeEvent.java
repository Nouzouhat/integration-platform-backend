package com.esiea.integrationplatform.adapters.kafka;

/**
 * Classe représentant l'événement Kafka publié quand une inscription est créée
 */
public class InscriptionCreeEvent {

    private Long inscriptionId;
    private Long etudiantId;
    private Long evenementId;
    private String statut;
    private String message;

    public InscriptionCreeEvent() {
    }

    public InscriptionCreeEvent(Long inscriptionId, Long etudiantId, Long evenementId, String statut, String message) {
        this.inscriptionId = inscriptionId;
        this.etudiantId = etudiantId;
        this.evenementId = evenementId;
        this.statut = statut;
        this.message = message;
    }

    public Long getInscriptionId() {
        return inscriptionId;
    }

    public void setInscriptionId(Long inscriptionId) {
        this.inscriptionId = inscriptionId;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "InscriptionCreeEvent{" +
                "inscriptionId=" + inscriptionId +
                ", etudiantId=" + etudiantId +
                ", evenementId=" + evenementId +
                ", statut='" + statut + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
