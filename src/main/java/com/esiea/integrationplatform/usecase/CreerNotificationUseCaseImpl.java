package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.model.Notification;
import com.esiea.integrationplatform.domain.port.in.CreerNotificationUseCase;
import com.esiea.integrationplatform.domain.port.out.EventPublisherPort;
import com.esiea.integrationplatform.domain.port.out.NotificationRepositoryPort;

import java.time.LocalDateTime;

/**
 * Implémentation du use case de création de notification
 * AUCUNE ANNOTATION SPRING - Pure Java
 */
public class CreerNotificationUseCaseImpl implements CreerNotificationUseCase {

    private final NotificationRepositoryPort notificationRepository;
    private final EventPublisherPort eventPublisher;

    public CreerNotificationUseCaseImpl(NotificationRepositoryPort notificationRepository,
                                       EventPublisherPort eventPublisher) {
        this.notificationRepository = notificationRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Notification creerNotification(Long userId, String type, String titre, String message,
                                         Long evenementId, String action) {
        // Validation
        if (userId == null) {
            throw new IllegalArgumentException("L'ID de l'utilisateur est obligatoire");
        }
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Le type de notification est obligatoire");
        }
        if (titre == null || titre.trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre est obligatoire");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Le message est obligatoire");
        }

        // Validation du type
        if (!type.equals("USER_ACTION") && !type.equals("EVENT_STATUS_CHANGE")) {
            throw new IllegalArgumentException("Type de notification invalide: " + type);
        }

        // Création de la notification
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setType(type);
        notification.setTitre(titre);
        notification.setMessage(message);
        notification.setStatut("NON_LUE");
        notification.setDateCreation(LocalDateTime.now());
        notification.setEvenementId(evenementId);
        notification.setAction(action);

        // Sauvegarde
        Notification savedNotification = notificationRepository.save(notification);

        // Publication Kafka selon le type
        if ("USER_ACTION".equals(type) && action != null) {
            eventPublisher.publishUserAction(userId, action, message);
        } else if ("EVENT_STATUS_CHANGE".equals(type) && evenementId != null) {
            // Pour les changements de statut d'événement, on extrait l'info du message
            eventPublisher.publishEventStatusChange(evenementId, "", "", titre);
        }

        return savedNotification;
    }
}
