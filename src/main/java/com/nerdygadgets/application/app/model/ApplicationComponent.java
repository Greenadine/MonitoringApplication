package com.nerdygadgets.application.app.model;

/**
 * Super class for specific {@link javax.swing.JComponent}s within the application.
 *
 * @author Kevin Zuman
 */
public interface ApplicationComponent {

    /**
     * Method called every time before the component is shown.
     */
    void onShow();

    /**
     * Method called ever time after the component is hidden.
     */
    void onHide();
}
