package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.port.in.TraiterEvenementCreeUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use case pour traiter les événements Kafka reçus
 * Ici on pourrait envoyer des notifications, mettre à jour des stats, etc.
 */
public class TraiterEvenementCreeUseCaseImpl implements TraiterEvenementCreeUseCase {

    private static final Logger log = LoggerFactory.getLogger(TraiterEvenementCreeUseCaseImpl.class);

    @Override
    public void execute(Long evenementId, String titre) {
        log.info("🎉 Traitement de l'événement créé - ID: {}, Titre: {}", evenementId, titre);

        // Ici tu pourrais ajouter de la logique métier comme :
        // - Envoyer une notification par email
        // - Mettre à jour des statistiques
        // - Déclencher un workflow

        log.info("✅ Événement traité avec succès !");
    }
}