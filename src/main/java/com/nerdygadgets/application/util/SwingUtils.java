package com.nerdygadgets.application.util;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public final class SwingUtils {

    /**
     * Adds a button to the given target {@link JComponent}.
     *
     * @param target The target {@code JComponent}.
     * @param label The text on the button.
     * @param width The width of the button in pixels.
     * @param height The height of the button in pixels.
     * @param actionListener The {@link ActionListener} for the button.
     */
    public static void addButton(@NotNull final JComponent target, @NotNull final String label, final int width, final int height, @NotNull final ActionListener actionListener) {
        JButton button = new JButton(label);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(Fonts.DEFAULT);
        setSizeAll(button, new Dimension(width, height));
        button.addActionListener(actionListener);
        target.add(button);
    }

    public static void addVerticalSpacer(@NotNull final JComponent target, final int height) {
        target.add(Box.createVerticalStrut(height));
    }

    public static void setSizeAll(Component component, Dimension dimension) {
        component.setPreferredSize(dimension);
        component.setMinimumSize(dimension);
        component.setMaximumSize(dimension);
    }

    public static void setSizeMinMax(Component component, Dimension dimension) {
        component.setMinimumSize(dimension);
        component.setMaximumSize(dimension);
    }
}
