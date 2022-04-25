package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.ApplicationFrame;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Super class for all screens within the application.
 *
 * @author Kevin Zuman
 */
public abstract class AbstractApplicationScreen extends JPanel {

    protected final ApplicationFrame applicationFrame;

    public AbstractApplicationScreen(@NotNull final ApplicationFrame applicationFrame) {
        this.applicationFrame = applicationFrame;
    }

    /**
     * Method called when opening and displaying the panel.
     */
    public void open() {
        applicationFrame.setVisible(false);
        preOpen();
        applicationFrame.getContentPanel().removeAll(); // Remove all current contents
        applicationFrame.getContentPanel().add(this);
        applicationFrame.getContentPanel().repaint();
        applicationFrame.pack(); // Resize frame to fit content
        applicationFrame.centerFrame();
        applicationFrame.setVisible(true);
    }

    /**
     * Method called before the panel is opened.
     */
    public abstract void preOpen();
}
