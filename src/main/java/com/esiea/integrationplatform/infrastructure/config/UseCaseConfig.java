package com.esiea.integrationplatform.infrastructure.config;

import com.esiea.integrationplatform.domain.port.in.AnnulerInscriptionUseCase;
import com.esiea.integrationplatform.domain.port.in.ConsulterInscriptionsEtudiantUseCase;
import com.esiea.integrationplatform.domain.port.in.CreerEvenementUseCase;
import com.esiea.integrationplatform.domain.port.in.InscrireEtudiantUseCase;
import com.esiea.integrationplatform.domain.port.in.ListerEvenementsUseCase;
import com.esiea.integrationplatform.domain.port.in.ModifierInscriptionUseCase;
import com.esiea.integrationplatform.domain.port.in.RecupererInscriptionUseCase;
import com.esiea.integrationplatform.domain.port.in.TraiterEvenementCreeUseCase;
import com.esiea.integrationplatform.domain.port.out.EvenementRepositoryPort;
import com.esiea.integrationplatform.domain.port.out.EventPublisherPort;
import com.esiea.integrationplatform.domain.port.out.InscriptionRepositoryPort;
import com.esiea.integrationplatform.usecase.AnnulerInscriptionUseCaseImpl;
import com.esiea.integrationplatform.usecase.ConsulterInscriptionsEtudiantUseCaseImpl;
import com.esiea.integrationplatform.usecase.CreerEvenementUseCaseImpl;
import com.esiea.integrationplatform.usecase.InscrireEtudiantUseCaseImpl;
import com.esiea.integrationplatform.usecase.ListerEvenementsUseCaseImpl;
import com.esiea.integrationplatform.usecase.ModifierInscriptionUseCaseImpl;
import com.esiea.integrationplatform.usecase.RecupererInscriptionUseCaseImpl;
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

    @Bean
    public InscrireEtudiantUseCase inscrireEtudiantUseCase(
            InscriptionRepositoryPort inscriptionRepository,
            EvenementRepositoryPort evenementRepository) {
        return new InscrireEtudiantUseCaseImpl(inscriptionRepository, evenementRepository);
    }

    @Bean
    public ConsulterInscriptionsEtudiantUseCase consulterInscriptionsEtudiantUseCase(
            InscriptionRepositoryPort inscriptionRepository) {
        return new ConsulterInscriptionsEtudiantUseCaseImpl(inscriptionRepository);
    }

    @Bean
    public RecupererInscriptionUseCase recupererInscriptionUseCase(
            InscriptionRepositoryPort inscriptionRepository) {
        return new RecupererInscriptionUseCaseImpl(inscriptionRepository);
    }

    @Bean
    public ModifierInscriptionUseCase modifierInscriptionUseCase(
            InscriptionRepositoryPort inscriptionRepository) {
        return new ModifierInscriptionUseCaseImpl(inscriptionRepository);
    }

    @Bean
    public AnnulerInscriptionUseCase annulerInscriptionUseCase(
            InscriptionRepositoryPort inscriptionRepository) {
        return new AnnulerInscriptionUseCaseImpl(inscriptionRepository);
    }
}