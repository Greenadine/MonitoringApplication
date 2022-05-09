package com.nerdygadgets.application.exception;

/**
 * An {@code Exception} thrown when the application fails to establish a connection to the remote database.
 *
 * @author Kevin Zuman
 */
public class DatabaseException extends RuntimeException {

    private final Throwable parent;

    public DatabaseException(final String message) {
        this(null, message);
    }

    public DatabaseException(final Throwable parent, final String message, Object... replacements) {
        super(String.format(message, replacements));
        this.parent = parent;
    }

    @Override
    public void printStackTrace() {
        if (parent != null) {
            parent.printStackTrace();
        } else {
            super.printStackTrace();
        }
    }
}
