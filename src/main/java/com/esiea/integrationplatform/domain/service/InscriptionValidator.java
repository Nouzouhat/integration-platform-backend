package com.esiea.integrationplatform.domain.service;

import com.esiea.integrationplatform.domain.model.Inscription;

/**
 * Service métier pour valider les règles de gestion d'une inscription
 * AUCUNE ANNOTATION SPRING
 */
public class InscriptionValidator {

    public void validate(Inscription inscription) {
        if (inscription == null) {
            throw new IllegalArgumentException("L'inscription ne peut pas être null");
        }

        // Validation de l'étudiant ID
        if (inscription.getEtudiantId() == null) {
            throw new IllegalArgumentException("L'ID de l'étudiant est obligatoire");
        }

        if (inscription.getEtudiantId() <= 0) {
            throw new IllegalArgumentException("L'ID de l'étudiant doit être strictement positif");
        }

        // Validation de l'événement ID
        if (inscription.getEvenementId() == null) {
            throw new IllegalArgumentException("L'ID de l'événement est obligatoire");
        }

        if (inscription.getEvenementId() <= 0) {
            throw new IllegalArgumentException("L'ID de l'événement doit être strictement positif");
        }

        // Validation du statut
        if (inscription.getStatut() != null) {
            String statut = inscription.getStatut().toUpperCase();
            if (!statut.equals("EN_ATTENTE") &&
                    !statut.equals("CONFIRMEE") &&
                    !statut.equals("ANNULEE")) {
                throw new IllegalArgumentException(
                        "Le statut doit être : EN_ATTENTE, CONFIRMEE ou ANNULEE"
                );
            }
        }

        // Validation de la date d'inscription
        if (inscription.getDateInscription() == null) {
            throw new IllegalArgumentException("La date d'inscription est obligatoire");
        }

        // Validation du commentaire
        if (inscription.getCommentaire() != null &&
                inscription.getCommentaire().length() > 500) {
            throw new IllegalArgumentException("Le commentaire ne peut pas dépasser 500 caractères");
        }
    }
}
