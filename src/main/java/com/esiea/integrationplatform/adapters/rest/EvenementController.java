package com.esiea.integrationplatform.adapters.rest;

import com.esiea.integrationplatform.adapters.rest.dto.EvenementDtoMapper;
import com.esiea.integrationplatform.adapters.rest.dto.EvenementRequestDTO;
import com.esiea.integrationplatform.adapters.rest.dto.EvenementResponseDTO;
import com.esiea.integrationplatform.adapters.rest.dto.StatutRequestDTO;
import com.esiea.integrationplatform.domain.exception.EvenementAlreadyPublishedException;
import com.esiea.integrationplatform.domain.exception.EvenementNotFoundException;
import com.esiea.integrationplatform.domain.exception.EvenementNotPublishedException;
import com.esiea.integrationplatform.domain.model.Evenement;
import com.esiea.integrationplatform.domain.port.in.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour les événements
 */
@RestController
@RequestMapping("/evenements")
@Tag(name = "Événements", description = "API de gestion des événements de la semaine d'intégration")
public class EvenementController {

    private final CreerEvenementUseCase creerEvenementUseCase;
    private final ListerEvenementsUseCase listerEvenementsUseCase;
    private final ObtenirEvenementUseCase obtenirEvenementUseCase;
    private final ModifierEvenementUseCase modifierEvenementUseCase;
    private final SupprimerEvenementUseCase supprimerEvenementUseCase;
    private final PublierEvenementUseCase publierEvenementUseCase;
    private final DepublierEvenementUseCase depublierEvenementUseCase;
    private final ExporterEvenementsCSVUseCase exporterEvenementsCSVUseCase;

    public EvenementController(
            CreerEvenementUseCase creerEvenementUseCase,
            ListerEvenementsUseCase listerEvenementsUseCase,
            ObtenirEvenementUseCase obtenirEvenementUseCase,
            ModifierEvenementUseCase modifierEvenementUseCase,
            SupprimerEvenementUseCase supprimerEvenementUseCase,
            PublierEvenementUseCase publierEvenementUseCase,
            DepublierEvenementUseCase depublierEvenementUseCase,
            ExporterEvenementsCSVUseCase exporterEvenementsCSVUseCase) {
        this.creerEvenementUseCase = creerEvenementUseCase;
        this.listerEvenementsUseCase = listerEvenementsUseCase;
        this.obtenirEvenementUseCase = obtenirEvenementUseCase;
        this.modifierEvenementUseCase = modifierEvenementUseCase;
        this.supprimerEvenementUseCase = supprimerEvenementUseCase;
        this.publierEvenementUseCase = publierEvenementUseCase;
        this.depublierEvenementUseCase = depublierEvenementUseCase;
        this.exporterEvenementsCSVUseCase = exporterEvenementsCSVUseCase;
    }

    /**
     * Créer un nouvel événement
     */
    @PostMapping
    @Operation(summary = "Créer un événement",
            description = "Crée un nouvel événement pour la semaine d'intégration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Événement créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<EvenementResponseDTO> creerEvenement(
            @RequestBody @Valid EvenementRequestDTO requestDTO) {

        Evenement evenement = EvenementDtoMapper.toDomain(requestDTO);
        Evenement evenementCree = creerEvenementUseCase.execute(evenement);
        EvenementResponseDTO responseDTO = EvenementDtoMapper.toResponseDto(evenementCree);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Lister tous les événements
     */
    @GetMapping
    @Operation(summary = "Lister les événements",
            description = "Récupère la liste de tous les événements au format JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<List<EvenementResponseDTO>> listerEvenements() {

        List<Evenement> evenements = listerEvenementsUseCase.execute();
        List<EvenementResponseDTO> responseDTOs = evenements.stream()
                .map(EvenementDtoMapper::toResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Exporter les événements en CSV
     */
    @GetMapping("/e")
    @Operation(summary = "Exporter les événements en CSV",
            description = "Génère et télécharge un fichier CSV contenant tous les événements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CSV généré avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<String> exporterEvenementsCSV() {

        String csv = exporterEvenementsCSVUseCase.exporterEnCSV();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment", "evenements.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .body(csv);
    }

    /**
     * Obtenir un événement par ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un événement par ID",
            description = "Récupère les détails complets d'un événement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Événement trouvé"),
            @ApiResponse(responseCode = "404", description = "Événement non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<?> obtenirEvenement(@PathVariable Long id) {
        try {
            Evenement evenement = obtenirEvenementUseCase.obtenirParId(id);
            EvenementResponseDTO responseDTO = EvenementDtoMapper.toResponseDto(evenement);
            return ResponseEntity.ok(responseDTO);
        } catch (EvenementNotFoundException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 404);
            error.put("error", "Not Found");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Modifier un événement (toutes les données)
     */
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un événement",
            description = "Modifie toutes les données d'un événement existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Événement modifié avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Événement non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<?> modifierEvenement(
            @PathVariable Long id,
            @RequestBody @Valid EvenementRequestDTO requestDTO) {

        try {
            Evenement evenement = EvenementDtoMapper.toDomain(requestDTO);
            Evenement evenementModifie = modifierEvenementUseCase.modifier(id, evenement);
            EvenementResponseDTO responseDTO = EvenementDtoMapper.toResponseDto(evenementModifie);

            return ResponseEntity.ok(responseDTO);
        } catch (EvenementNotFoundException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 404);
            error.put("error", "Not Found");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Modifier le statut d'un événement
     */
    @PatchMapping("/{id}")
    @Operation(summary = "Modifier le statut d'un événement",
            description = "Change le statut d'un événement. Statuts possibles : BROUILLON, PUBLIE, ANNULE, TERMINE. " +
                    "Publier un événement (BROUILLON → PUBLIE) déclenche un événement Kafka.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statut modifié avec succès"),
            @ApiResponse(responseCode = "400", description = "Transition de statut invalide ou données invalides"),
            @ApiResponse(responseCode = "404", description = "Événement non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<?> modifierStatut(
            @PathVariable Long id,
            @RequestBody @Valid StatutRequestDTO statutRequest) {

        try {
            Evenement evenement;
            String nouveauStatut = statutRequest.getStatut().toUpperCase();

            switch (nouveauStatut) {
                case "PUBLIE":
                    evenement = publierEvenementUseCase.publier(id);
                    break;

                case "BROUILLON":
                    evenement = depublierEvenementUseCase.depublier(id);
                    break;

                case "ANNULE":
                case "TERMINE":
                    evenement = obtenirEvenementUseCase.obtenirParId(id);
                    evenement.setStatut(nouveauStatut);
                    evenement = modifierEvenementUseCase.modifier(id, evenement);
                    break;

                default:
                    Map<String, Object> error = new HashMap<>();
                    error.put("timestamp", LocalDateTime.now());
                    error.put("status", 400);
                    error.put("error", "Bad Request");
                    error.put("message", "Statut invalide : " + nouveauStatut);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            EvenementResponseDTO responseDTO = EvenementDtoMapper.toResponseDto(evenement);
            return ResponseEntity.ok(responseDTO);

        } catch (EvenementNotFoundException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 404);
            error.put("error", "Not Found");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (EvenementAlreadyPublishedException | EvenementNotPublishedException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 400);
            error.put("error", "Bad Request");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Supprimer un événement
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un événement",
            description = "Supprime définitivement un événement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Événement supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Événement non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    public ResponseEntity<?> supprimerEvenement(@PathVariable Long id) {
        try {
            supprimerEvenementUseCase.supprimer(id);
            return ResponseEntity.noContent().build();
        } catch (EvenementNotFoundException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 404);
            error.put("error", "Not Found");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}