package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.ApplicationWindow;
import com.nerdygadgets.application.app.panel.ApplicationPanel;
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
    public void addApplicationPanel(ApplicationPanel applicationPanel) {
        panels.add(applicationPanel);
    }

    /**
     * Method called when opening and displaying the panel.
     */
    public void open() {
        window.setVisible(false);
        window.getCurrentScreen().ifPresent(ApplicationScreen::onClose); // Perform 'on close' of previous screen
        onOpen();
        window.getContentPanel().removeAll(); // Remove all current contents
        window.getContentPanel().add(this);
        window.getContentPanel().repaint();
        window.pack(); // Resize frame to fit content
        window.centerFrame();
        window.setCurrentScreen(this); // Set this screen as current
        window.setVisible(true);
    }

    /**
     * Method called before the panel is opened.
     */
    public void onOpen() {
        panels.forEach(ApplicationPanel::onDisplay);
        onOpenImpl();
    }

    /**
     * Implemented method called before the panel is opened.
     */
    protected abstract void onOpenImpl();

    /**
     * Method called after a panel was closed.
     */
    public void onClose() {
        panels.forEach(ApplicationPanel::onHide);
        onCloseImpl();
    }

    /**
     * Implemented method called after a panel was closed.
     */
    protected abstract void onCloseImpl();
}
