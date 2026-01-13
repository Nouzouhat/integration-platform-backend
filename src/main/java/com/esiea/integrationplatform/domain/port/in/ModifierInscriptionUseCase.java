package com.esiea.integrationplatform.domain.port.in;

import com.esiea.integrationplatform.domain.model.Inscription;

/**
 * Port d'entrée pour modifier une inscription
 */
public interface ModifierInscriptionUseCase {

    /**
     * Modifie une inscription existante
     *
     * @param id L'identifiant de l'inscription
     * @param statut Le nouveau statut (optionnel)
     * @param commentaire Le nouveau commentaire (optionnel)
     * @return L'inscription modifiée
     */
    Inscription execute(Long id, String statut, String commentaire);
}
