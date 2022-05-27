package com.nerdygadgets.application.app.component;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * A {@link JButton} wrapped within a {@link JPanel}.
 *
 * @author Kevin Zuman
 */
public class WrappedJButton extends JPanel {

    private final JButton button;

    public WrappedJButton() {
        this("");
    }

    public WrappedJButton(@NotNull final String text) {
        this(text, null);
    }

    public WrappedJButton(@NotNull final String text, @Nullable final Icon icon) {
        super();

        this.setLayout(new BorderLayout());

        this.button = new JButton(text, icon);
        this.add(button, BorderLayout.CENTER);
    }

    /**
     * Gets the wrapped {@link JButton}.
     *
     * @return The wrapped {@code JButton}.
     */
    public JButton getButton() {
        return button;
    }
}
