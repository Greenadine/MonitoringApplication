package com.nerdygadgets.application.exception;

import com.nerdygadgets.application.app.model.ApplicationComponent;

/**
 * An {@code Exception} thrown when an issue occurs while attempting to display an {@link ApplicationComponent}.
 *
 * @author Kevin Zuman
 */
public class DisplayComponentException extends RuntimeException {

    public DisplayComponentException(String message) {
        super(message);
    }
}
