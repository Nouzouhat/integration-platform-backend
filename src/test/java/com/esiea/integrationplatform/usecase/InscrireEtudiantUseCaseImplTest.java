package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.exception.EvenementNotFoundException;
import com.esiea.integrationplatform.domain.model.Evenement;
import com.esiea.integrationplatform.domain.model.Inscription;
import com.esiea.integrationplatform.domain.port.out.EvenementRepositoryPort;
import com.esiea.integrationplatform.domain.port.out.InscriptionRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitaires - InscrireEtudiantUseCase")
class InscrireEtudiantUseCaseImplTest {

    @Mock
    private InscriptionRepositoryPort inscriptionRepository;

    @Mock
    private EvenementRepositoryPort evenementRepository;

    private InscrireEtudiantUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new InscrireEtudiantUseCaseImpl(inscriptionRepository, evenementRepository);
    }

    @Test
    @DisplayName("Devrait créer une inscription confirmée quand l'événement a de la place")
    void shouldCreateConfirmedInscriptionWhenEventHasCapacity() {
        // Given
        Long etudiantId = 1L;
        Long evenementId = 1L;
        String commentaire = "Je souhaite participer";
        
        Evenement evenement = createEvenement(evenementId, 50);
        when(evenementRepository.findById(evenementId)).thenReturn(Optional.of(evenement));
        when(inscriptionRepository.existsByEtudiantIdAndEvenementId(etudiantId, evenementId)).thenReturn(false);
        when(inscriptionRepository.countByEvenementIdAndStatut(evenementId, "CONFIRMEE")).thenReturn(10L);
        when(inscriptionRepository.save(any(Inscription.class))).thenAnswer(invocation -> {
            Inscription saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        // When
        Inscription result = useCase.execute(etudiantId, evenementId, commentaire);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getEtudiantId()).isEqualTo(etudiantId);
        assertThat(result.getEvenementId()).isEqualTo(evenementId);
        assertThat(result.getStatut()).isEqualTo("CONFIRMEE");
        assertThat(result.getCommentaire()).isEqualTo(commentaire);
        assertThat(result.getDateInscription()).isNotNull();
        
        verify(evenementRepository).findById(evenementId);
        verify(inscriptionRepository).existsByEtudiantIdAndEvenementId(etudiantId, evenementId);
        verify(inscriptionRepository).save(any(Inscription.class));
    }

    @Test
    @DisplayName("Devrait créer une inscription en attente quand l'événement est complet")
    void shouldCreateWaitingInscriptionWhenEventIsFull() {
        // Given
        Long etudiantId = 1L;
        Long evenementId = 1L;
        
        Evenement evenement = createEvenement(evenementId, 50);
        when(evenementRepository.findById(evenementId)).thenReturn(Optional.of(evenement));
        when(inscriptionRepository.existsByEtudiantIdAndEvenementId(etudiantId, evenementId)).thenReturn(false);
        when(inscriptionRepository.countByEvenementIdAndStatut(evenementId, "CONFIRMEE")).thenReturn(50L);
        when(inscriptionRepository.save(any(Inscription.class))).thenAnswer(invocation -> {
            Inscription saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        // When
        Inscription result = useCase.execute(etudiantId, evenementId, null);

        // Then
        assertThat(result.getStatut()).isEqualTo("EN_ATTENTE");
        verify(inscriptionRepository).countByEvenementIdAndStatut(evenementId, "CONFIRMEE");
    }

    @Test
    @DisplayName("Devrait créer une inscription confirmée si pas de capacité max définie")
    void shouldCreateConfirmedInscriptionWhenNoMaxCapacity() {
        // Given
        Long etudiantId = 1L;
        Long evenementId = 1L;
        
        Evenement evenement = createEvenement(evenementId, null); // Pas de capacité max
        when(evenementRepository.findById(evenementId)).thenReturn(Optional.of(evenement));
        when(inscriptionRepository.existsByEtudiantIdAndEvenementId(etudiantId, evenementId)).thenReturn(false);
        when(inscriptionRepository.save(any(Inscription.class))).thenAnswer(invocation -> {
            Inscription saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        // When
        Inscription result = useCase.execute(etudiantId, evenementId, null);

        // Then
        assertThat(result.getStatut()).isEqualTo("CONFIRMEE");
        verify(inscriptionRepository, never()).countByEvenementIdAndStatut(any(), any());
    }

    @Test
    @DisplayName("Devrait lever une exception si l'événement n'existe pas")
    void shouldThrowExceptionWhenEventNotFound() {
        // Given
        Long etudiantId = 1L;
        Long evenementId = 999L;
        when(evenementRepository.findById(evenementId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> useCase.execute(etudiantId, evenementId, null))
                .isInstanceOf(EvenementNotFoundException.class);
        
        verify(evenementRepository).findById(evenementId);
        verify(inscriptionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Devrait lever une exception si l'étudiant est déjà inscrit")
    void shouldThrowExceptionWhenStudentAlreadyRegistered() {
        // Given
        Long etudiantId = 1L;
        Long evenementId = 1L;
        
        Evenement evenement = createEvenement(evenementId, 50);
        when(evenementRepository.findById(evenementId)).thenReturn(Optional.of(evenement));
        when(inscriptionRepository.existsByEtudiantIdAndEvenementId(etudiantId, evenementId)).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> useCase.execute(etudiantId, evenementId, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("déjà inscrit");
        
        verify(inscriptionRepository, never()).save(any());
    }

    private Evenement createEvenement(Long id, Integer capaciteMax) {
        Evenement evenement = new Evenement();
        evenement.setId(id);
        evenement.setTitre("Événement Test");
        evenement.setDescription("Description");
        evenement.setDateDebut(LocalDateTime.now().plusDays(1));
        evenement.setDateFin(LocalDateTime.now().plusDays(2));
        evenement.setLieu("Salle A");
        evenement.setCapaciteMax(capaciteMax);
        return evenement;
    }
}
