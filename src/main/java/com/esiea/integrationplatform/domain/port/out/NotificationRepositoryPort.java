package com.esiea.integrationplatform.domain.port.out;

import com.esiea.integrationplatform.domain.model.Notification;
import java.util.List;
import java.util.Optional;

/**
 * Port de sortie pour la persistance des notifications
 * Interface métier - AUCUNE ANNOTATION SPRING
 */
public interface NotificationRepositoryPort {

    /**
     * Sauvegarde une notification
     * @param notification la notification à sauvegarder
     * @return la notification sauvegardée avec son ID
     */
    Notification save(Notification notification);

    /**
     * Trouve une notification par son ID
     * @param id l'ID de la notification
     * @return la notification si trouvée
     */
    Optional<Notification> findById(Long id);

    /**
     * Trouve toutes les notifications d'un utilisateur
     * @param userId l'ID de l'utilisateur
     * @return la liste des notifications
     */
    List<Notification> findByUserId(Long userId);

    /**
     * Trouve les notifications non lues d'un utilisateur
     * @param userId l'ID de l'utilisateur
     * @return la liste des notifications non lues
     */
    List<Notification> findByUserIdAndStatut(Long userId, String statut);

    /**
     * Supprime une notification
     * @param id l'ID de la notification à supprimer
     */
    void deleteById(Long id);
}
