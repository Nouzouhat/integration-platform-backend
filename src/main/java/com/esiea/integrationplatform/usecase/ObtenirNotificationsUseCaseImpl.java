package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.model.Notification;
import com.esiea.integrationplatform.domain.port.in.ObtenirNotificationsUseCase;
import com.esiea.integrationplatform.domain.port.out.NotificationRepositoryPort;

import java.util.List;

/**
 * Implémentation du use case d'obtention des notifications
 * AUCUNE ANNOTATION SPRING - Pure Java
 */
public class ObtenirNotificationsUseCaseImpl implements ObtenirNotificationsUseCase {

    private final NotificationRepositoryPort notificationRepository;

    public ObtenirNotificationsUseCaseImpl(NotificationRepositoryPort notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> obtenirNotificationsUtilisateur(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("L'ID de l'utilisateur est obligatoire");
        }
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> obtenirNotificationsNonLues(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("L'ID de l'utilisateur est obligatoire");
        }
        return notificationRepository.findByUserIdAndStatut(userId, "NON_LUE");
    }
}
