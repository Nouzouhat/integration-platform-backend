package com.esiea.integrationplatform.domain.exception;

/**
 * Exception levée lorsqu'un utilisateur avec cet email existe déjà
 */
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String email) {
        super("Un utilisateur avec l'email '" + email + "' existe déjà");
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
