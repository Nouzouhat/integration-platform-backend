package com.esiea.integrationplatform.adapters.kafka;

import com.esiea.integrationplatform.domain.port.in.CreerNotificationUseCase;
import com.esiea.integrationplatform.domain.port.out.InscriptionRepositoryPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Listener Kafka pour créer automatiquement des notifications
 * Écoute les événements de changement de statut d'événement
 */
@Component
@ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "true", matchIfMissing = true)
public class NotificationKafkaListener {

    private static final Logger log = LoggerFactory.getLogger(NotificationKafkaListener.class);

    private final CreerNotificationUseCase creerNotificationUseCase;
    private final InscriptionRepositoryPort inscriptionRepository;
    private final ObjectMapper objectMapper;

    public NotificationKafkaListener(CreerNotificationUseCase creerNotificationUseCase,
                                    InscriptionRepositoryPort inscriptionRepository) {
        this.creerNotificationUseCase = creerNotificationUseCase;
        this.inscriptionRepository = inscriptionRepository;
        this.objectMapper = new ObjectMapper();
        // Configuration pour gérer LocalDateTime
        this.objectMapper.findAndRegisterModules();
    }

    /**
     * Écoute les événements de changement de statut d'événement
     * et crée des notifications pour tous les utilisateurs inscrits
     */
    @KafkaListener(topics = "event-status-change", groupId = "notification-group")
    public void handleEventStatusChange(String message) {
        try {
            log.info("Événement de changement de statut reçu : {}", message);

            EventStatusChangeEvent event = objectMapper.readValue(message, EventStatusChangeEvent.class);

            // Récupérer toutes les inscriptions pour cet événement
            var inscriptions = inscriptionRepository.findByEvenementId(event.getEvenementId());

            // Créer une notification pour chaque utilisateur inscrit
            for (var inscription : inscriptions) {
                String notificationMessage = String.format(
                        "Le statut de l'événement '%s' auquel vous êtes inscrit est passé de '%s' à '%s'",
                        event.getTitre(),
                        event.getAncienStatut(),
                        event.getNouveauStatut()
                );

                creerNotificationUseCase.creerNotification(
                        inscription.getEtudiantId(),
                        "EVENT_STATUS_CHANGE",
                        "Changement de statut d'événement",
                        notificationMessage,
                        event.getEvenementId(),
                        null
                );

                log.info("Notification créée pour l'utilisateur {} concernant l'événement {}",
                        inscription.getEtudiantId(), event.getEvenementId());
            }

        } catch (Exception e) {
            log.error("Erreur lors du traitement de l'événement de changement de statut", e);
        }
    }

    /**
     * Écoute les événements d'actions utilisateur
     * (pour logging ou traitement futur)
     */
    @KafkaListener(topics = "user-action", groupId = "notification-group")
    public void handleUserAction(String message) {
        try {
            log.info("Événement d'action utilisateur reçu : {}", message);

            UserActionEvent event = objectMapper.readValue(message, UserActionEvent.class);

            // Pour l'instant, on log simplement l'événement
            // On pourrait créer des notifications pour les admins, etc.
            log.info("Action '{}' effectuée par l'utilisateur {} : {}",
                    event.getAction(), event.getUserId(), event.getDetails());

        } catch (Exception e) {
            log.error("Erreur lors du traitement de l'événement d'action utilisateur", e);
        }
    }
}
