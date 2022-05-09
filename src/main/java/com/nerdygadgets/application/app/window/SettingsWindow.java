package com.nerdygadgets.application.app.window;

import com.nerdygadgets.application.app.model.ApplicationWindow;
import com.nerdygadgets.application.app.screen.SettingsScreen;
import com.nerdygadgets.application.util.SwingUtils;

public class SettingsWindow extends ApplicationWindow {

    public SettingsWindow() {
        super("Settings");

        // Configure window
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setIconImage(SwingUtils.getIconFromResource("app.png").getImage());

        // Create and register screens
        registerScreen("settings", new SettingsScreen(this));

        // Open settings screen
        openScreen("settings");
        this.setVisible(false);
    }
}
