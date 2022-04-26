package com.nerdygadgets.application.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public final class SwingUtils {

    /**
     * Sets the size of the given {@link JComponent}.
     *
     * @param target The {@code JComponent}.
     * @param width The width in pixels.
     * @param height The height in pixels.
     */
    public static void setSize(@NotNull final JComponent target, final int width, final int height) {
        final Dimension dimension = new Dimension(width, height);
        target.setPreferredSize(dimension);
        target.setMaximumSize(dimension);
    }

    /**
     * Adds a button to the given target {@link JComponent}.
     *
     * @param target The target {@code JComponent}.
     * @param text The text on the button.
     * @param icon The {@link Icon} to display on the button.
     * @param width The width of the button in pixels.
     * @param height The height of the button in pixels.
     * @param actionListener The {@link ActionListener} for the button.
     */
    public static void addButton(@NotNull final JComponent target, @Nullable final String text, @Nullable final Icon icon, final int width, final int height, @NotNull final ActionListener actionListener) {
        target.add(createButton(text, icon, width, height, actionListener));
    }

    /**
     * Creates a new {@link JButton}.
     *
     * @param text The text on the button.
     * @param icon The {@link Icon} to display on the button.
     * @param width The width of the button in pixels.
     * @param height The height of the button in pixels.
     * @param actionListener The {@link ActionListener} for the button.
     *
     * @return The newly created {@code JButton}.
     */
    public static JButton createButton(@Nullable final String text, @Nullable final Icon icon, final int width, final int height, @NotNull final ActionListener actionListener) {
        JButton button = createButton(text, icon, actionListener);
        setSize(button, width, height);
        return button;
    }

    /**
     * Creates a new {@link JButton}.
     *
     * @param text The text on the button.
     * @param icon The {@link Icon} to dispaly onthe button.
     * @param actionListener The {@link ActionListener} for the button.
     *
     * @return The newly created {@code JButton}.
     */
    public static JButton createButton(@Nullable final String text, @Nullable final Icon icon, @NotNull final ActionListener actionListener) {
        JButton button = new JButton(text, icon);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(Fonts.DEFAULT);
        button.addActionListener(actionListener);
        return button;
    }

    public static void addHorizontalSeparator(@NotNull final JComponent target) {
        target.add(new JSeparator(SwingConstants.HORIZONTAL));
    }

    public static void addVerticalSeparator(@NotNull final JComponent target) {
        target.add(new JSeparator(SwingConstants.VERTICAL));
    }

    public static void addHorizontalSpacer(@NotNull final JComponent target, final int width) {
        target.add(Box.createHorizontalStrut(width));
    }

    public static void addVerticalSpacer(@NotNull final JComponent target, final int height) {
        target.add(Box.createVerticalStrut(height));
    }
}
