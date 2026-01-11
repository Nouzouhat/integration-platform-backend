package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.model.Evenement;
import com.esiea.integrationplatform.domain.port.in.CreerEvenementUseCase;
import com.esiea.integrationplatform.domain.port.out.EvenementRepositoryPort;
import com.esiea.integrationplatform.domain.port.out.EventPublisherPort;
import com.esiea.integrationplatform.domain.service.EvenementValidator;

/**
 * Implémentation du cas d'usage de création d'événement
 * AUCUNE ANNOTATION SPRING dans le use case
 */
public class CreerEvenementUseCaseImpl implements CreerEvenementUseCase {

    private final EvenementRepositoryPort evenementRepository;
    private final EventPublisherPort eventPublisher;
    private final EvenementValidator validator;

    public CreerEvenementUseCaseImpl(
            EvenementRepositoryPort evenementRepository,
            EventPublisherPort eventPublisher) {
        this.evenementRepository = evenementRepository;
        this.eventPublisher = eventPublisher;
        this.validator = new EvenementValidator();
    }

    @Override
    public Evenement execute(Evenement evenement) {
        // 1. Valider l'événement
        validator.validate(evenement);

        // 2. Définir un statut par défaut si absent
        if (evenement.getStatut() == null || evenement.getStatut().isEmpty()) {
            evenement.setStatut("BROUILLON");
        }

        // 3. Sauvegarder l'événement
        Evenement evenementCree = evenementRepository.save(evenement);

        // 4. Publier un événement Kafka si l'événement est publié
        if ("PUBLIE".equalsIgnoreCase(evenementCree.getStatut())) {
            eventPublisher.publishEvenementCree(
                    evenementCree.getId(),
                    evenementCree.getTitre()
            );
        }

        return evenementCree;
    }
}