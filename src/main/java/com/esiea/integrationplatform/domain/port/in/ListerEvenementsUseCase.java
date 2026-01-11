package com.esiea.integrationplatform.domain.port.in;

import com.esiea.integrationplatform.domain.model.Evenement;
import java.util.List;

/**
 * Port d'entrée pour le cas d'usage de listage des événements
 */
public interface ListerEvenementsUseCase {

    /**
     * Récupère tous les événements
     * @return liste de tous les événements
     */
    List<Evenement> execute();
}