package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.model.Evenement;
import com.esiea.integrationplatform.domain.port.in.ListerEvenementsUseCase;
import com.esiea.integrationplatform.domain.port.out.EvenementRepositoryPort;

import java.util.List;

/**
 * Implémentation du cas d'usage de listage des événements
 * AUCUNE ANNOTATION SPRING dans le use case
 */
public class ListerEvenementsUseCaseImpl implements ListerEvenementsUseCase {

    private final EvenementRepositoryPort evenementRepository;

    public ListerEvenementsUseCaseImpl(EvenementRepositoryPort evenementRepository) {
        this.evenementRepository = evenementRepository;
    }

    @Override
    public List<Evenement> execute() {
        return evenementRepository.findAll();
    }
}