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
}