package com.nerdygadgets.application.app.component;

import com.nerdygadgets.application.app.model.ApplicationComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class WrappedJButton extends JPanel implements ApplicationComponent {

    private final JButton button;

    public WrappedJButton() {
        this("");
    }

    public WrappedJButton(String name) {
        this(name, null);
    }

    public WrappedJButton(@NotNull final String name, @Nullable final Icon icon) {
        button = new JButton(name, icon);
        this.add(button);
    }

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
