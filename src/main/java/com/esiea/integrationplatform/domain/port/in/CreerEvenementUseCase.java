package com.esiea.integrationplatform.domain.port.in;

import com.esiea.integrationplatform.domain.model.Evenement;

/**
 * Port d'entrée pour le cas d'usage de création d'événement
 */
public interface CreerEvenementUseCase {

    /**
     * Crée un nouvel événement
     * @param evenement l'événement à créer
     * @return l'événement créé avec son ID
     */
    Evenement execute(Evenement evenement);
}