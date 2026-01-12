package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.exception.EvenementAlreadyPublishedException;
import com.esiea.integrationplatform.domain.exception.EvenementNotFoundException;
import com.esiea.integrationplatform.domain.model.Evenement;
import com.esiea.integrationplatform.domain.port.in.PublierEvenementUseCase;
import com.esiea.integrationplatform.domain.port.out.EvenementRepositoryPort;
import com.esiea.integrationplatform.domain.port.out.EventPublisherPort;

/**
 * Implémentation du use case pour publier un événement
 */
public class PublierEvenementUseCaseImpl implements PublierEvenementUseCase {

    private final EvenementRepositoryPort evenementRepositoryPort;
    private final EventPublisherPort eventPublisher;

    public PublierEvenementUseCaseImpl(
            EvenementRepositoryPort evenementRepositoryPort,
            EventPublisherPort eventPublisher) {
        this.evenementRepositoryPort = evenementRepositoryPort;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Evenement publier(Long id) {
        // Récupérer l'événement
        Evenement evenement = evenementRepositoryPort.findById(id)
                .orElseThrow(() -> new EvenementNotFoundException(id));

        // Vérifier s'il n'est pas déjà publié
        if ("PUBLIE".equals(evenement.getStatut())) {
            throw new EvenementAlreadyPublishedException(id);
        }

        // Changer le statut
        evenement.setStatut("PUBLIE");

        // Sauvegarder
        Evenement evenementPublie = evenementRepositoryPort.save(evenement);

        // Publier l'événement Kafka
        eventPublisher.publishEvenementCree(evenementPublie.getId(), evenementPublie.getTitre());

        return evenementPublie;
    }
}