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
    public static final String TOPIC_INSCRIPTION_CREEE = "inscription-creee";
    public static final String TOPIC_USER_ACTION = "user-action";
    public static final String TOPIC_EVENT_STATUS_CHANGE = "event-status-change";


    @Bean
    public NewTopic evenementCreeTopic() {
        return TopicBuilder.name(TOPIC_EVENEMENT_CREE)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic inscriptionCreeeTopic() {
        return TopicBuilder.name(TOPIC_INSCRIPTION_CREEE)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic userActionTopic() {
        return TopicBuilder.name(TOPIC_USER_ACTION)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic eventStatusChangeTopic() {
        return TopicBuilder.name(TOPIC_EVENT_STATUS_CHANGE)
                .partitions(1)
                .replicas(1)
                .build();
    }
}