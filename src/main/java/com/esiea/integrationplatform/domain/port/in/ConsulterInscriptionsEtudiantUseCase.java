package com.esiea.integrationplatform.domain.port.in;

import com.esiea.integrationplatform.domain.model.Inscription;

import java.util.List;

/**
 * Port d'entrée pour consulter les inscriptions d'un étudiant
 * Use case du domaine
 */
public interface ConsulterInscriptionsEtudiantUseCase {

    /**
     * Récupère toutes les inscriptions d'un étudiant
     * @param etudiantId ID de l'étudiant
     * @return Liste des inscriptions
     */
    List<Inscription> execute(Long etudiantId);
}
