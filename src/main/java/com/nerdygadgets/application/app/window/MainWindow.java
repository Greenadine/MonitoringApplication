package com.nerdygadgets.application.app.window;

import com.nerdygadgets.application.app.screen.*;
import com.nerdygadgets.application.app.model.ApplicationWindow;
import com.nerdygadgets.application.util.SwingUtils;

public class MainWindow extends ApplicationWindow {

    public MainWindow() {
        super("NerdyGadgets Network Application");

        // Configure window
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setIconImage(SwingUtils.getIconFromResource("app.png").getImage());
        this.setResizable(false);

        // Create and register screens
        registerScreen("home", new HomeScreen(this));
        registerScreen("network-configurations-menu", new NetworkConfigurationsMenuScreen(this));
        registerScreen("network-configuration", new NetworkConfigurationScreen(this));
        registerScreen("view-network-components", new ViewNetworkComponentsScreen(this));
        registerScreen("network-monitor", new NetworkMonitorScreen(this));

        // Open home screen and make window visible
        openScreen("home");
        this.setVisible(true);
    }
}
