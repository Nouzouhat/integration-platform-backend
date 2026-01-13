package com.esiea.integrationplatform.domain.exception;

/**
 * Exception levée lorsqu'un utilisateur n'est pas trouvé
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("Utilisateur avec l'ID " + id + " introuvable");
    }

    public UserNotFoundException(String email) {
        super("Utilisateur avec l'email '" + email + "' introuvable");
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
