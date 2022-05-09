package com.nerdygadgets.application.exception;

import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.model.ApplicationWindow;
import org.jetbrains.annotations.NotNull;

/**
 * An {@code Exception} thrown when attempting to open an unknown {@link ApplicationScreen} for an {@link ApplicationWindow}.
 *
 * @author Kevin Zuman
 */
public class InvalidWindowScreenException extends RuntimeException {

    public InvalidWindowScreenException(@NotNull final String windowName, @NotNull final String screenName) {
        super(String.format("Invalid screen '%s' for window '%s'.", screenName, windowName));
    }
}
