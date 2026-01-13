package com.esiea.integrationplatform.domain.port.in;

import com.esiea.integrationplatform.domain.model.Inscription;

/**
 * Port d'entrée pour récupérer une inscription par son ID
 */
public interface RecupererInscriptionUseCase {

    /**
     * Récupère une inscription par son identifiant
     *
     * @param id L'identifiant de l'inscription
     * @return L'inscription trouvée
     */
    Inscription execute(Long id);
}
