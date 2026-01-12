package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.exception.EvenementNotFoundException;
import com.esiea.integrationplatform.domain.port.in.SupprimerEvenementUseCase;
import com.esiea.integrationplatform.domain.port.out.EvenementRepositoryPort;

/**
 * Implémentation du use case pour supprimer un événement
 */
public class SupprimerEvenementUseCaseImpl implements SupprimerEvenementUseCase {

    private final EvenementRepositoryPort evenementRepositoryPort;

    public SupprimerEvenementUseCaseImpl(EvenementRepositoryPort evenementRepositoryPort) {
        this.evenementRepositoryPort = evenementRepositoryPort;
    }

    @Override
    public void supprimer(Long id) {
        // Vérifier que l'événement existe avant de supprimer
        if (!evenementRepositoryPort.findById(id).isPresent()) {
            throw new EvenementNotFoundException(id);
        }

        evenementRepositoryPort.deleteById(id);
    }
}