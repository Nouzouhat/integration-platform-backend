package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.exception.InscriptionNotFoundException;
import com.esiea.integrationplatform.domain.model.Inscription;
import com.esiea.integrationplatform.domain.port.in.RecupererInscriptionUseCase;
import com.esiea.integrationplatform.domain.port.out.InscriptionRepositoryPort;

/**
 * Implémentation du cas d'usage de récupération d'une inscription
 * AUCUNE ANNOTATION SPRING dans le use case
 */
public class RecupererInscriptionUseCaseImpl implements RecupererInscriptionUseCase {

    private final InscriptionRepositoryPort inscriptionRepository;

    public RecupererInscriptionUseCaseImpl(InscriptionRepositoryPort inscriptionRepository) {
        this.inscriptionRepository = inscriptionRepository;
    }

    @Override
    public Inscription execute(Long id) {
        return inscriptionRepository.findById(id)
                .orElseThrow(() -> new InscriptionNotFoundException(id));
    }
}
