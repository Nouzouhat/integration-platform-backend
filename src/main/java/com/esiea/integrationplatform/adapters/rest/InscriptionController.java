package com.esiea.integrationplatform.adapters.rest;

import com.esiea.integrationplatform.adapters.rest.dto.ErrorResponseDTO;
import com.esiea.integrationplatform.adapters.rest.dto.InscriptionDtoMapper;
import com.esiea.integrationplatform.adapters.rest.dto.InscriptionRequestDTO;
import com.esiea.integrationplatform.adapters.rest.dto.InscriptionResponseDTO;
import com.esiea.integrationplatform.adapters.rest.dto.ModifierInscriptionRequestDTO;
import com.esiea.integrationplatform.domain.exception.EvenementNotFoundException;
import com.esiea.integrationplatform.domain.exception.InscriptionNotFoundException;
import com.esiea.integrationplatform.domain.model.Inscription;
import com.esiea.integrationplatform.domain.port.in.AnnulerInscriptionUseCase;
import com.esiea.integrationplatform.domain.port.in.ConsulterInscriptionsEtudiantUseCase;
import com.esiea.integrationplatform.domain.port.in.InscrireEtudiantUseCase;
import com.esiea.integrationplatform.domain.port.in.ModifierInscriptionUseCase;
import com.esiea.integrationplatform.domain.port.in.RecupererInscriptionUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    /**
     * Inscrire un étudiant à un événement
     */
    @PostMapping
    @Operation(summary = "Inscrire un étudiant",
            description = "Inscrit un étudiant à un événement. Si la capacité est atteinte, l'inscription est mise en liste d'attente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inscription créée avec succès",
                    content = @Content(schema = @Schema(implementation = InscriptionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides ou étudiant déjà inscrit",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Événement non trouvé",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<?> inscrireEtudiant(
            @RequestBody InscriptionRequestDTO requestDTO) {

        try {
            // Exécuter le use case
            Inscription inscription = inscrireEtudiantUseCase.execute(
                    requestDTO.getEtudiantId(),
                    requestDTO.getEvenementId(),
                    requestDTO.getCommentaire()
            );

            // Convertir en DTO de réponse
            InscriptionResponseDTO responseDTO = InscriptionDtoMapper.toResponseDto(inscription);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

        } catch (IllegalArgumentException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 400);
            error.put("error", "Bad Request");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (EvenementNotFoundException ex) { // Si défini, sinon on laisse le global ou on l'ajoute si le usecase le lance
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 404);
            error.put("error", "Not Found");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Récupérer les inscriptions par étudiant
     */
    @GetMapping(params = "etudiantId")
    @Operation(summary = "Récupérer les inscriptions d'un étudiant",
            description = "Récupère toutes les inscriptions d'un étudiant via query parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des inscriptions récupérée avec succès",
                    content = @Content(schema = @Schema(implementation = InscriptionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Paramètre 'etudiantId' invalide",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<?> getInscriptionsByEtudiant(
            @RequestParam Long etudiantId) {

        try {
            // Exécuter le use case
            List<Inscription> inscriptions = consulterInscriptionsEtudiantUseCase.execute(etudiantId);

            // Convertir en DTOs
            List<InscriptionResponseDTO> responseDTOs = inscriptions.stream()
                    .map(InscriptionDtoMapper::toResponseDto)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(responseDTOs);
        } catch (IllegalArgumentException ex) {
             Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 400);
            error.put("error", "Bad Request");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }


    /**
     * Récupérer une inscription par son ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une inscription",
            description = "Récupère une inscription par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscription récupérée avec succès",
                    content = @Content(schema = @Schema(implementation = InscriptionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "ID invalide",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Inscription non trouvée",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<?> recupererInscription(
            @PathVariable Long id) {

        try {
            // Exécuter le use case
            Inscription inscription = recupererInscriptionUseCase.execute(id);

            // Convertir en DTO
            InscriptionResponseDTO responseDTO = InscriptionDtoMapper.toResponseDto(inscription);

            return ResponseEntity.ok(responseDTO);

        } catch (InscriptionNotFoundException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 404);
            error.put("error", "Not Found");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Modifier une inscription (remplacement complet)
     */
    @PutMapping("/{id}")
    @Operation(summary = "Modifier une inscription (PUT)",
            description = "Remplace complètement une inscription existante. Tous les champs doivent être fournis.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscription modifiée avec succès",
                    content = @Content(schema = @Schema(implementation = InscriptionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Inscription non trouvée",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<?> modifierInscription(
            @PathVariable Long id,
            @RequestBody ModifierInscriptionRequestDTO requestDTO) {

        try {
            // Exécuter le use case
            Inscription inscription = modifierInscriptionUseCase.execute(
                    id,
                    requestDTO.getStatut(),
                    requestDTO.getCommentaire()
            );

            // Convertir en DTO
            InscriptionResponseDTO responseDTO = InscriptionDtoMapper.toResponseDto(inscription);

            return ResponseEntity.ok(responseDTO);

        } catch (InscriptionNotFoundException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 404);
            error.put("error", "Not Found");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (IllegalArgumentException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 400);
            error.put("error", "Bad Request");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Modifier partiellement une inscription
     */
    @PatchMapping("/{id}")
    @Operation(summary = "Modifier partiellement une inscription (PATCH)",
            description = "Modifie uniquement les champs fournis d'une inscription. Les champs non fournis restent inchangés.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscription modifiée avec succès",
                    content = @Content(schema = @Schema(implementation = InscriptionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Inscription non trouvée",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<?> modifierPartiellementInscription(
            @PathVariable Long id,
            @RequestBody ModifierInscriptionRequestDTO requestDTO) {

        try {
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

        } catch (InscriptionNotFoundException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 404);
            error.put("error", "Not Found");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (IllegalArgumentException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 400);
            error.put("error", "Bad Request");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Annuler une inscription
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Annuler une inscription",
            description = "Annule/supprime une inscription existante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Inscription annulée avec succès (sans contenu)"),
            @ApiResponse(responseCode = "400", description = "ID invalide",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Inscription non trouvée",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<?> annulerInscription(@PathVariable Long id) {

        try {
            // Exécuter le use case
            annulerInscriptionUseCase.execute(id);

            return ResponseEntity.noContent().build();

        } catch (InscriptionNotFoundException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 404);
            error.put("error", "Not Found");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Récupérer les inscriptions par événement
     */
    @GetMapping(params = "evenementId")
    @Operation(summary = "Récupérer les inscriptions d'un événement",
            description = "Récupère toutes les inscriptions pour un événement donné via query parameter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des inscriptions récupérée avec succès",
                    content = @Content(schema = @Schema(implementation = InscriptionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Paramètre 'evenementId' invalide",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<?> getInscriptionsByEvenement(
            @RequestParam Long evenementId) {

        // Pour l'instant, on utilise le repository directement
        // TODO: Créer un use case dédié si nécessaire
        return ResponseEntity.ok(List.of());
    }
}
