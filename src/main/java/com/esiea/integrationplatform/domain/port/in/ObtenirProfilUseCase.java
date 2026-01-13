package com.esiea.integrationplatform.domain.port.in;

import com.esiea.integrationplatform.domain.model.User;

/**
 * Use Case : Obtenir le profil d'un utilisateur
 */
public interface ObtenirProfilUseCase {

    /**
     * Récupère le profil complet d'un utilisateur par son ID
     *
     * @param userId ID de l'utilisateur
     * @return Le profil de l'utilisateur
     * @throws com.esiea.integrationplatform.domain.exception.UserNotFoundException si l'utilisateur n'existe pas
     */
    User execute(Long userId);
}
