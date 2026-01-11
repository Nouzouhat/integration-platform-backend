package com.esiea.integrationplatform.domain.port.in;

/**
 * Port d'entrée pour le cas d'usage de traitement d'événement créé
 */
public interface TraiterEvenementCreeUseCase {

    /**
     * Traite un événement créé (reçu via Kafka)
     * @param evenementId l'ID de l'événement
     * @param titre le titre de l'événement
     */
    void execute(Long evenementId, String titre);
}