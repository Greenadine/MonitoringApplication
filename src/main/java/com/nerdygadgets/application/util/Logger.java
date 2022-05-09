package com.nerdygadgets.application.util;

import org.jetbrains.annotations.NotNull;

/**
 * Utility class for logging information to console.
 *
 * @author Kevin Zuman
 */
public final class Logger {

    public static void info(@NotNull final String message) {
        System.out.println("INFO: " + message);
    }

    public static void info(@NotNull final String message, final Object... replacements) {
        info(String.format(message, replacements));
    }

    public static void warning(@NotNull final String message) {
        System.out.println("WARNING: " + message);
    }

    public static void warning(@NotNull final String message, final Object... replacements) {
        warning(String.format(message, replacements));
    }

    public static void error(@NotNull final Throwable thrown, @NotNull final String message) {
        System.out.println("ERROR: " + message);
        thrown.printStackTrace();
    }

    public static void error(@NotNull final Throwable thrown, @NotNull final String message, final Object... replacements) {
        error(thrown, String.format(message, replacements));
    }
}
