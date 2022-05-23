package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.Main;
import com.nerdygadgets.application.Settings;
import com.nerdygadgets.application.app.component.LineGraphComponent;
import com.nerdygadgets.application.app.component.WrappedJLabel;
import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.concurrent.ScheduledFuture;

/**
 * A {@link JPanel} for displaying a (remote) system's uptime, CPU usage and storage disk(s) information.
 *
 * @author Kevin Zuman
 */
public class SystemMonitorPanel extends ApplicationPanel {

    private final String systemAddress;
    private final String systemUsername;
    private final String systemPassword;

    private JLabel systemNameLabel;
    private WrappedJLabel systemUptimeValue;
    private LineGraphComponent cpuUsageGraphPanel;
    private JPanel disksTableContentPanel;

    private ScheduledFuture<?> uptimeUpdater;
    private ScheduledFuture<?> cpuUsageUpdater;
    private ScheduledFuture<?> disksUpdater;
    private ScheduledFuture<?> systemStatusChecker;

    private boolean previousOnlineStatus;
    private boolean onlineStatus;

    public SystemMonitorPanel(@NotNull final ApplicationScreen applicationScreen, @NotNull final String systemName) {
        this(applicationScreen, systemName, null, null, null);
    }

    public SystemMonitorPanel(@NotNull final ApplicationScreen applicationScreen, @NotNull final String systemName, @Nullable final String systemAddress, @Nullable final String systemUsername, @Nullable final String systemPassword) {
        super(applicationScreen);
        this.systemAddress = systemAddress;
        this.systemUsername = systemUsername;
        this.systemPassword = systemPassword;

        // Configure panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(0, 5, 5, 5));
        this.setBackground(Colors.MAIN_BACKGROUND);

        // Add system name
        addSystemName(systemName);
        addSystemStatus();
        addCpuUsageGraph();
        addStorageDisksInformation();

        // Schedule uptime updater & system status checker at 1-minute intervals
        if (Settings.isBackgroundMonitoringEnabled()) {
            // TODO when implementing setting
        }
    }

    /**
     * Adds the system name as a header of the panel.
     *
     * @param systemName The name of the system.
     */
    private void addSystemName(String systemName) {
        final JPanel systemNameLabelPanel = new JPanel();
        systemNameLabelPanel.setLayout(new FlowLayout());
        systemNameLabelPanel.setBackground(Colors.MONITOR_BACKGROUND);
        this.add(systemNameLabelPanel);

        systemNameLabel = new JLabel(systemName, SwingUtils.getIconFromResource("status-neutral.png"), SwingConstants.LEADING);
        systemNameLabel.setFont(Fonts.MONITOR_TITLE);
        systemNameLabelPanel.add(systemNameLabel);
    }

    /**
     * Adds the current total system uptime to the panel.
     */
    private void addSystemStatus() {
        // Create content panels
        final JPanel systemInformationPanel = new JPanel();
        systemInformationPanel.setLayout(new GridBagLayout());
        this.add(systemInformationPanel);
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // Create and add uptime label
        final WrappedJLabel systemUptimeLabel = new WrappedJLabel("Uptime", SwingConstants.LEFT);
        systemUptimeLabel.setFont(Fonts.MONITOR_LABEL_BOLD);
        systemUptimeLabel.getWrapperPanel().setBackground(Colors.MONITOR_VALUE_LABEL);
        systemUptimeLabel.setAlignmentX(LEFT_ALIGNMENT);
        systemUptimeLabel.getWrapperPanel().setAlignmentX(LEFT_ALIGNMENT);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        systemInformationPanel.add(systemUptimeLabel.getWrapperPanel(), constraints);

        // Create and add uptime value label
        systemUptimeValue = new WrappedJLabel("Loading...", SwingConstants.RIGHT);
        systemUptimeValue.setFont(Fonts.MONITOR_LABEL);
        systemUptimeValue.setBackground(Colors.MONITOR_TABLE_CONTENT);
        systemUptimeValue.setAlignmentX(RIGHT_ALIGNMENT);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 3;
        constraints.anchor = GridBagConstraints.EAST;
        systemInformationPanel.add(systemUptimeValue.getWrapperPanel(), constraints);
    }

