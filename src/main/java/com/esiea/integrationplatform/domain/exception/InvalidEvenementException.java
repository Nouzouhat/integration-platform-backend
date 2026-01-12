package com.esiea.integrationplatform.domain.exception;

/**
 * Exception levée lorsque les données d'un événement sont invalides
 * Code HTTP : 400 Bad Request
 */
public class InvalidEvenementException extends RuntimeException {

    public InvalidEvenementException(String message) {
        super(message);
    }
}