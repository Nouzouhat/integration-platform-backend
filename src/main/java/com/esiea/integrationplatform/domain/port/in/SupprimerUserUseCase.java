package com.esiea.integrationplatform.domain.port.in;

/**
 * Use Case : Supprimer un utilisateur
 * AUCUNE ANNOTATION SPRING
 */
public interface SupprimerUserUseCase {

    /**
     * Supprime un utilisateur par son ID
     *
     * @param id l'ID de l'utilisateur à supprimer
     * @throws com.esiea.integrationplatform.domain.exception.UserNotFoundException si l'utilisateur n'existe pas
     */
    void execute(Long id);
}
