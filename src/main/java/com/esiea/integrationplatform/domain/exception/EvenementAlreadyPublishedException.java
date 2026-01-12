package com.esiea.integrationplatform.domain.exception;

/**
 * Exception levée lorsqu'on tente de publier un événement déjà publié
 * Code HTTP : 400 Bad Request
 */
public class EvenementAlreadyPublishedException extends RuntimeException {

    public EvenementAlreadyPublishedException() {
        super("L'événement est déjà publié");
    }

    public EvenementAlreadyPublishedException(Long id) {
        super("L'événement avec l'ID " + id + " est déjà publié");
    }
}