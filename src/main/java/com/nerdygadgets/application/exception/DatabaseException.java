package com.nerdygadgets.application.exception;

/**
 * An {@code Exception} thrown when the application fails to establish a connection to the remote database.
 *
 * @author Kevin Zuman
 */
public class DatabaseException extends RuntimeException {

    private final Throwable parent;

    public DatabaseException(String message) {
        this(message, null);
    }

    public DatabaseException(String message, Throwable parent) {
        super(message);
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
