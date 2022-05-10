package com.nerdygadgets.application.exception;

/**
 * An {@code Exception} thrown when the application fails to establish a connection to the remote database.
 *
 * @author Kevin Zuman
 */
public class DatabaseException extends RuntimeException {

    public DatabaseException(final String message) {
        this(null, message);
    }

    public DatabaseException(final Throwable parent, final String message, Object... replacements) {
        super(String.format(message, replacements));
        super.initCause(parent);
    }

    @Override
    public void printStackTrace() {
        if (this.getCause() != null) {
            this.getCause().printStackTrace();
        } else {
            super.printStackTrace();
        }
    }
}
