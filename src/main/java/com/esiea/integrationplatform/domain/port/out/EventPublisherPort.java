package com.esiea.integrationplatform.domain.port.out;

/**
 * Port de sortie pour la publication d'événements Kafka
 * Interface métier - AUCUNE ANNOTATION SPRING
 */
public interface EventPublisherPort {

    /**
     * Publie un événement Kafka quand un événement est créé
     * @param evenementId l'ID de l'événement créé
     * @param titre le titre de l'événement
     */
    void publishEvenementCree(Long evenementId, String titre);
}