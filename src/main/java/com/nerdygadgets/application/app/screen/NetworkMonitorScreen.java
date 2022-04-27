package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.ApplicationFrame;
import com.nerdygadgets.application.util.*;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.TimerTask;

public class NetworkMonitorScreen extends AbstractApplicationScreen {

    private JPanel sidebar;

    private JLabel networkStatusValue;
    private JLabel networkUptimeValue;
    private JLabel networkCpuUsageValue;

    private MonitorUpdater updater;

    public NetworkMonitorScreen(@NotNull final ApplicationFrame applicationFrame) throws IOException {
        super(applicationFrame);

        this.updater = new MonitorUpdater();

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
        header.setBorder(new MatteBorder(0, 0, 5, 0, Colors.BACKGROUND_ACCENT));
        header.setBackground(Colors.BACKGROUND);
        header.setPreferredSize(new Dimension(1250, 50));
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
        titleLabel.setBorder(new EmptyBorder(5, 5, 5, 500)); // TODO center label properly (without this)
        header.add(titleLabel);
    }

    private void createSidebar() throws IOException {
        sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new MatteBorder(0, 3, 3, 3, Colors.BACKGROUND_ACCENT));
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
        networkInformationPanel.setLayout(new GridLayout(2, 2));
        networkInformationPanel.setBackground(Colors.TABLE_CONTENT);
        networkPanel.add(networkInformationPanel);

        // Add system uptime
        JPanel networkUptimeLabelPanel = new JPanel();
        networkUptimeLabelPanel.setLayout(new BoxLayout(networkUptimeLabelPanel, BoxLayout.X_AXIS));
        networkUptimeLabelPanel.setBorder(new EmptyBorder(2, 5, 0, 0));
        networkUptimeLabelPanel.setBackground(Colors.TABLE_HEADER);
        networkInformationPanel.add(networkUptimeLabelPanel);

        JLabel networkUptimeLabel = new JLabel("Uptime");
        networkUptimeLabel.setFont(Fonts.SIDEBAR_HEADER);
        networkUptimeLabelPanel.add(networkUptimeLabel);

        networkUptimeValue = new JLabel("Loading...", SwingConstants.RIGHT);
        networkUptimeValue.setFont(Fonts.SIDEBAR_CONTENT);
        networkInformationPanel.add(networkUptimeValue);

        // Add CPU load
        JPanel networkCpuUsageLabelPanel = new JPanel();
        networkCpuUsageLabelPanel.setLayout(new BoxLayout(networkCpuUsageLabelPanel, BoxLayout.X_AXIS));
        networkCpuUsageLabelPanel.setBorder(new EmptyBorder(0, 5, 2, 0));
        networkCpuUsageLabelPanel.setBackground(Colors.TABLE_HEADER);
        networkInformationPanel.add(networkCpuUsageLabelPanel);

        JLabel networkCpuUsageLabel = new JLabel("CPU Usage");
        networkCpuUsageLabel.setFont(Fonts.SIDEBAR_HEADER);
        networkCpuUsageLabelPanel.add(networkCpuUsageLabel);

        networkCpuUsageValue = new JLabel("Loading...", SwingConstants.RIGHT);
        networkCpuUsageValue.setFont(Fonts.SIDEBAR_CONTENT);
        networkInformationPanel.add(networkCpuUsageValue);

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

        JLabel storageTitleLabel = new JLabel("Disks");
        storageTitleLabel.setFont(Fonts.TITLE);
        storageHeaderPanel.add(storageTitleLabel);

        NetworkMonitoringResult monitoringResult = NetworkMonitoring.getResult();

        /* ----- Add table headers ----- */
        JPanel storageInfoTableHeaderPanel = new JPanel();
        storageInfoTableHeaderPanel.setLayout(new GridLayout(1, 4));
        storageInfoTableHeaderPanel.setBorder(new EmptyBorder(2, 5, 0, 5));
        storageInfoTableHeaderPanel.setBackground(Colors.TABLE_HEADER);
        storagePanel.add(storageInfoTableHeaderPanel);

        // Add disk name header
        JLabel diskNameHeaderLabel = new JLabel("Name");
        diskNameHeaderLabel.setFont(Fonts.TABLE_HEADER);
        storageInfoTableHeaderPanel.add(diskNameHeaderLabel);

