package com.esiea.integrationplatform.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Configuration Kafka - Création des topics
 */
@Configuration
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