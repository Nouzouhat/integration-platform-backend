package com.esiea.integrationplatform.domain.port.in;

import com.esiea.integrationplatform.domain.model.Notification;

/**
 * Port d'entrée pour marquer une notification comme lue
 * Interface métier - AUCUNE ANNOTATION SPRING
 */
public interface MarquerNotificationLueUseCase {

    /**
     * Marque une notification comme lue
     * @param notificationId l'ID de la notification
     * @param userId l'ID de l'utilisateur (pour vérification)
     * @return la notification mise à jour
     */
    Notification marquerCommeLue(Long notificationId, Long userId);
}
