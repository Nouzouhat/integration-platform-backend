package com.esiea.integrationplatform.adapters.kafka;

import com.esiea.integrationplatform.domain.port.out.EventPublisherPort;
import com.esiea.integrationplatform.infrastructure.config.KafkaConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Implémentation du publisher Kafka
 * Activé uniquement si Kafka est configuré
 */
@Component
@ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "true", matchIfMissing = true)
public class KafkaEventPublisher implements EventPublisherPort {

    private static final Logger log = LoggerFactory.getLogger(KafkaEventPublisher.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void publishEvenementCree(Long evenementId, String titre) {
        try {
            EvenementCreeEvent event = new EvenementCreeEvent(
                    evenementId,
                    titre,
                    "Un nouvel événement '" + titre + "' a été créé !"
            );

            String eventJson = objectMapper.writeValueAsString(event);

            kafkaTemplate.send(KafkaConfig.TOPIC_EVENEMENT_CREE, eventJson);

            log.info("Événement Kafka publié : {}", event);

        } catch (JsonProcessingException e) {
            log.error("Erreur lors de la sérialisation de l'événement", e);
        }
    }

    @Override
    public void publishInscriptionCreee(Long inscriptionId, Long etudiantId, Long evenementId, String statut) {
        try {
            String message = statut.equals("CONFIRMEE")
                    ? "Inscription confirmée pour l'événement ID " + evenementId
                    : "Inscription en liste d'attente pour l'événement ID " + evenementId;

            InscriptionCreeEvent event = new InscriptionCreeEvent(
                    inscriptionId,
                    etudiantId,
                    evenementId,
                    statut,
                    message
            );

            String eventJson = objectMapper.writeValueAsString(event);

            kafkaTemplate.send(KafkaConfig.TOPIC_INSCRIPTION_CREEE, eventJson);

            log.info("Événement Kafka publié : {}", event);

        } catch (JsonProcessingException e) {
            log.error("Erreur lors de la sérialisation de l'événement d'inscription", e);
        }
    }
}