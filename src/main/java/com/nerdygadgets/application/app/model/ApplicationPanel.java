package com.nerdygadgets.application.app.model;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Super class for specific panels within the application.
 *
 * @author Kevin Zuman
 */
public abstract class ApplicationPanel extends JPanel {

    public final ApplicationScreen parentScreen;

    protected final ArrayList<ApplicationComponent> components;

    public ApplicationPanel(@NotNull final ApplicationScreen parentScreen) {
        parentScreen.addApplicationPanel(this);
        this.parentScreen = parentScreen;
        this.components = new ArrayList<>();
    }

    /**
     * Adds an {@link ApplicationComponent} to this screen.
     *
     * <p>NOTE: This method does not add the component to the panel for displaying,
     * this is an internal method only for tracking which components are present on a particular panel.
     *
     * @param applicationComponent The {@code ApplicationComponent}.
     */
    void addPanelComponent(@NotNull final ApplicationComponent applicationComponent) {
        components.add(applicationComponent);
    }

    /**
     * Method called before the panel is displayed.
     */
    public void onShow() {
        components.forEach(ApplicationComponent::onShow);
        onShowImpl();
    }

    /**
     * Method called after the panel is hidden.
     */
    public void onHide() {
        components.forEach(ApplicationComponent::onHide);
        onHideImpl();
    }

    /**
     * Implemented method called before the panel is displayed.
     */
    public abstract void onShowImpl();

    /**
     * Method called after the panel is hidden.
     */
    public abstract void onHideImpl();
}
