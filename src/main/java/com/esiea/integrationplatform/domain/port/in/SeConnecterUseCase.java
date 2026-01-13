package com.esiea.integrationplatform.domain.port.in;

import com.esiea.integrationplatform.domain.model.User;

/**
 * Use Case : Se connecter (Login)
 * Vérifie les credentials et retourne l'utilisateur si valide
 */
public interface SeConnecterUseCase {

    /**
     * Authentifie un utilisateur avec email et mot de passe
     *
     * @param email Email de l'utilisateur
     * @param motDePasse Mot de passe en clair
     * @return L'utilisateur authentifié
     * @throws com.esiea.integrationplatform.domain.exception.InvalidCredentialsException si les credentials sont incorrects
     * @throws com.esiea.integrationplatform.domain.exception.UserNotFoundException si l'utilisateur n'existe pas
     */
    User execute(String email, String motDePasse);
}
