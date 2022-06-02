package com.nerdygadgets.application.app.window;

import com.nerdygadgets.application.app.dialog.NewAddComponentDialog;
import com.nerdygadgets.application.app.screen.*;
import com.nerdygadgets.application.app.model.ApplicationWindow;

public class MainWindow extends ApplicationWindow {

    public MainWindow() {
        super("NerdyGadgets Network Application");

        // Configure window
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

        // Register screens
        registerScreen("home", new HomeScreen(this));
        registerScreen("network-configurations-menu", new NetworkConfigurationsMenuScreen(this));
        registerScreen("network-configuration", new NetworkConfigurationScreen(this));
        registerScreen("view-network-components", new ViewNetworkComponentsScreen(this));
        registerScreen("network-monitor", new NetworkMonitorScreen(this));

        // Register dialogs
        registerDialog(new NewAddComponentDialog(this, true));

        // Open home screen and make window visible
        openScreen("home");
        this.setVisible(true);
    }
}
