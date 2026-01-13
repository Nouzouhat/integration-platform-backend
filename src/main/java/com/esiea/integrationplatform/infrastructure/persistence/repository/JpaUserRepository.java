package com.esiea.integrationplatform.infrastructure.persistence.repository;

import com.esiea.integrationplatform.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository JPA pour les utilisateurs
 */
@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Recherche un utilisateur par email
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Vérifie si un email existe déjà
     */
    boolean existsByEmail(String email);

    /**
     * Recherche tous les utilisateurs par rôle
     */
    List<UserEntity> findByRole(String role);

    /**
     * Recherche tous les utilisateurs actifs/inactifs
     */
    List<UserEntity> findByActif(Boolean actif);

    /**
     * Compte le nombre d'utilisateurs par rôle
     */
    long countByRole(String role);
}
