package com.esiea.integrationplatform.domain.port.in;

import com.esiea.integrationplatform.domain.model.Notification;
import java.util.List;

/**
 * Port d'entrée pour obtenir les notifications d'un utilisateur
 * Interface métier - AUCUNE ANNOTATION SPRING
 */
public interface ObtenirNotificationsUseCase {

    /**
     * Récupère toutes les notifications d'un utilisateur
     * @param userId l'ID de l'utilisateur
     * @return la liste des notifications
     */
    List<Notification> obtenirNotificationsUtilisateur(Long userId);

    /**
     * Récupère les notifications non lues d'un utilisateur
     * @param userId l'ID de l'utilisateur
     * @return la liste des notifications non lues
     */
    List<Notification> obtenirNotificationsNonLues(Long userId);
}
