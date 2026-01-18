package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.exception.NotificationNotFoundException;
import com.esiea.integrationplatform.domain.model.Notification;
import com.esiea.integrationplatform.domain.port.in.MarquerNotificationLueUseCase;
import com.esiea.integrationplatform.domain.port.out.NotificationRepositoryPort;

/**
 * Implémentation du use case de marquage d'une notification comme lue
 * AUCUNE ANNOTATION SPRING - Pure Java
 */
public class MarquerNotificationLueUseCaseImpl implements MarquerNotificationLueUseCase {

    private final NotificationRepositoryPort notificationRepository;

    public MarquerNotificationLueUseCaseImpl(NotificationRepositoryPort notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification marquerCommeLue(Long notificationId, Long userId) {
        if (notificationId == null) {
            throw new IllegalArgumentException("L'ID de la notification est obligatoire");
        }
        if (userId == null) {
            throw new IllegalArgumentException("L'ID de l'utilisateur est obligatoire");
        }

        // Récupération de la notification
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException(notificationId));

        // Vérification que la notification appartient bien à l'utilisateur
        if (!notification.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Cette notification n'appartient pas à cet utilisateur");
        }

        // Marquage comme lue
        notification.marquerCommeLue();

        // Sauvegarde
        return notificationRepository.save(notification);
    }
}
