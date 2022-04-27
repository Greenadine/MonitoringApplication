package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.ApplicationFrame;
import com.nerdygadgets.application.util.*;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

public class NetworkMonitorScreen extends AbstractApplicationScreen {

    private JPanel sidebar;

    private JLabel networkStatusValue;
    private JLabel networkUptimeValue;
    private JLabel networkCpuUsageValue;

    private ArrayList<String> test;

    public NetworkMonitorScreen(@NotNull final ApplicationFrame applicationFrame) throws IOException {
        super(applicationFrame);

        // Configure screen
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        createHeader();
        createSidebar();
    }

    private void createHeader() throws IOException {
        // Create and configure panel
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
        header.setBackground(Colors.BACKGROUND);
        header.setPreferredSize(new Dimension(1000, 50));
        this.add(header, BorderLayout.PAGE_START);

        /* Populate panel */

        // Add home button
        JButton homeButton = SwingUtils.createButton("Home", new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/assets/icons/home.png"))), this::actionReturnToHome);
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

    private void createSidebar() throws IOException {
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
        JPanel networkHeaderPanel = new JPanel();
        networkHeaderPanel.setLayout(new FlowLayout());
        networkHeaderPanel.setBackground(Colors.BACKGROUND);
        networkPanel.add(networkHeaderPanel);

        JLabel networkTitleLabel = new JLabel("System");
        networkTitleLabel.setFont(Fonts.TITLE);
        networkHeaderPanel.add(networkTitleLabel);

        // Create content panel
        JPanel networkInformationPanel = new JPanel();
        networkInformationPanel.setLayout(new GridLayout(1, 2));
        networkInformationPanel.setBorder(new EmptyBorder(2, 5, 2, 5));
        networkInformationPanel.setBackground(Colors.BACKGROUND_ACCENT);
        networkPanel.add(networkInformationPanel);

        // Add CPU load
        JLabel networkCpuUsageLabel = new JLabel("CPU Usage");
        networkCpuUsageLabel.setFont(Fonts.PARAGRAPH_BIG);
        networkInformationPanel.add(networkCpuUsageLabel);

        networkCpuUsageValue = new JLabel("0%", SwingConstants.RIGHT);
        networkCpuUsageValue.setFont(Fonts.PARAGRAPH_BIG);
        networkInformationPanel.add(networkCpuUsageValue);

        Tasker.scheduleTask(new MonitorUpdater(), 0, 100);

        // TODO this currently displays information about current system, not a remote server

        /*// Add network status
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
        networkInformationPanel.add(networkCpuUsageValue);*/
    }

    private void addStorageInformation() throws IOException {
        // Create panel
        JPanel storagePanel = new JPanel();
        storagePanel.setLayout(new BoxLayout(storagePanel, BoxLayout.Y_AXIS));
        storagePanel.setBackground(Colors.BACKGROUND);
        sidebar.add(storagePanel);

        // Add header
        JPanel storageHeaderPanel = new JPanel();
        storageHeaderPanel.setLayout(new FlowLayout());
        storageHeaderPanel.setBackground(Colors.BACKGROUND);
        storagePanel.add(storageHeaderPanel);

        JLabel storageTitleLabel = new JLabel("Storage");
        storageTitleLabel.setFont(Fonts.TITLE);
        storageHeaderPanel.add(storageTitleLabel);

        NetworkMonitoringResult monitoringResult = NetworkMonitoring.getResult();

        // Create content panel
        JPanel storageInformationPanel = new JPanel();
        storageInformationPanel.setLayout(new GridLayout(monitoringResult.getDisks().size() + 1, 4));
        storageInformationPanel.setBorder(new EmptyBorder(2, 5, 2, 5));
        storageInformationPanel.setBackground(Colors.BACKGROUND_ACCENT);
        storagePanel.add(storageInformationPanel);

        JLabel diskNameHeaderLabel = new JLabel("Name");
        diskNameHeaderLabel.setFont(Fonts.PARAGRAPH);
        storageInformationPanel.add(diskNameHeaderLabel);

        JLabel diskTotalSpaceHeaderLabel = new JLabel("Total Space", SwingConstants.RIGHT);
        diskTotalSpaceHeaderLabel.setFont(Fonts.PARAGRAPH);
        storageInformationPanel.add(diskTotalSpaceHeaderLabel);

        JLabel diskSpaceInUseHeaderLabel = new JLabel("In Use", SwingConstants.RIGHT);
        diskSpaceInUseHeaderLabel.setFont(Fonts.PARAGRAPH);
        storageInformationPanel.add(diskSpaceInUseHeaderLabel);

        JLabel diskSpaceUsedHeaderLabel = new JLabel("% Used", SwingConstants.RIGHT);
        diskSpaceUsedHeaderLabel.setFont(Fonts.PARAGRAPH);
        storageInformationPanel.add(diskSpaceUsedHeaderLabel);


        for (NetworkMonitoringResult.DiskResult diskResult : monitoringResult.getDisks()) {
            JLabel diskNameLabel = new JLabel(diskResult.getName());
            diskNameLabel.setFont(Fonts.PARAGRAPH);
            storageInformationPanel.add(diskNameLabel);

            JLabel diskTotalSpaceLabel = new JLabel(String.format("%.2f GB", diskResult.getTotalSpace()), SwingConstants.RIGHT);
            diskTotalSpaceLabel.setFont(Fonts.PARAGRAPH);
            storageInformationPanel.add(diskTotalSpaceLabel);

            JLabel diskSpaceInUseLabel = new JLabel(String.format("%.2f GB", diskResult.getFreeSpace()), SwingConstants.RIGHT);
            diskSpaceInUseLabel.setFont(Fonts.PARAGRAPH);
            storageInformationPanel.add(diskSpaceInUseLabel);

            JLabel diskSpaceUsedLabel = new JLabel(String.format("%.2f%%", (diskResult.getFreeSpace() / diskResult.getTotalSpace()) * 100), SwingConstants.RIGHT);
            diskSpaceUsedLabel.setFont(Fonts.PARAGRAPH);
            storageInformationPanel.add(diskSpaceUsedLabel);
        }
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

    private class MonitorUpdater extends TimerTask {

        @Override
        public void run() {
            try {
                NetworkMonitoringResult result = NetworkMonitoring.getResult();

                networkCpuUsageValue.setText(result.getCpuLoad() + "%");
            } catch (IOException ignored) { }
        }
    }
}
