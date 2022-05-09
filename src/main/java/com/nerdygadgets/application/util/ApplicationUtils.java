package com.nerdygadgets.application.util;

import com.nerdygadgets.application.Main;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public final class ApplicationUtils {

    /**
     * Shows an information popup message with the provided title and message.
     *
     * @param title The title.
     * @param message The message.
     */
    public static void showPopupInfoMessage(@NotNull final String title, @NotNull final String message) {
        JOptionPane.showMessageDialog(Main.mainWindow, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows an error popup message with the provided title and message.
     *
     * @param title The title.
     * @param message The message.
     */
    public static void showPopupErrorMessage(@NotNull final String title, @NotNull final String message) {
        JOptionPane.showMessageDialog(Main.mainWindow, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