    /**
     * Adds the CPU usage graph to the panel.
     */
    private void addCpuUsageGraph() {
        // Add header
        final JLabel cpuUsageHeader = new WrappedJLabel("CPU Load", SwingUtils.getIconFromResource("cpu.png"));
        cpuUsageHeader.setFont(Fonts.MONITOR_SUBTITLE);
        cpuUsageHeader.setBorder(new EmptyBorder(5, 0, 5, 0));
        this.add(cpuUsageHeader);

        // Add graph
        cpuUsageGraphPanel = (LineGraphComponent) this.add(new LineGraphComponent(this, 250, 175));
        cpuUsageGraphPanel.setMinValue(0);
        cpuUsageGraphPanel.setMaxValue(100);
        cpuUsageGraphPanel.setMaxPoints(30);
        cpuUsageGraphPanel.setYAxisDivisions(5);
        cpuUsageGraphPanel.setDisplayXAxis(false); // Only draw the Y-axis
        cpuUsageGraphPanel.setYAxisUnit("%");
        cpuUsageGraphPanel.setResetOnHide(true); // Set all values to 0 upon hiding (closing) the panel
    }

    /**
     * Adds the storage disks information to the panel
     */
    private void addStorageDisksInformation() {
        // Add header
        final JPanel disksHeaderPanel = new JPanel();
        disksHeaderPanel.setLayout(new FlowLayout());
        disksHeaderPanel.setBackground(Colors.MONITOR_BACKGROUND);
        this.add(disksHeaderPanel);

        final JLabel disksHeaderLabel = new JLabel("Storage Drives", SwingUtils.getIconFromResource("storage-drive.png"), SwingConstants.LEADING);
        disksHeaderLabel.setFont(Fonts.MONITOR_SUBTITLE);
        disksHeaderPanel.add(disksHeaderLabel);

        /* --- Create and populate table header panel --- */
        final JPanel disksTableHeaderPanel = new JPanel();
        disksTableHeaderPanel.setLayout(new GridLayout(1, 4));
        disksTableHeaderPanel.setBorder(new EmptyBorder(2, 5, 0, 5));
        disksTableHeaderPanel.setBackground(Colors.MONITOR_TABLE_HEADER);
        this.add(disksTableHeaderPanel);

        // Add disk name header
        final JLabel diskNameHeaderLabel = new JLabel("Name");
        diskNameHeaderLabel.setFont(Fonts.MONITOR_TABLE_HEADER);
        disksTableHeaderPanel.add(diskNameHeaderLabel);

        // Add disk total space header
        final JLabel diskTotalSpaceHeaderLabel = new JLabel("Total Space", SwingConstants.RIGHT);
        diskTotalSpaceHeaderLabel.setFont(Fonts.MONITOR_TABLE_HEADER);
        diskTotalSpaceHeaderLabel.setBorder(new EmptyBorder(0, 0, 0, 15));
        disksTableHeaderPanel.add(diskTotalSpaceHeaderLabel);

        // Add disk name header
        final JLabel diskFreeSpaceHeaderLabel = new JLabel("Free Space", SwingConstants.RIGHT);
        diskFreeSpaceHeaderLabel.setFont(Fonts.MONITOR_TABLE_HEADER);
        disksTableHeaderPanel.add(diskFreeSpaceHeaderLabel);

        // Add disk space in use header
        final JLabel diskSpaceInUseHeaderLabel = new JLabel("In Use", SwingConstants.RIGHT);
        diskSpaceInUseHeaderLabel.setFont(Fonts.MONITOR_TABLE_HEADER);
        disksTableHeaderPanel.add(diskSpaceInUseHeaderLabel);

        /* --- Add disks table panel  --- */
        addEmptyDisksTable("Loading...");
    }

    /**
     * Resets the contents of the disk(s) information table.
     */
    private void addEmptyDisksTable(@NotNull final String labelText) {
        if (disksTableContentPanel != null) {
            disksTableContentPanel.removeAll();
        } else {
            disksTableContentPanel = new JPanel();
            this.add(disksTableContentPanel);
        }

        disksTableContentPanel.setLayout(new FlowLayout());

        final JLabel loadingLabel = new JLabel(labelText);
        loadingLabel.setFont(Fonts.MONITOR_TABLE_CONTENT);
        disksTableContentPanel.add(loadingLabel);
    }

