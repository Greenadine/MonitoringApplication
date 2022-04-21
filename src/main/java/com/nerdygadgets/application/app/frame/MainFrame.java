package com.nerdygadgets.application.app.frame;

import com.nerdygadgets.application.app.panel.CreateNetworkConfigurationPanel;
import com.nerdygadgets.application.app.panel.HomePanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final JPanel mainPanel;

    private final HomePanel homePanel;
    private final CreateNetworkConfigurationPanel createNetworkConfigurationPanel;

    public MainFrame() {
        /* Configure frame */
        this.setTitle("NerdyGadgets Network Application");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setIconImage(new ImageIcon("src\\main\\resources\\icon.png").getImage());

        /* Create panels */

        // Create home panel
        homePanel = new HomePanel(this);

        // Create, configure and add main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.add(mainPanel);

        // Create network configuration creation panel
        createNetworkConfigurationPanel = new CreateNetworkConfigurationPanel(this);

        openHomePanel(); // Load home panel

        // Set starting location on screen
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - mainPanel.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - mainPanel.getHeight()) / 2);
        this.setLocation(x, y);

        this.setVisible(true); // Make visible
    }

    public void openHomePanel() {
        clearFrame();
        mainPanel.add(homePanel);
        mainPanel.repaint();
        this.pack();
    }

    public void openCreateNetworkConfigurationPanel() {
        clearFrame();
        mainPanel.add(createNetworkConfigurationPanel);
        mainPanel.repaint();
        this.pack();
    }

    private void clearFrame() {
        mainPanel.removeAll();
        mainPanel.revalidate();
    }
}
