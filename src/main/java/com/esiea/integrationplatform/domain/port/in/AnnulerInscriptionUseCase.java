package com.esiea.integrationplatform.domain.port.in;

/**
 * Port d'entrée pour annuler/supprimer une inscription
 */
public interface AnnulerInscriptionUseCase {

    /**
     * Annule une inscription existante
     *
     * @param id L'identifiant de l'inscription à annuler
     */
    void execute(Long id);
}
