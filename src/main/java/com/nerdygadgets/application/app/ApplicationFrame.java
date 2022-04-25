package com.nerdygadgets.application.app;

import com.nerdygadgets.application.app.screen.CreateNetworkConfigurationScreen;
import com.nerdygadgets.application.app.screen.HomeScreen;
import com.nerdygadgets.application.app.screen.NetworkMonitorScreen;

import javax.swing.*;
import java.awt.*;

public class ApplicationFrame extends JFrame {

    private final JPanel contentPanel;

    private final HomeScreen homeScreen;
    private final CreateNetworkConfigurationScreen createNetworkConfigurationScreen;
    private final NetworkMonitorScreen networkMonitorScreen;

    public ApplicationFrame() {
        /* Configure frame */
        this.setTitle("NerdyGadgets Network Application");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon("assets\\icons\\app.png").getImage());
        this.setResizable(false);

        /* Create panels */

        // Create home panel
        homeScreen = new HomeScreen(this);

        // Create, configure and add main panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.add(contentPanel);

        // Create network configuration creation screen
        createNetworkConfigurationScreen = new CreateNetworkConfigurationScreen(this);

        // Create network monitor screen
        networkMonitorScreen = new NetworkMonitorScreen(this);

        homeScreen.open(); // Open home screen

        // Set starting location on screen
        centerFrame();

        this.setVisible(true); // Make visible
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public HomeScreen getHomeScreen() {
        return homeScreen;
    }

    public CreateNetworkConfigurationScreen getCreateNetworkConfigurationScreen() {
        return createNetworkConfigurationScreen;
    }

    public NetworkMonitorScreen getNetworkMonitorScreen() {
        return networkMonitorScreen;
    }

    public void centerFrame() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - contentPanel.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - contentPanel.getHeight()) / 2);
        this.setLocation(x, y);
    }
}
