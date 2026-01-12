package com.esiea.integrationplatform.domain.exception;

/**
 * Exception levée lorsqu'on tente de dépublier un événement non publié
 * Code HTTP : 400 Bad Request
 */
public class EvenementNotPublishedException extends RuntimeException {

    public EvenementNotPublishedException() {
        super("L'événement n'est pas publié");
    }

    public EvenementNotPublishedException(Long id) {
        super("L'événement avec l'ID " + id + " n'est pas publié");
    }
}