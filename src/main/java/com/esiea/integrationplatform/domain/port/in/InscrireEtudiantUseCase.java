package com.esiea.integrationplatform.domain.port.in;

import com.esiea.integrationplatform.domain.model.Inscription;

/**
 * Port d'entrée pour inscrire un étudiant à un événement
 * Use case du domaine
 */
public interface InscrireEtudiantUseCase {

    /**
     * Inscrit un étudiant à un événement
     * @param etudiantId ID de l'étudiant
     * @param evenementId ID de l'événement
     * @param commentaire Commentaire optionnel
     * @return L'inscription créée
     */
    Inscription execute(Long etudiantId, Long evenementId, String commentaire);
}
