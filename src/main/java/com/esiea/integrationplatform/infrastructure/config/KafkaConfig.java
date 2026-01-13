package com.esiea.integrationplatform.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Configuration Kafka - Création des topics
 * Activé uniquement si Kafka est configuré
 */
@Configuration
@ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "true", matchIfMissing = true)
public class KafkaConfig {

    public static final String TOPIC_EVENEMENT_CREE = "evenement-cree";

    @Bean
    public NewTopic evenementCreeTopic() {
        return TopicBuilder.name(TOPIC_EVENEMENT_CREE)
                .partitions(1)
                .replicas(1)
                .build();
    }
}