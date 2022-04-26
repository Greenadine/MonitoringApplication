package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.ApplicationFrame;
import com.nerdygadgets.application.util.Colors;
import com.nerdygadgets.application.util.Fonts;
import com.nerdygadgets.application.util.SwingUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NetworkMonitorScreen extends AbstractApplicationScreen {

    private JPanel sidebar;

    private JLabel networkStatusValue;
    private JLabel networkUptimeValue;
    private JLabel networkCpuUsageValue;

    public NetworkMonitorScreen(@NotNull final ApplicationFrame applicationFrame) {
        super(applicationFrame);

        // Configure screen
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        createHeader();
        createSidebar();
    }

    private void createHeader() {
        // Create and configure panel
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
        header.setBackground(Colors.BACKGROUND);
        header.setPreferredSize(new Dimension(1000, 50));
        this.add(header, BorderLayout.PAGE_START);

        /* Populate panel */

        // Add home button
        JButton homeButton = SwingUtils.createButton("Home", new ImageIcon("assets\\icons\\home.png"), this::actionReturnToHome);
        homeButton.setBackground(Colors.ACCENT);
        homeButton.setForeground(Color.WHITE);
        homeButton.setIconTextGap(15);
        homeButton.setBorder(new EmptyBorder(15, 10, 15, 10));
        header.add(homeButton);

        SwingUtils.addVerticalSeparator(header);  // Add separator

        // Add title label
        JLabel titleLabel = new JLabel("Network Monitor");
        titleLabel.setFont(Fonts.TITLE);
        titleLabel.setBorder(new EmptyBorder(5, 5, 5, 400)); // TODO center label properly (without this)
        header.add(titleLabel);
    }

    private void createSidebar() {
        sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(Colors.BACKGROUND);
        this.add(sidebar, BorderLayout.LINE_START);

        addNetworkInformation();
        addStorageInformation();
    }

    private void addNetworkInformation() {
        // Create panel
        JPanel networkPanel = new JPanel();
        networkPanel.setLayout(new BoxLayout(networkPanel, BoxLayout.Y_AXIS));
        networkPanel.setBackground(Colors.BACKGROUND);
        sidebar.add(networkPanel);

        // Add header
        JLabel networkTitleLabel = new JLabel("Network");
        networkTitleLabel.setVerticalAlignment(SwingConstants.CENTER);
        networkTitleLabel.setFont(Fonts.TITLE);
        networkPanel.add(networkTitleLabel);

        // Create content panel
        JPanel networkInformationPanel = new JPanel();
        networkInformationPanel.setLayout(new GridLayout(3, 2));
        networkInformationPanel.setBackground(Colors.BACKGROUND_ACCENT);
        networkPanel.add(networkInformationPanel);

        // Add network status
        JLabel networkStatusLabel = new JLabel("Status");
        networkStatusLabel.setFont(Fonts.PARAGRAPH);
        networkInformationPanel.add(networkStatusLabel);

        networkStatusValue = new JLabel("Unknown");
        networkStatusValue.setFont(Fonts.PARAGRAPH);
        networkInformationPanel.add(networkStatusValue);

        // Add network uptime
        JLabel networkUptimeLabel = new JLabel("Uptime");
        networkUptimeLabel.setFont(Fonts.PARAGRAPH);
        networkInformationPanel.add(networkUptimeLabel);

        networkUptimeValue = new JLabel("Unknown");
        networkUptimeValue.setFont(Fonts.PARAGRAPH);
        networkInformationPanel.add(networkUptimeValue);

        // Add CPU usage
        JLabel networkCpuUsageLabel = new JLabel("CPU Usage");
        networkCpuUsageLabel.setFont(Fonts.PARAGRAPH);
        networkInformationPanel.add(networkCpuUsageLabel);

        networkCpuUsageValue = new JLabel("0%");
        networkCpuUsageValue.setFont(Fonts.PARAGRAPH);
        networkInformationPanel.add(networkCpuUsageValue);
    }

    private void addStorageInformation() {
        // Create panel
        JPanel storagePanel = new JPanel();
        storagePanel.setLayout(new BoxLayout(storagePanel, BoxLayout.Y_AXIS));
        storagePanel.setBackground(Colors.BACKGROUND);
        sidebar.add(storagePanel);

        // Add header
        JLabel storageTitleLabel = new JLabel("Storage", SwingConstants.LEFT);
        storageTitleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        storageTitleLabel.setFont(Fonts.TITLE);
        storagePanel.add(storageTitleLabel);

        // Create content panel
        JPanel storageInformationPanel = new JPanel();
        storageInformationPanel.setLayout(new GridLayout(3, 2));
        storageInformationPanel.setForeground(Colors.BACKGROUND_ACCENT);
        storagePanel.add(storageInformationPanel);
    }

    /* Button Actions */

    /**
     * The {@link Action} that is performed when the "Return to home" button is pressed.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionReturnToHome(ActionEvent event) {
        applicationFrame.getHomeScreen().open();
    }

    @Override
    public void preOpen() {
        // TODO
    }
}