    /**
     * Causes the panel to start monitoring the system's status.
     */
    public void startMonitoringSystemStatus() {
        uptimeUpdater = Scheduler.scheduleAtFixedRate(new UptimeUpdater(), 0, 60);
        systemStatusChecker = Scheduler.scheduleAtFixedRate(new UptimeStatusCheck(), 2, 60);
    }

    /**
     * Causes the panel to stop monitoring the system's status.
     */
    public void stopMonitoringSystemStatus() {
        if (uptimeUpdater != null) {
            uptimeUpdater.cancel(true);
            systemStatusChecker.cancel(true);

            uptimeUpdater = null;
            systemStatusChecker = null;
        }

        systemNameLabel.setIcon(SwingUtils.getIconFromResource("status-neutral.png")); // Set status icon to neutral
        systemUptimeValue.setText("Loading...");
    }

    /**
     * Adds a {@link Component} to the panel.
     *
     * @param component The {@code Component}.
     */
    protected void addComponent(Component component) {
        this.add(component);
    }

    /**
     * Removes a {@link Component} from the panel.
     *
     * @param component The {@code Component}.
     */
    protected void removeComponent(Component component) {
        this.remove(component);
    }

    @Override
    public void onShowImpl() {
        // If monitoring in the background is disabled, re-schedule uptime updater and system status checker
        /*if (!Settings.isBackgroundMonitoringEnabled()) {
            startMonitoringSystemStatus();
        }*/
        startMonitoringSystemStatus();

        // Update CPU usage at a 1-second interval
        cpuUsageUpdater = Scheduler.scheduleAtFixedRate(new CpuUsageUpdater(), 0, 1);
        // Update disk(s) information at a 5-minute interval
        disksUpdater = Scheduler.scheduleAtFixedRate(new DisksUpdater(), 0, 300);
    }

    @Override
    public void onHideImpl() {
        // If monitoring in the background is disabled, also cancel uptime updater and system status checker
        /*if (!Settings.isBackgroundMonitoringEnabled()) {
            stopMonitoringSystemStatus();
        }*/
        stopMonitoringSystemStatus();

        // Cancel CPU usage and disks information updaters
        cpuUsageUpdater.cancel(true);
        disksUpdater.cancel(true);
        cpuUsageUpdater = null;
        disksUpdater = null;

        addEmptyDisksTable("Loading..."); // Clear disks table
    }

    /**
     * A {@code Runnable} for periodically checking whether the system's uptime has been retrieved successfully recently, and sending out a warning if not.
     *
     * @author Kevin Zuman
     */
    public class UptimeStatusCheck implements Runnable {

        @Override
        public void run() {
            systemNameLabel.setIcon(SwingUtils.getIconFromResource(String.format("status-%s.png", onlineStatus ? "online" : previousOnlineStatus ? "warning" : "offline")));

            if (!onlineStatus) {
                systemUptimeValue.setText("Can't be reached");

                addEmptyDisksTable("Can't be reached");
                // TODO maybe send some kind of warning for the system being offline, for example through email?
            }
        }
    }

    /**
     * A {@code Runnable} for updating the system's CPU usage.
     *
     * @author Kevin Zuman
     */
    private class CpuUsageUpdater implements Runnable {

        @Override
        public void run() {
            if (systemAddress != null) {
                SystemMonitor.getCpuLoad(systemAddress, systemUsername, systemPassword).ifPresent(cpuUsageGraphPanel::appendValue);
            } else {
                SystemMonitor.getLocalCpuLoad().ifPresent(cpuUsageGraphPanel::appendValue);
            }
        }
    }

    /**
     * A {@code Runnable} for updating the system's uptime.
     *
     * @author Kevin Zuman
     */
    public class UptimeUpdater implements Runnable {

        @Override
        public void run() {
            final Instant lastBootUpTime = systemAddress != null
                    ? SystemMonitor.getLastBootUpTime(systemAddress, systemUsername, systemPassword)
                    : SystemMonitor.getLocalLastBootUpTime();

            previousOnlineStatus = onlineStatus;
            onlineStatus = lastBootUpTime != null;

            // Set uptime
            if (lastBootUpTime != null) {
                final Duration duration = Duration.between(lastBootUpTime, Instant.now());
                systemUptimeValue.setText(formatDuration(duration));
            }
        }

