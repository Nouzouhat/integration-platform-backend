package com.esiea.integrationplatform.adapters.rest;

import com.esiea.integrationplatform.adapters.rest.dto.InscriptionDtoMapper;
import com.esiea.integrationplatform.adapters.rest.dto.InscriptionRequestDTO;
import com.esiea.integrationplatform.adapters.rest.dto.InscriptionResponseDTO;
import com.esiea.integrationplatform.adapters.rest.dto.ModifierInscriptionRequestDTO;
import com.esiea.integrationplatform.domain.model.Inscription;
import com.esiea.integrationplatform.domain.port.in.AnnulerInscriptionUseCase;
import com.esiea.integrationplatform.domain.port.in.ConsulterInscriptionsEtudiantUseCase;
import com.esiea.integrationplatform.domain.port.in.InscrireEtudiantUseCase;
import com.esiea.integrationplatform.domain.port.in.ModifierInscriptionUseCase;
import com.esiea.integrationplatform.domain.port.in.RecupererInscriptionUseCase;
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
@RequestMapping("/inscriptions")
@Tag(name = "Inscriptions", description = "API de gestion des inscriptions aux événements")
public class InscriptionController {

    private final InscrireEtudiantUseCase inscrireEtudiantUseCase;
    private final ConsulterInscriptionsEtudiantUseCase consulterInscriptionsEtudiantUseCase;
    private final RecupererInscriptionUseCase recupererInscriptionUseCase;
    private final ModifierInscriptionUseCase modifierInscriptionUseCase;
    private final AnnulerInscriptionUseCase annulerInscriptionUseCase;

    public InscriptionController(
            InscrireEtudiantUseCase inscrireEtudiantUseCase,
            ConsulterInscriptionsEtudiantUseCase consulterInscriptionsEtudiantUseCase,
            RecupererInscriptionUseCase recupererInscriptionUseCase,
            ModifierInscriptionUseCase modifierInscriptionUseCase,
            AnnulerInscriptionUseCase annulerInscriptionUseCase) {
        this.inscrireEtudiantUseCase = inscrireEtudiantUseCase;
        this.consulterInscriptionsEtudiantUseCase = consulterInscriptionsEtudiantUseCase;
        this.recupererInscriptionUseCase = recupererInscriptionUseCase;
        this.modifierInscriptionUseCase = modifierInscriptionUseCase;
        this.annulerInscriptionUseCase = annulerInscriptionUseCase;
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
     * Récupérer les inscriptions par étudiant
     */
    @GetMapping(params = "etudiantId")
    @Operation(summary = "Récupérer les inscriptions d'un étudiant",
            description = "Récupère toutes les inscriptions d'un étudiant via query parameter")
    public ResponseEntity<List<InscriptionResponseDTO>> getInscriptionsByEtudiant(
            @RequestParam Long etudiantId) {

        // Exécuter le use case
        List<Inscription> inscriptions = consulterInscriptionsEtudiantUseCase.execute(etudiantId);

        // Convertir en DTOs
        List<InscriptionResponseDTO> responseDTOs = inscriptions.stream()
                .map(InscriptionDtoMapper::toResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDTOs);
    }


    /**
     * Récupérer une inscription par son ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une inscription",
            description = "Récupère une inscription par son identifiant")
    public ResponseEntity<InscriptionResponseDTO> recupererInscription(
            @PathVariable Long id) {

        // Exécuter le use case
        Inscription inscription = recupererInscriptionUseCase.execute(id);

        // Convertir en DTO
        InscriptionResponseDTO responseDTO = InscriptionDtoMapper.toResponseDto(inscription);

        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Modifier une inscription (remplacement complet)
     */
    @PutMapping("/{id}")
    @Operation(summary = "Modifier une inscription (PUT)",
            description = "Remplace complètement une inscription existante. Tous les champs doivent être fournis.")
    public ResponseEntity<InscriptionResponseDTO> modifierInscription(
            @PathVariable Long id,
            @RequestBody ModifierInscriptionRequestDTO requestDTO) {

        // Exécuter le use case
        Inscription inscription = modifierInscriptionUseCase.execute(
                id,
                requestDTO.getStatut(),
                requestDTO.getCommentaire()
        );

        // Convertir en DTO
        InscriptionResponseDTO responseDTO = InscriptionDtoMapper.toResponseDto(inscription);

        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Modifier partiellement une inscription
     */
    @PatchMapping("/{id}")
    @Operation(summary = "Modifier partiellement une inscription (PATCH)",
            description = "Modifie uniquement les champs fournis d'une inscription. Les champs non fournis restent inchangés.")
    public ResponseEntity<InscriptionResponseDTO> modifierPartiellementInscription(
            @PathVariable Long id,
            @RequestBody ModifierInscriptionRequestDTO requestDTO) {

        // Exécuter le use case (même logique que PUT pour l'instant)
        // Le use case gère déjà les modifications partielles (champs null = pas de modification)
        Inscription inscription = modifierInscriptionUseCase.execute(
                id,
                requestDTO.getStatut(),
                requestDTO.getCommentaire()
        );

        // Convertir en DTO
        InscriptionResponseDTO responseDTO = InscriptionDtoMapper.toResponseDto(inscription);

        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Annuler une inscription
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Annuler une inscription",
            description = "Annule/supprime une inscription existante")
    public ResponseEntity<Void> annulerInscription(@PathVariable Long id) {

        // Exécuter le use case
        annulerInscriptionUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Récupérer les inscriptions par événement
     */
    @GetMapping(params = "evenementId")
    @Operation(summary = "Récupérer les inscriptions d'un événement",
            description = "Récupère toutes les inscriptions pour un événement donné via query parameter")
    public ResponseEntity<List<InscriptionResponseDTO>> getInscriptionsByEvenement(
            @RequestParam Long evenementId) {

        // Pour l'instant, on utilise le repository directement
        // TODO: Créer un use case dédié si nécessaire
        return ResponseEntity.ok(List.of());
    }
}
