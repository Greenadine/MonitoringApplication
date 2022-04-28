package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.screen.ApplicationScreen;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Super class for specific panels within the application.
 *
 * @author Kevin Zuman
 */
public abstract class ApplicationPanel extends JPanel {

    protected final ApplicationScreen screen;

    public ApplicationPanel(@NotNull final ApplicationScreen screen) {
        screen.addApplicationPanel(this);
        this.screen = screen;
    }

    /**
     * Gets the {@link ApplicationScreen} on which this panel is displayed.
     *
     * @return The panel's parent {@code ApplicationScreen}.
     */
    public ApplicationScreen getScreen() {
        return screen;
    }

    /**
     * Method called before the panel is displayed.
     */
    public abstract void onDisplay();

    /**
     * Method called after the panel is hidden.
     */
    public abstract void onHide();
}
