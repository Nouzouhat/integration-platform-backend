package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.exception.InscriptionNotFoundException;
import com.esiea.integrationplatform.domain.model.Inscription;
import com.esiea.integrationplatform.domain.port.in.AnnulerInscriptionUseCase;
import com.esiea.integrationplatform.domain.port.out.InscriptionRepositoryPort;

/**
 * Implémentation du cas d'usage d'annulation d'inscription
 * AUCUNE ANNOTATION SPRING dans le use case
 */
public class AnnulerInscriptionUseCaseImpl implements AnnulerInscriptionUseCase {

    private final InscriptionRepositoryPort inscriptionRepository;

    public AnnulerInscriptionUseCaseImpl(InscriptionRepositoryPort inscriptionRepository) {
        this.inscriptionRepository = inscriptionRepository;
    }

    @Override
    public void execute(Long id) {
        // 1. Vérifier que l'inscription existe
        Inscription inscription = inscriptionRepository.findById(id)
                .orElseThrow(() -> new InscriptionNotFoundException(id));

        // 2. Supprimer l'inscription
        inscriptionRepository.deleteById(id);

        // Note: Dans une version plus avancée, on pourrait :
        // - Publier un événement "InscriptionAnnulee" via Kafka
        // - Promouvoir une personne en liste d'attente si applicable
        // - Envoyer une notification à l'étudiant
    }
}
