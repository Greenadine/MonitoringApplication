package com.nerdygadgets.application.app.frame;

import com.nerdygadgets.application.app.panel.CreateNetworkConfigurationPanel;
import com.nerdygadgets.application.app.panel.HomePanel;
import com.nerdygadgets.application.app.panel.SidemenuPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final JPanel mainPanel;

    private final HomePanel homePanel;
    private final SidemenuPanel sidemenuPanel;
    private final CreateNetworkConfigurationPanel createNetworkConfigurationPanel;

    public final static int FRAME_WIDTH = 1440;
    public final static int FRAME_HEIGHT = 810;

    /**
     * Width of the body of the app, which is equal to the total application width - the width of the sidebar menu.
     */
    public final static int BODY_WIDTH = FRAME_WIDTH - SidemenuPanel.PANEL_WIDTH;

    public MainFrame() {
        // Configure frame
        this.setTitle("NerdyGadgets Network Monitor");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setResizable(false);

        /* Create panels */

        // Create, configure and add main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.add(mainPanel);

        // Create menu panel
        sidemenuPanel = new SidemenuPanel(this);

        // Create home panel
        homePanel = new HomePanel(this);

        // Create network configuration creation panel
        createNetworkConfigurationPanel = new CreateNetworkConfigurationPanel(this);

        /* Load home panel */
        openHomePanel();
        this.setVisible(true);
    }

    public void openHomePanel() {
        clearFrame();
        mainPanel.add(homePanel);
        mainPanel.repaint();
    }

    public void openCreateNetworkConfigurationPanel() {
        clearFrame();
        mainPanel.add(createNetworkConfigurationPanel);
        mainPanel.repaint();
    }

    private void clearFrame() {
        mainPanel.removeAll();
        mainPanel.revalidate();
        mainPanel.add(sidemenuPanel);
    }
}
