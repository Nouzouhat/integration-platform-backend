package com.esiea.integrationplatform.infrastructure.config;

import com.esiea.integrationplatform.domain.port.in.*;
import com.esiea.integrationplatform.domain.port.out.EvenementRepositoryPort;
import com.esiea.integrationplatform.domain.port.out.EventPublisherPort;
import com.esiea.integrationplatform.domain.port.out.InscriptionRepositoryPort;
import com.esiea.integrationplatform.domain.port.out.PasswordEncoderPort;
import com.esiea.integrationplatform.domain.port.out.UserRepositoryPort;
import com.esiea.integrationplatform.usecase.*;
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

    // ========== Use Cases Inscription ==========

    @Bean
    public InscrireEtudiantUseCase inscrireEtudiantUseCase(
            InscriptionRepositoryPort inscriptionRepository,
            EvenementRepositoryPort evenementRepository) {
        return new InscrireEtudiantUseCaseImpl(inscriptionRepository, evenementRepository);
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

    @Bean
    public ConsulterInscriptionsEtudiantUseCase consulterInscriptionsEtudiantUseCase(
            InscriptionRepositoryPort inscriptionRepository) {
        return new ConsulterInscriptionsEtudiantUseCaseImpl(inscriptionRepository);
    }

    // ========== Use Cases User ==========

    @Bean
    public CreerCompteUseCase creerCompteUseCase(
            UserRepositoryPort userRepository,
            PasswordEncoderPort passwordEncoder) {
        return new CreerCompteUseCaseImpl(userRepository, passwordEncoder);
    }

    @Bean
    public SeConnecterUseCase seConnecterUseCase(
            UserRepositoryPort userRepository,
            PasswordEncoderPort passwordEncoder) {
        return new SeConnecterUseCaseImpl(userRepository, passwordEncoder);
    }

    @Bean
    public ObtenirProfilUseCase obtenirProfilUseCase(UserRepositoryPort userRepository) {
        return new ObtenirProfilUseCaseImpl(userRepository);
    }

    @Bean
    public ModifierProfilUseCase modifierProfilUseCase(UserRepositoryPort userRepository) {
        return new ModifierProfilUseCaseImpl(userRepository);
    }

}