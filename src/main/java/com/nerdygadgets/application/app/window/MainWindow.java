package com.nerdygadgets.application.app.window;

import com.nerdygadgets.application.app.screen.*;
import com.nerdygadgets.application.app.model.ApplicationWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public class MainWindow extends ApplicationWindow {

    public MainWindow() throws IOException {
        super("NerdyGadgets Network Application");

        // Configure window
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/assets/icons/app.png"))).getImage());
        this.setResizable(false);

        // Create and register screens
        registerScreen("home", new HomeScreen(this));
        registerScreen("network-configurations", new NetworkConfigurationsScreen(this));
        registerScreen("view-network-configuration", new ViewNetworkConfigurationScreen(this));
        registerScreen("create-network-configuration", new CreateNetworkConfigurationScreen(this));
        registerScreen("view-network-components", new ViewNetworkComponentsScreen(this));
        registerScreen("add-network-component", new AddNetworkComponentScreen(this));
        registerScreen("network-monitor", new NetworkMonitorScreen(this));

        // Open home screen and make window visible
        openScreen("home");
        this.setVisible(true);
    }
}