        // Add disk total space header
        JLabel diskTotalSpaceHeaderLabel = new JLabel("Total Space", SwingConstants.RIGHT);
        diskTotalSpaceHeaderLabel.setFont(Fonts.TABLE_HEADER);
        diskTotalSpaceHeaderLabel.setBorder(new EmptyBorder(0, 0, 0, 15));
        storageInfoTableHeaderPanel.add(diskTotalSpaceHeaderLabel);

        // Add disk free space header
        JLabel diskSpaceInUseHeaderLabel = new JLabel("Free Space", SwingConstants.RIGHT);
        diskSpaceInUseHeaderLabel.setFont(Fonts.TABLE_HEADER);
        storageInfoTableHeaderPanel.add(diskSpaceInUseHeaderLabel);

        // Add disk space in use header
        JLabel diskSpaceUsedHeaderLabel = new JLabel("% Used", SwingConstants.RIGHT);
        diskSpaceUsedHeaderLabel.setFont(Fonts.TABLE_HEADER);
        storageInfoTableHeaderPanel.add(diskSpaceUsedHeaderLabel);

        /* ----- Add table content ----- */
        JPanel storageInfoTableContentPanel = new JPanel();
        storageInfoTableContentPanel.setLayout(new GridLayout(monitoringResult.getDisks().size(), 4));
        storageInfoTableContentPanel.setBorder(new EmptyBorder(0, 5, 2, 5));
        storageInfoTableContentPanel.setBackground(Colors.TABLE_CONTENT);
        storagePanel.add(storageInfoTableContentPanel);

        // Create content panel
        /*JPanel storageInformationPanel = new JPanel();
        storageInformationPanel.setLayout(new GridLayout(monitoringResult.getDisks().size() + 1, 4));
        storageInformationPanel.setBorder(new EmptyBorder(2, 5, 2, 5));
        storageInformationPanel.setBackground(Colors.BACKGROUND_ACCENT);
        storagePanel.add(storageInformationPanel);*/

        for (NetworkMonitoringResult.DiskResult diskResult : monitoringResult.getDisks()) {
            JLabel diskNameLabel = new JLabel(diskResult.getName());
            diskNameLabel.setFont(Fonts.TABLE_CONTENT);
            storageInfoTableContentPanel.add(diskNameLabel);

            JLabel diskTotalSpaceLabel = new JLabel(String.format("%.2f GB", diskResult.getTotalSpace()), SwingConstants.RIGHT);
            diskTotalSpaceLabel.setBorder(new EmptyBorder(0, 0, 0, 15));
            diskTotalSpaceLabel.setFont(Fonts.TABLE_CONTENT);
            storageInfoTableContentPanel.add(diskTotalSpaceLabel);

            JLabel diskSpaceInUseLabel = new JLabel(String.format("%.2f GB", diskResult.getFreeSpace()), SwingConstants.RIGHT);
            diskSpaceInUseLabel.setFont(Fonts.TABLE_CONTENT);
            storageInfoTableContentPanel.add(diskSpaceInUseLabel);

            JLabel diskSpaceUsedLabel = new JLabel(String.format("%.2f%%", 100 - (diskResult.getFreeSpace() / diskResult.getTotalSpace()) * 100), SwingConstants.RIGHT);
            diskSpaceUsedLabel.setFont(Fonts.TABLE_CONTENT);
            storageInfoTableContentPanel.add(diskSpaceUsedLabel);
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
        updater = new MonitorUpdater();
        Tasker.scheduleTask(updater, 0, 500);
    }

    @Override
    public void postClose() {
        updater.cancel();

        // Clear values
        networkUptimeValue.setText("Loading...");
        networkCpuUsageValue.setText("Loading...");
    }

    private class MonitorUpdater extends TimerTask {

        @SuppressWarnings("ConstantConditions")
        @Override
        public void run() {
            try {
                NetworkMonitoringResult result = NetworkMonitoring.getResult();

                networkCpuUsageValue.setText(result.getCpuLoad() + "%"); // Set CPU load

                // Set uptime
                Duration duration = Duration.between(result.getLastBootUpTime(), Instant.now());
                networkUptimeValue.setText(DurationFormatUtils.formatDuration(duration.toMillis(), "H:mm:ss", true));
            } catch (IOException ignored) { }
        }
    }
}
