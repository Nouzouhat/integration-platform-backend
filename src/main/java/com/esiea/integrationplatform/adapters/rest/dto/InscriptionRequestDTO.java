package com.esiea.integrationplatform.adapters.rest.dto;

/**
 * DTO pour les requêtes de création d'inscription
 */
public class InscriptionRequestDTO {

    private Long etudiantId;
    private Long evenementId;
    private String commentaire;

    // Constructeurs
    public InscriptionRequestDTO() {
    }

    public InscriptionRequestDTO(Long etudiantId, Long evenementId, String commentaire) {
        this.etudiantId = etudiantId;
        this.evenementId = evenementId;
        this.commentaire = commentaire;
    }

    // Getters et Setters
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

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
