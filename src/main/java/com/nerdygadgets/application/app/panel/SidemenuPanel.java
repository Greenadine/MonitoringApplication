package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.frame.MainFrame;
import com.nerdygadgets.application.util.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SidemenuPanel extends JPanel {

    private final MainFrame mainFrame;

    public final static int PANEL_WIDTH = 200;

    public SidemenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        // Create and configure panel
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.DARK_GRAY));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Create and add open network monitor button to panel
        JButton openNetworkMonitorButton = newButton("Network Monitor", this::actionOpenNetworkMonitor);
        this.add(openNetworkMonitorButton);

        // Create and add create network design button to panel
        JButton createNetworkDesignButton = newButton("Create Network Design", this::actionCreateNetworkConfiguration);
        this.add(createNetworkDesignButton);

        // Create and add open network design button to panel
        JButton openNetworkDesignButton = newButton("Open Network Design", this::actionOpenNetworkConfiguration);
        this.add(openNetworkDesignButton);

        // Create and add open availability calculator tool button to panel
        JButton openAvailabilityCalculatorToolButton = newButton("Availability Calculator", this::actionOpenAvailabilityCalulator);
        this.add(openAvailabilityCalculatorToolButton);

        // Create and add home button to panel
        JButton homeButton = newButton("Home", this::actionHome);
        this.add(homeButton);
    }

    private JButton newButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        Utils.setSizeAll(button, new Dimension(PANEL_WIDTH, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(listener);

        return button;
    }

    /* Button Actions */

    private void actionOpenNetworkMonitor(ActionEvent event) {
        // TODO
    }

    private void actionCreateNetworkConfiguration(ActionEvent event) {
        mainFrame.openCreateNetworkConfigurationPanel();
    }

    private void actionOpenNetworkConfiguration(ActionEvent event) {
        // TODO
    }

    private void actionOpenAvailabilityCalulator(ActionEvent event) {
        // TODO
    }

    private void actionHome(ActionEvent event) {
        mainFrame.openHomePanel();
    }
}
