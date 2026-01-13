package com.esiea.integrationplatform.adapters.rest;

import com.esiea.integrationplatform.adapters.rest.dto.CreateUserRequestDTO;
import com.esiea.integrationplatform.adapters.rest.dto.ErrorResponseDTO;
import com.esiea.integrationplatform.adapters.rest.dto.UpdateProfileRequestDTO;
import com.esiea.integrationplatform.adapters.rest.dto.UserDtoMapper;
import com.esiea.integrationplatform.adapters.rest.dto.UserResponseDTO;
import com.esiea.integrationplatform.domain.exception.InvalidUserException;
import com.esiea.integrationplatform.domain.exception.UserAlreadyExistsException;
import com.esiea.integrationplatform.domain.exception.UserNotFoundException;
import com.esiea.integrationplatform.domain.model.User;
import com.esiea.integrationplatform.domain.port.in.CreerCompteUseCase;
import com.esiea.integrationplatform.domain.port.in.ModifierProfilUseCase;
import com.esiea.integrationplatform.domain.port.in.ObtenirProfilUseCase;
import com.esiea.integrationplatform.domain.port.in.SupprimerUserUseCase;
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
import java.util.Map;

@RestController
@RequestMapping("/users")
@Tag(name = "Utilisateurs", description = "API de gestion des profils utilisateurs")
public class UserController {

    private final ObtenirProfilUseCase obtenirProfilUseCase;
    private final ModifierProfilUseCase modifierProfilUseCase;
    private final CreerCompteUseCase creerCompteUseCase;
    private final SupprimerUserUseCase supprimerUserUseCase;

    public UserController(ObtenirProfilUseCase obtenirProfilUseCase,
                         ModifierProfilUseCase modifierProfilUseCase,
                         CreerCompteUseCase creerCompteUseCase,
                         SupprimerUserUseCase supprimerUserUseCase) {
        this.obtenirProfilUseCase = obtenirProfilUseCase;
        this.modifierProfilUseCase = modifierProfilUseCase;
        this.creerCompteUseCase = creerCompteUseCase;
        this.supprimerUserUseCase = supprimerUserUseCase;
    }

    /**
     * Créer un nouvel utilisateur
     */
    @PostMapping
    @Operation(summary = "Créer un utilisateur",
            description = "Crée un nouveau compte utilisateur avec le rôle ETUDIANT par défaut")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "Email déjà existant",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequestDTO requestDTO) {

        try {
            // Exécuter le use case
            User user = creerCompteUseCase.execute(
                    requestDTO.getEmail(),
                    requestDTO.getMotDePasse(),
                    requestDTO.getNom(),
                    requestDTO.getPrenom(),
                    requestDTO.getPromotion(),
                    requestDTO.getNumeroEtudiant(),
                    requestDTO.getFiliere()
            );

            // Convertir en DTO
            UserResponseDTO responseDTO = UserDtoMapper.toResponseDto(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

        } catch (UserAlreadyExistsException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 409);
            error.put("error", "Conflict");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (InvalidUserException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 400);
            error.put("error", "Bad Request");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Obtenir un profil utilisateur
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un profil",
            description = "Récupère le profil d'un utilisateur par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profil récupéré avec succès",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<?> getProfile(@PathVariable Long id) {

        try {
            // Exécuter le use case
            User user = obtenirProfilUseCase.execute(id);

            // Convertir en DTO
            UserResponseDTO responseDTO = UserDtoMapper.toResponseDto(user);

            return ResponseEntity.ok(responseDTO);

        } catch (UserNotFoundException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 404);
            error.put("error", "Not Found");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Modifier un profil utilisateur
     */
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un profil",
            description = "Modifie le profil d'un utilisateur par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profil modifié avec succès",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<?> updateProfile(
            @PathVariable Long id,
            @RequestBody UpdateProfileRequestDTO requestDTO) {

        try {
            // Exécuter le use case
            User user = modifierProfilUseCase.execute(
                    id,
                    requestDTO.getNom(),
                    requestDTO.getPrenom(),
                    requestDTO.getPromotion(),
                    requestDTO.getNumeroEtudiant(),
                    requestDTO.getFiliere()
            );

            // Convertir en DTO
            UserResponseDTO responseDTO = UserDtoMapper.toResponseDto(user);

            return ResponseEntity.ok(responseDTO);

        } catch (UserNotFoundException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 404);
            error.put("error", "Not Found");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (InvalidUserException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 400);
            error.put("error", "Bad Request");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Supprimer un utilisateur
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un utilisateur",
            description = "Supprime définitivement un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Utilisateur supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        try {
            // Exécuter le use case
            supprimerUserUseCase.execute(id);

            return ResponseEntity.noContent().build();

        } catch (UserNotFoundException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 404);
            error.put("error", "Not Found");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
