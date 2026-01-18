package com.esiea.integrationplatform.infrastructure.persistence.repository;

import com.esiea.integrationplatform.infrastructure.persistence.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository JPA pour les notifications
 */
@Repository
public interface JpaNotificationRepository extends JpaRepository<NotificationEntity, Long> {

    /**
     * Recherche toutes les notifications d'un utilisateur
     */
    List<NotificationEntity> findByUserId(Long userId);

    /**
     * Recherche les notifications d'un utilisateur par statut
     */
    List<NotificationEntity> findByUserIdAndStatut(Long userId, String statut);

    /**
     * Recherche les notifications d'un utilisateur par type
     */
    List<NotificationEntity> findByUserIdAndType(Long userId, String type);

    /**
     * Compte le nombre de notifications non lues d'un utilisateur
     */
    long countByUserIdAndStatut(Long userId, String statut);
}
