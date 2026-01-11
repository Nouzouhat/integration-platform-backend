package com.esiea.integrationplatform.adapters.kafka;

import com.esiea.integrationplatform.domain.port.in.TraiterEvenementCreeUseCase;
import com.esiea.integrationplatform.infrastructure.config.KafkaConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Listener Kafka qui écoute les événements
 * IMPORTANT : Le listener ne contient AUCUNE logique métier
 * Il délègue tout au use case
 */
@Component
public class KafkaEventListener {

    private static final Logger log = LoggerFactory.getLogger(KafkaEventListener.class);

    private final TraiterEvenementCreeUseCase traiterEvenementCreeUseCase;
    private final ObjectMapper objectMapper;

    public KafkaEventListener(TraiterEvenementCreeUseCase traiterEvenementCreeUseCase) {
        this.traiterEvenementCreeUseCase = traiterEvenementCreeUseCase;
        this.objectMapper = new ObjectMapper();
    }

    @KafkaListener(topics = KafkaConfig.TOPIC_EVENEMENT_CREE, groupId = "integration-platform-group")
    public void listen(String message) {
        try {
            log.info("📨 Message Kafka reçu : {}", message);

            // Désérialiser le message
            EvenementCreeEvent event = objectMapper.readValue(message, EvenementCreeEvent.class);

            // Déléguer au use case (AUCUNE LOGIQUE ICI)
            traiterEvenementCreeUseCase.execute(event.getEvenementId(), event.getTitre());

        } catch (Exception e) {
            log.error("❌ Erreur lors du traitement du message Kafka", e);
        }
    }
}