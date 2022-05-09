package com.nerdygadgets.application.app.component;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * A {@link JLabel} wrapped within a {@link JPanel}.
 *
 * @author Kevin Zuman
 */
public class WrappedJLabel extends JLabel {

    private final JPanel wrapperPanel;

    public WrappedJLabel(@NotNull final String text) {
        this(text, LEADING);
    }

    public WrappedJLabel(@NotNull final String text, final int horizontalAlignment) {
        this(text, null, horizontalAlignment);
    }

    public WrappedJLabel(@NotNull final String text, @Nullable final ImageIcon imageIcon) {
        this(text, imageIcon, LEADING);
    }

    public WrappedJLabel(@NotNull final String text, @Nullable final ImageIcon imageIcon, final int horizontalAlignment) {
        super(text, imageIcon, horizontalAlignment);
        this.setAlignmentX(CENTER_ALIGNMENT); // Set default alignment as center

        wrapperPanel = new JPanel();
        wrapperPanel.add(this);
    }

    /**
     * Gets the wrapper {@link JPanel} of the label.
     *
     * @return The wrapper {@code JPanel}.
     */
    public JPanel getWrapperPanel() {
        return wrapperPanel;
    }
}
