package com.grinderwolf.eswm.api.exceptions;

/**
 * Exception thrown when a
 * world could not be found.
 */
public class UnknownWorldException extends SlimeException {

    public UnknownWorldException(String world) {
        super("Unknown world " + world);
    }
}
