package com.esiea.integrationplatform.infrastructure.config;

import com.esiea.integrationplatform.domain.port.in.CreerEvenementUseCase;
import com.esiea.integrationplatform.domain.port.in.ListerEvenementsUseCase;
import com.esiea.integrationplatform.domain.port.in.TraiterEvenementCreeUseCase;
import com.esiea.integrationplatform.domain.port.out.EvenementRepositoryPort;
import com.esiea.integrationplatform.domain.port.out.EventPublisherPort;
import com.esiea.integrationplatform.usecase.CreerEvenementUseCaseImpl;
import com.esiea.integrationplatform.usecase.ListerEvenementsUseCaseImpl;
import com.esiea.integrationplatform.usecase.TraiterEvenementCreeUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.esiea.integrationplatform.domain.port.in.*;
import com.esiea.integrationplatform.usecase.*;

@Configuration
public class UseCaseConfig {

    @Bean
    public CreerEvenementUseCase creerEvenementUseCase(
            EvenementRepositoryPort evenementRepository,
            EventPublisherPort eventPublisher) {
        return new CreerEvenementUseCaseImpl(evenementRepository, eventPublisher);
    }

    @Bean
    public ListerEvenementsUseCase listerEvenementsUseCase(
            EvenementRepositoryPort evenementRepository) {
        return new ListerEvenementsUseCaseImpl(evenementRepository);
    }

    @Bean
    public TraiterEvenementCreeUseCase traiterEvenementCreeUseCase() {
        return new TraiterEvenementCreeUseCaseImpl();
    }


    @Bean
    public ObtenirEvenementUseCase obtenirEvenementUseCase(EvenementRepositoryPort evenementRepository) {
        return new ObtenirEvenementUseCaseImpl(evenementRepository);
    }

    @Bean
    public ModifierEvenementUseCase modifierEvenementUseCase(EvenementRepositoryPort evenementRepository) {
        return new ModifierEvenementUseCaseImpl(evenementRepository);
    }

    @Bean
    public SupprimerEvenementUseCase supprimerEvenementUseCase(EvenementRepositoryPort evenementRepository) {
        return new SupprimerEvenementUseCaseImpl(evenementRepository);
    }

    @Bean
    public PublierEvenementUseCase publierEvenementUseCase(
            EvenementRepositoryPort evenementRepository,
            EventPublisherPort eventPublisher) {
        return new PublierEvenementUseCaseImpl(evenementRepository, eventPublisher);
    }

    @Bean
    public DepublierEvenementUseCase depublierEvenementUseCase(EvenementRepositoryPort evenementRepository) {
        return new DepublierEvenementUseCaseImpl(evenementRepository);
    }

    @Bean
    public ExporterEvenementsCSVUseCase exporterEvenementsCSVUseCase(EvenementRepositoryPort evenementRepository) {
        return new ExporterEvenementsCSVUseCaseImpl(evenementRepository);
    }


}