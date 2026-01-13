package com.esiea.integrationplatform.domain.port.in;

import com.esiea.integrationplatform.domain.model.User;

/**
 * Use Case : Modifier le profil d'un utilisateur
 */
public interface ModifierProfilUseCase {

    /**
     * Modifie le profil d'un utilisateur
     * Seuls les champs non-null sont modifiés
     *
     * @param userId ID de l'utilisateur
     * @param nom Nouveau nom (optionnel)
     * @param prenom Nouveau prénom (optionnel)
     * @param promotion Nouvelle promotion (optionnel)
     * @param numeroEtudiant Nouveau numéro étudiant (optionnel)
     * @param filiere Nouvelle filière (optionnel)
     * @return L'utilisateur modifié
     * @throws com.esiea.integrationplatform.domain.exception.UserNotFoundException si l'utilisateur n'existe pas
     * @throws com.esiea.integrationplatform.domain.exception.InvalidUserException si les données sont invalides
     */
    User execute(Long userId, String nom, String prenom, 
                 String promotion, String numeroEtudiant, String filiere);
}
