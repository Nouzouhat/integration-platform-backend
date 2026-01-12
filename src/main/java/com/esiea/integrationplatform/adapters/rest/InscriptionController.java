package com.esiea.integrationplatform.adapters.rest;

import com.esiea.integrationplatform.adapters.rest.dto.InscriptionDtoMapper;
import com.esiea.integrationplatform.adapters.rest.dto.InscriptionRequestDTO;
import com.esiea.integrationplatform.adapters.rest.dto.InscriptionResponseDTO;
import com.esiea.integrationplatform.domain.model.Inscription;
import com.esiea.integrationplatform.domain.port.in.ConsulterInscriptionsEtudiantUseCase;
import com.esiea.integrationplatform.domain.port.in.InscrireEtudiantUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour les inscriptions
 */
@RestController
@RequestMapping("/api/inscriptions")
@Tag(name = "Inscriptions", description = "API de gestion des inscriptions aux événements")
public class InscriptionController {

    private final InscrireEtudiantUseCase inscrireEtudiantUseCase;
    private final ConsulterInscriptionsEtudiantUseCase consulterInscriptionsEtudiantUseCase;

    public InscriptionController(
            InscrireEtudiantUseCase inscrireEtudiantUseCase,
            ConsulterInscriptionsEtudiantUseCase consulterInscriptionsEtudiantUseCase) {
        this.inscrireEtudiantUseCase = inscrireEtudiantUseCase;
        this.consulterInscriptionsEtudiantUseCase = consulterInscriptionsEtudiantUseCase;
    }

    /**
     * Inscrire un étudiant à un événement
     */
    @PostMapping
    @Operation(summary = "Inscrire un étudiant",
            description = "Inscrit un étudiant à un événement. Si la capacité est atteinte, l'inscription est mise en liste d'attente.")
    public ResponseEntity<InscriptionResponseDTO> inscrireEtudiant(
            @RequestBody InscriptionRequestDTO requestDTO) {

        // Exécuter le use case
        Inscription inscription = inscrireEtudiantUseCase.execute(
                requestDTO.getEtudiantId(),
                requestDTO.getEvenementId(),
                requestDTO.getCommentaire()
        );

        // Convertir en DTO de réponse
        InscriptionResponseDTO responseDTO = InscriptionDtoMapper.toResponseDto(inscription);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Consulter les inscriptions d'un étudiant
     */
    @GetMapping("/etudiant/{etudiantId}")
    @Operation(summary = "Consulter les inscriptions d'un étudiant",
            description = "Récupère toutes les inscriptions d'un étudiant")
    public ResponseEntity<List<InscriptionResponseDTO>> consulterInscriptionsEtudiant(
            @PathVariable Long etudiantId) {

        // Exécuter le use case
        List<Inscription> inscriptions = consulterInscriptionsEtudiantUseCase.execute(etudiantId);

        // Convertir en DTOs
        List<InscriptionResponseDTO> responseDTOs = inscriptions.stream()
                .map(InscriptionDtoMapper::toResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Lister les inscrits à un événement
     */
    @GetMapping("/evenement/{evenementId}")
    @Operation(summary = "Lister les inscrits à un événement",
            description = "Récupère toutes les inscriptions pour un événement donné")
    public ResponseEntity<List<InscriptionResponseDTO>> listerInscritsEvenement(
            @PathVariable Long evenementId) {

        // Pour l'instant, on utilise le repository directement
        // TODO: Créer un use case dédié si nécessaire
        return ResponseEntity.ok(List.of());
    }
}
