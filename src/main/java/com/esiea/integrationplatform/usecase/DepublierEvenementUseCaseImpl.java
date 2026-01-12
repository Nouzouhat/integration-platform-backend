package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.exception.EvenementNotFoundException;
import com.esiea.integrationplatform.domain.exception.EvenementNotPublishedException;
import com.esiea.integrationplatform.domain.model.Evenement;
import com.esiea.integrationplatform.domain.port.in.DepublierEvenementUseCase;
import com.esiea.integrationplatform.domain.port.out.EvenementRepositoryPort;

/**
 * Implémentation du use case pour dépublier un événement
 */
public class DepublierEvenementUseCaseImpl implements DepublierEvenementUseCase {

    private final EvenementRepositoryPort evenementRepositoryPort;

    public DepublierEvenementUseCaseImpl(EvenementRepositoryPort evenementRepositoryPort) {
        this.evenementRepositoryPort = evenementRepositoryPort;
    }

    @Override
    public Evenement depublier(Long id) {
        // Récupérer l'événement
        Evenement evenement = evenementRepositoryPort.findById(id)
                .orElseThrow(() -> new EvenementNotFoundException(id));

        // Vérifier s'il est publié
        if (!"PUBLIE".equals(evenement.getStatut())) {
            throw new EvenementNotPublishedException(id);
        }

        // Changer le statut
        evenement.setStatut("BROUILLON");

        // Sauvegarder
        return evenementRepositoryPort.save(evenement);
    }
}