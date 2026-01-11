package com.esiea.integrationplatform.domain.service;

import com.esiea.integrationplatform.domain.model.Evenement;

/**
 * Service métier pour valider les règles de gestion d'un événement
 * AUCUNE ANNOTATION SPRING
 */
public class EvenementValidator {

    public void validate(Evenement evenement) {
        if (evenement == null) {
            throw new IllegalArgumentException("L'événement ne peut pas être null");
        }

        // Validation du titre
        if (evenement.getTitre() == null || evenement.getTitre().trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre de l'événement est obligatoire");
        }

        if (evenement.getTitre().length() < 3) {
            throw new IllegalArgumentException("Le titre doit contenir au moins 3 caractères");
        }

        if (evenement.getTitre().length() > 100) {
            throw new IllegalArgumentException("Le titre ne peut pas dépasser 100 caractères");
        }

        // Validation de la capacité
        if (evenement.getCapaciteMax() != null && evenement.getCapaciteMax() <= 0) {
            throw new IllegalArgumentException("La capacité maximale doit être strictement positive");
        }

        if (evenement.getCapaciteMax() != null && evenement.getCapaciteMax() > 1000) {
            throw new IllegalArgumentException("La capacité maximale ne peut pas dépasser 1000 personnes");
        }

        // Validation des dates
        if (evenement.getDateDebut() == null) {
            throw new IllegalArgumentException("La date de début est obligatoire");
        }

        if (evenement.getDateFin() != null &&
                evenement.getDateFin().isBefore(evenement.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin doit être postérieure à la date de début");
        }

        // Validation du statut
        if (evenement.getStatut() != null) {
            String statut = evenement.getStatut().toUpperCase();
            if (!statut.equals("BROUILLON") &&
                    !statut.equals("PUBLIE") &&
                    !statut.equals("ANNULE") &&
                    !statut.equals("TERMINE")) {
                throw new IllegalArgumentException(
                        "Le statut doit être : BROUILLON, PUBLIE, ANNULE ou TERMINE"
                );
            }
        }

        // Validation du type d'événement
        if (evenement.getTypeEvenement() != null) {
            String type = evenement.getTypeEvenement().toUpperCase();
            if (!type.equals("SOIREE") &&
                    !type.equals("ATELIER") &&
                    !type.equals("SPORT") &&
                    !type.equals("CONFERENCE")) {
                throw new IllegalArgumentException(
                        "Le type d'événement doit être : SOIREE, ATELIER, SPORT ou CONFERENCE"
                );
            }
        }
    }
}