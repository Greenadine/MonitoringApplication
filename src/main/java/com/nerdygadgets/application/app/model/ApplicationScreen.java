package com.nerdygadgets.application.app.model;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;

/**
 * Super class for all screens within the application.
 *
 * @author Kevin Zuman
 */
public abstract class ApplicationScreen extends JPanel {

    protected final ApplicationWindow window;

    protected final ArrayList<ApplicationPanel> panels;

    public ApplicationScreen(@NotNull final ApplicationWindow window) {
        this.window = window;
        this.panels = new ArrayList<>();

        this.setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    /**
     * Adds an {@link ApplicationPanel} to this screen.
     *
     * <p>NOTE: This method does not add the component to the screen for displaying,
     * this is an internal method only for tracking which panels are present on a particular screen.
     *
     * @param applicationPanel The {@code ApplicationPanel}.
     */
    void addApplicationPanel(ApplicationPanel applicationPanel) {
        panels.add(applicationPanel);
    }

    /**
     * Method called before the panel is opened.
     */
    public void onOpen() {
        panels.forEach(ApplicationPanel::onShow);
        onOpenImpl();
    }

    /**
     * Method called after a panel was closed.
     */
    public void onClose() {
        panels.forEach(ApplicationPanel::onHide);
        onCloseImpl();
    }

    /**
     * Implemented method called before the panel is opened.
     */
    protected abstract void onOpenImpl();

    /**
     * Implemented method called after a panel was closed.
     */
    protected abstract void onCloseImpl();
}
