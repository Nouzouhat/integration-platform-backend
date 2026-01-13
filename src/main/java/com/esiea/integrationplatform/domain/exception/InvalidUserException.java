package com.esiea.integrationplatform.domain.exception;

/**
 * Exception levée lorsque les données d'un utilisateur sont invalides
 */
public class InvalidUserException extends RuntimeException {

    public InvalidUserException(String message) {
        super(message);
    }

    public InvalidUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
