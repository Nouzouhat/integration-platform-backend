package com.esiea.integrationplatform.domain.port.in;

import com.esiea.integrationplatform.domain.model.User;

/**
 * Use Case : Créer un compte utilisateur (Register)
 * Par défaut, un nouveau compte a le rôle ETUDIANT
 */
public interface CreerCompteUseCase {

    /**
     * Crée un nouveau compte utilisateur
     *
     * @param email Email unique de l'utilisateur
     * @param motDePasse Mot de passe en clair (sera hashé)
     * @param nom Nom de famille
     * @param prenom Prénom
     * @param promotion Promotion (optionnel)
     * @param numeroEtudiant Numéro étudiant (optionnel)
     * @param filiere Filière (optionnel)
     * @return L'utilisateur créé
     * @throws com.esiea.integrationplatform.domain.exception.UserAlreadyExistsException si l'email existe déjà
     * @throws com.esiea.integrationplatform.domain.exception.InvalidUserException si les données sont invalides
     */
    User execute(String email, String motDePasse, String nom, String prenom,
                 String promotion, String numeroEtudiant, String filiere);
}