        /**
         * Formats the {@link Duration} between the last boot-up time and now, into days, hours and minutes.
         *
         * @param duration The {@code Duration} between the last boot-up time, and now.
         *
         * @return A {@code String} with days, hours and minutes between the last boot-up time, and now.
         */
        private String formatDuration(Duration duration) {
            final StringJoiner joiner = new StringJoiner(" ");

            final String str = DurationFormatUtils.formatDuration(duration.toMillis(), "d' days 'H' hours 'm' minutes'");
            String[] strArr = str.split("\\s+");

            try {
                final int days = Integer.parseInt(strArr[0]);
                if (days > 0) {
                    joiner.add(days + " days");
                }

                final int hours = Integer.parseInt(strArr[2]);
                if (hours > 0) {
                    joiner.add(hours + " hours");
                }

                final int minutes = Integer.parseInt(strArr[4]);
                if (minutes > 0) {
                    joiner.add(minutes + " minutes");
                }
            } catch (NumberFormatException ex) {
                Logger.error(ex, "Failed to format system uptime.");
            }

            String result = joiner.toString();
            result = StringUtils.replaceOnce(result, " 1 minutes", " 1 minute");
            result = StringUtils.replaceOnce(result, " 1 hours", " 1 hour");
            result = StringUtils.replaceOnce(result, "1 days", " 1 day");
            return result;
        }
    }

    /**
     * A {@code Runnable} for updating the system's disk(s) information.
     *
     * @author Kevin Zuman
     */
    private class DisksUpdater implements Runnable {

        private boolean firstRun = true;

        @Override
        public void run() {
            final ArrayList<SystemMonitor.DiskResult> disks = systemAddress != null
                    ? SystemMonitor.getDisks(systemAddress, systemUsername, systemPassword)
                    : SystemMonitor.getLocalDisks();

            if (!disks.isEmpty()) {
                if (firstRun) {
                    removeComponent(disksTableContentPanel); // We have to first remove and then re-add the component for it to update

                    disksTableContentPanel = new JPanel();
                    disksTableContentPanel.setLayout(new GridLayout(1, 4));
                    disksTableContentPanel.setBorder(new EmptyBorder(2, 5, 0, 5));
                    disksTableContentPanel.setBackground(Colors.MONITOR_TABLE_CONTENT);
                    addComponent(disksTableContentPanel);
                    firstRun = false;

                    ((GridLayout) disksTableContentPanel.getLayout()).setRows(disks.size()); // Set amount of rows equal to amount of disks
                } else {
                    disksTableContentPanel.removeAll();
                }

                for (SystemMonitor.DiskResult disk : disks) {
                    // Add disk name
                    final JLabel diskNameLabel = new JLabel(disk.getName());
                    diskNameLabel.setFont(Fonts.MONITOR_TABLE_CONTENT);
                    disksTableContentPanel.add(diskNameLabel);

                    // Add disk total space
                    final JLabel diskTotalSpaceLabel = new JLabel(String.format("%.2f GB", disk.getTotalSpace()), SwingConstants.RIGHT);
                    diskTotalSpaceLabel.setFont(Fonts.MONITOR_TABLE_CONTENT);
                    diskTotalSpaceLabel.setBorder(new EmptyBorder(0, 0, 0, 15));
                    disksTableContentPanel.add(diskTotalSpaceLabel);

                    // Add disk free space
                    final JLabel diskFreeSpaceLabel = new JLabel(String.format("%.2f GB", disk.getFreeSpace()), SwingConstants.RIGHT);
                    diskFreeSpaceLabel.setFont(Fonts.MONITOR_TABLE_CONTENT);
                    disksTableContentPanel.add(diskFreeSpaceLabel);

                    // Add disk space in use
                    final JLabel diskSpaceInUseLabel = new JLabel(String.format("%.2f%%", 100 - (disk.getFreeSpace() / disk.getTotalSpace() * 100)), SwingConstants.RIGHT);
                    diskSpaceInUseLabel.setFont(Fonts.MONITOR_TABLE_CONTENT);
                    disksTableContentPanel.add(diskSpaceInUseLabel);
                }

                Main.mainWindow.pack();
            }
        }
    }
}
