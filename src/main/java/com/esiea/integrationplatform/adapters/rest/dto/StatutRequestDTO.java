package com.esiea.integrationplatform.adapters.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * DTO pour la modification du statut d'un événement
 */
public class StatutRequestDTO {

    @NotBlank(message = "Le statut est obligatoire")
    @Pattern(regexp = "BROUILLON|PUBLIE|ANNULE|TERMINE",
            message = "Le statut doit être : BROUILLON, PUBLIE, ANNULE ou TERMINE")
    private String statut;

    // Constructeur vide
    public StatutRequestDTO() {
    }

    // Constructeur avec paramètre
    public StatutRequestDTO(String statut) {
        this.statut = statut;
    }

    // Getter
    public String getStatut() {
        return statut;
    }

    // Setter
    public void setStatut(String statut) {
        this.statut = statut;
    }
}