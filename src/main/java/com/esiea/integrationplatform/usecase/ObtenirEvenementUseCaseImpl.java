package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.exception.EvenementNotFoundException;
import com.esiea.integrationplatform.domain.model.Evenement;
import com.esiea.integrationplatform.domain.port.in.ObtenirEvenementUseCase;
import com.esiea.integrationplatform.domain.port.out.EvenementRepositoryPort;

/**
 * Implémentation du use case pour obtenir un événement par son ID
 */
public class ObtenirEvenementUseCaseImpl implements ObtenirEvenementUseCase {

    private final EvenementRepositoryPort evenementRepositoryPort;

    public ObtenirEvenementUseCaseImpl(EvenementRepositoryPort evenementRepositoryPort) {
        this.evenementRepositoryPort = evenementRepositoryPort;
    }

    @Override
    public Evenement obtenirParId(Long id) {
        return evenementRepositoryPort.findById(id)
                .orElseThrow(() -> new EvenementNotFoundException(id));
    }
}