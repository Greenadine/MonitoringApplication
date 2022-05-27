package com.nerdygadgets.application.app.component;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * A {@link JLabel} wrapped within a {@link JPanel}.
 *
 * @author Kevin Zuman
 */
public class WrappedJLabel extends JPanel {

    private final JLabel label;

    public WrappedJLabel(@NotNull final String text) {
        this(text, SwingConstants.LEADING);
    }

    public WrappedJLabel(@NotNull final String text, final int horizontalAlignment) {
        this(text, null, horizontalAlignment);
    }

    public WrappedJLabel(@NotNull final String text, @Nullable final ImageIcon imageIcon) {
        this(text, imageIcon, SwingConstants.LEADING);
    }

    public WrappedJLabel(@NotNull final String text, @Nullable final ImageIcon imageIcon, final int horizontalAlignment) {
        super();

        // Configure panel
        this.setLayout(new BorderLayout());

        // Create label
        label = new JLabel(text, imageIcon, horizontalAlignment);
        this.add(label, BorderLayout.CENTER);
    }

    /**
     * Gets the wrapped {@link JLabel}.
     *
     * @return The wrapped {@code JLabel}.
     */
    public JLabel getLabel() {
        return label;
    }
}
