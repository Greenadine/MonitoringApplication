package com.nerdygadgets.application.app.component;

import com.nerdygadgets.application.app.model.ApplicationComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * A {@link JButton} wrapped within a {@link JPanel}.
 *
 * @author Kevin Zuman
 */
public class WrappedJButton extends JPanel implements ApplicationComponent {

    private final JButton button;

    public WrappedJButton() {
        this("");
    }

    public WrappedJButton(String name) {
        this(name, null);
    }

    public WrappedJButton(@NotNull final String name, @Nullable final Icon icon) {
        this.setLayout(new BorderLayout());

        button = new JButton(name, icon);
        this.add(button);
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

    @Override
    public void onShow() {

    }

    @Override
    public void onHide() {

    }
}
