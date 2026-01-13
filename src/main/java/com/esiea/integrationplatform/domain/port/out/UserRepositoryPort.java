package com.esiea.integrationplatform.domain.port.out;

import com.esiea.integrationplatform.domain.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Port de sortie pour la persistance des utilisateurs
 * Interface du domaine - implémentée par l'infrastructure
 */
public interface UserRepositoryPort {

    /**
     * Sauvegarde un utilisateur
     */
    User save(User user);

    /**
     * Recherche un utilisateur par son ID
     */
    Optional<User> findById(Long id);

    /**
     * Recherche un utilisateur par son email
     */
    Optional<User> findByEmail(String email);

    /**
     * Vérifie si un email existe déjà
     */
    boolean existsByEmail(String email);

    /**
     * Récupère tous les utilisateurs
     */
    List<User> findAll();

    /**
     * Récupère tous les utilisateurs par rôle
     */
    List<User> findByRole(String role);

    /**
     * Récupère tous les utilisateurs actifs
     */
    List<User> findByActif(Boolean actif);

    /**
     * Supprime un utilisateur par son ID
     */
    void deleteById(Long id);

    /**
     * Compte le nombre total d'utilisateurs
     */
    long count();

    /**
     * Compte le nombre d'utilisateurs par rôle
     */
    long countByRole(String role);
}
