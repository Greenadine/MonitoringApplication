package com.nerdygadgets.application.util;

import com.nerdygadgets.application.Main;

import java.awt.event.ActionEvent;

/**
 * Contains common {@link java.awt.event.ActionListener}s used throughout the application.
 *
 * @author Kevin Zuman
 */
@SuppressWarnings("unused")
public final class ApplicationActions {

    /**
     * Opens the application's {@link com.nerdygadgets.application.app.screen.HomeScreen}.
     *
     * @param event The {@link ActionEvent}.
     */
    public static void openHome(ActionEvent event) {
        Main.getApplicationFrame().getHomeScreen().open();
    }
}
