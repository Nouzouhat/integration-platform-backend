package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.model.Evenement;
import com.esiea.integrationplatform.domain.service.EvenementValidator;
import com.esiea.integrationplatform.domain.port.in.ModifierEvenementUseCase;
import com.esiea.integrationplatform.domain.port.out.EvenementRepositoryPort;

public class ModifierEvenementUseCaseImpl implements ModifierEvenementUseCase {

    private final EvenementRepositoryPort evenementRepository;
    private final EvenementValidator validator;  // Ajoute le validator

    public ModifierEvenementUseCaseImpl(EvenementRepositoryPort evenementRepository) {
        this.evenementRepository = evenementRepository;
        this.validator = new EvenementValidator();  // Instancie le validator
    }

    @Override
    public Evenement modifier(Long id, Evenement evenementModifie) {
        // Vérifier que l'événement existe
        Evenement evenementExistant = evenementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé avec l'ID : " + id));

        // Valider les nouvelles données (CORRIGÉ : utilise validate, pas valider)
        validator.validate(evenementModifie);

        // Mettre à jour les champs (on garde l'ID)
        evenementModifie.setId(id);

        // Sauvegarder
        return evenementRepository.save(evenementModifie);
    }
}