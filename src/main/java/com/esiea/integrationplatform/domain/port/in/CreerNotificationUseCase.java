package com.esiea.integrationplatform.domain.port.in;

import com.esiea.integrationplatform.domain.model.Notification;

/**
 * Port d'entrée pour créer une notification
 * Interface métier - AUCUNE ANNOTATION SPRING
 */
public interface CreerNotificationUseCase {

    /**
     * Crée une nouvelle notification
     * @param userId l'ID de l'utilisateur destinataire
     * @param type le type de notification (USER_ACTION, EVENT_STATUS_CHANGE)
     * @param titre le titre de la notification
     * @param message le message de la notification
     * @param evenementId l'ID de l'événement (optionnel, pour EVENT_STATUS_CHANGE)
     * @param action l'action effectuée (optionnel, pour USER_ACTION)
     * @return la notification créée
     */
    Notification creerNotification(Long userId, String type, String titre, String message, 
                                   Long evenementId, String action);
}
