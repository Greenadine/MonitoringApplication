package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.ApplicationFrame;
import com.nerdygadgets.application.util.Fonts;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NetworkMonitorScreen extends AbstractApplicationScreen {

    private JPanel sidebar;

    public NetworkMonitorScreen(@NotNull final ApplicationFrame applicationFrame) {
        super(applicationFrame);

        // Configure main panel
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        createSidebar();
    }

    private void createSidebar() {
        sidebar = new JPanel();
        this.add(sidebar, BorderLayout.LINE_START);

        addNetworkInformation();
    }

    private void addNetworkInformation() {
        // Create panel
        JPanel networkPanel = new JPanel();
        networkPanel.setLayout(new BoxLayout(networkPanel, BoxLayout.Y_AXIS));
        sidebar.add(networkPanel);

        // Add header
        JLabel networkTitleLabel = new JLabel("Network");
        networkTitleLabel.setVerticalAlignment(SwingConstants.CENTER);
        networkTitleLabel.setFont(Fonts.TITLE);
        networkPanel.add(networkTitleLabel);

        JPanel networkInformationPanel = new JPanel();
        networkInformationPanel.setLayout(new GridLayout(3, 2));
        networkPanel.add(networkInformationPanel);

        // Add network status
        JLabel networkStatusLabel = new JLabel("Status");
        networkStatusLabel.setFont(Fonts.PARAGRAPH);
        networkInformationPanel.add(networkStatusLabel);

        JLabel networkStatusValueLabel = new JLabel("Unknown");
        networkStatusValueLabel.setFont(Fonts.PARAGRAPH);
        networkInformationPanel.add(networkStatusValueLabel);

        // Add network
        JLabel networkUptimeLabel = new JLabel("Uptime");
        networkUptimeLabel.setFont(Fonts.PARAGRAPH);
        networkInformationPanel.add(networkUptimeLabel);

        JLabel networkCpuUsageLabel = new JLabel("CPU Usage (%)");
        networkCpuUsageLabel.setFont(Fonts.PARAGRAPH);
        networkInformationPanel.add(networkCpuUsageLabel);

        JLabel storageTitleLabel = new JLabel("Storage");
        storageTitleLabel.setFont(Fonts.TITLE);
        networkInformationPanel.add(storageTitleLabel);
    }

    @Override
    public void preOpen() {
        // TODO
    }
}
