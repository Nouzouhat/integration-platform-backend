package com.esiea.integrationplatform.adapters.rest.dto;

/**
 * DTO pour la modification d'une inscription
 */
public class ModifierInscriptionRequestDTO {

    private String statut;
    private String commentaire;

    // Constructeurs
    public ModifierInscriptionRequestDTO() {
    }

    public ModifierInscriptionRequestDTO(String statut, String commentaire) {
        this.statut = statut;
        this.commentaire = commentaire;
    }

    // Getters et Setters
    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
