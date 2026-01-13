package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.model.Inscription;
import com.esiea.integrationplatform.domain.port.in.ConsulterInscriptionsEtudiantUseCase;
import com.esiea.integrationplatform.domain.port.out.InscriptionRepositoryPort;

import java.util.List;

/**
 * Implémentation du cas d'usage de consultation des inscriptions d'un étudiant
 * AUCUNE ANNOTATION SPRING dans le use case
 */
public class ConsulterInscriptionsEtudiantUseCaseImpl implements ConsulterInscriptionsEtudiantUseCase {

    private final InscriptionRepositoryPort inscriptionRepository;

    public ConsulterInscriptionsEtudiantUseCaseImpl(InscriptionRepositoryPort inscriptionRepository) {
        this.inscriptionRepository = inscriptionRepository;
    }

    @Override
    public List<Inscription> execute(Long etudiantId) {
        if (etudiantId == null || etudiantId <= 0) {
            throw new IllegalArgumentException("L'ID de l'étudiant doit être valide");
        }

        return inscriptionRepository.findByEtudiantId(etudiantId);
    }
}
