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

    /**
     * Publie un événement Kafka quand un utilisateur effectue une action
     * @param userId l'ID de l'utilisateur
     * @param action l'action effectuée (CREATION, MODIFICATION, SUPPRESSION, CONNEXION, etc.)
     * @param details détails supplémentaires sur l'action
     */
    void publishUserAction(Long userId, String action, String details);

    /**
     * Publie un événement Kafka quand le statut d'un événement change
     * @param evenementId l'ID de l'événement
     * @param ancienStatut l'ancien statut
     * @param nouveauStatut le nouveau statut
     * @param titre le titre de l'événement
     */
    void publishEventStatusChange(Long evenementId, String ancienStatut, String nouveauStatut, String titre);
}