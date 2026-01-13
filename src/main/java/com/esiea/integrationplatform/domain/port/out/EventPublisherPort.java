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

    /**
     * Publie un événement Kafka quand une inscription est créée
     * @param inscriptionId l'ID de l'inscription créée
     * @param etudiantId l'ID de l'étudiant inscrit
     * @param evenementId l'ID de l'événement
     * @param statut le statut de l'inscription (CONFIRMEE, EN_ATTENTE)
     */
    void publishInscriptionCreee(Long inscriptionId, Long etudiantId, Long evenementId, String statut);
}