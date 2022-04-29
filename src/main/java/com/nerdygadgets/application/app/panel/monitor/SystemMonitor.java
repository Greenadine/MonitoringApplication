package com.nerdygadgets.application.app.panel.monitor;

import com.nerdygadgets.application.Main;
import com.nerdygadgets.application.app.panel.ApplicationPanel;
import com.nerdygadgets.application.app.screen.ApplicationScreen;
import com.nerdygadgets.application.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.TimerTask;

public class SystemMonitor extends ApplicationPanel {

    private JLabel systemUptimeValue;
    private JLabel systemCpuUsageValue;

    private JPanel disksTableContentPanel;

    private UptimeUpdater uptimeUpdater;
    private CpuUsageUpdater cpuUsageUpdater;
    private DisksUpdater disksUpdater;

    public SystemMonitor(@NotNull final ApplicationScreen applicationScreen, @NotNull final String systemName) {
        super(applicationScreen);

        // Configure panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(0, 5, 5, 5));
        this.setBackground(Colors.MAIN_BACKGROUND);

        // Add system name
        JPanel systemNameLabelPanel = new JPanel();
        systemNameLabelPanel.setLayout(new FlowLayout());
        systemNameLabelPanel.setBackground(Colors.MONITOR_BACKGROUND);
        this.add(systemNameLabelPanel);

        JLabel systemNameLabel = new JLabel(systemName, SwingConstants.LEFT);
        systemNameLabel.setFont(Fonts.MONITOR_TITLE);
        systemNameLabelPanel.add(systemNameLabel);

        addSystemStatus();
        addStorageDisksInformation();
    }

    private void addSystemStatus() {
        // Create content panels
        JPanel newSystemInformationPanel = new JPanel();
        newSystemInformationPanel.setLayout(new GridLayout(1, 1));
        newSystemInformationPanel.setBackground(Colors.MONITOR_BACKGROUND);
        this.add(newSystemInformationPanel);

        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));
        labelsPanel.setBorder(new EmptyBorder(2, 5, 2, 5));
        labelsPanel.setBackground(Colors.MONITOR_VALUE_LABEL);
        newSystemInformationPanel.add(labelsPanel);

        JPanel valuesPanel = new JPanel();
        valuesPanel.setLayout(new BoxLayout(valuesPanel, BoxLayout.Y_AXIS));
        valuesPanel.setBorder(new EmptyBorder(2, 5, 2, 5));
        valuesPanel.setBackground(Colors.MONITOR_VALUE_CONTENT);
        newSystemInformationPanel.add(valuesPanel);

        // Add system uptime
        JLabel systemUptimeLabel = new JLabel("Uptime");
        systemUptimeLabel.setFont(Fonts.MONITOR_LABEL_BOLD);
        systemUptimeLabel.setBackground(Colors.MONITOR_VALUE_LABEL);
        labelsPanel.add(systemUptimeLabel);

        systemUptimeValue = new JLabel("Loading...", SwingConstants.RIGHT);
        systemUptimeValue.setFont(Fonts.MONITOR_LABEL);
        systemUptimeValue.setBackground(Colors.MONITOR_VALUE_CONTENT);
        systemUptimeValue.setAlignmentX(RIGHT_ALIGNMENT);
        valuesPanel.add(systemUptimeValue);

        // Add CPU usage

        JLabel systemCpuUsageLabel = new JLabel("CPU Usage");
        systemCpuUsageLabel.setFont(Fonts.MONITOR_LABEL_BOLD);
        labelsPanel.add(systemCpuUsageLabel);

        systemCpuUsageValue = new JLabel("Loading...", SwingConstants.RIGHT);
        systemCpuUsageValue.setFont(Fonts.MONITOR_LABEL);
        systemCpuUsageValue.setBackground(Colors.MONITOR_VALUE_CONTENT);
        systemCpuUsageValue.setAlignmentX(RIGHT_ALIGNMENT);
        valuesPanel.add(systemCpuUsageValue);
    }

    private void addStorageDisksInformation() {
        // Add header
        JPanel disksHeaderPanel = new JPanel();
        disksHeaderPanel.setLayout(new FlowLayout());
        disksHeaderPanel.setBackground(Colors.MONITOR_BACKGROUND);
        this.add(disksHeaderPanel);

        JLabel disksHeaderLabel = new JLabel("Disks Information");
        disksHeaderLabel.setFont(Fonts.MONITOR_SUBTITLE);
        disksHeaderPanel.add(disksHeaderLabel);

        /* --- Create and populate table header panel --- */
        JPanel disksTableHeaderPanel = new JPanel();
        disksTableHeaderPanel.setLayout(new GridLayout(1, 4));
        disksTableHeaderPanel.setBorder(new EmptyBorder(2, 5, 0, 5));
        disksTableHeaderPanel.setBackground(Colors.MONITOR_TABLE_HEADER);
        this.add(disksTableHeaderPanel);

        // Add disk name header
        JLabel diskNameHeaderLabel = new JLabel("Name");
        diskNameHeaderLabel.setFont(Fonts.MONITOR_TABLE_HEADER);
        disksTableHeaderPanel.add(diskNameHeaderLabel);

        // Add disk total space header
        JLabel diskTotalSpaceHeaderLabel = new JLabel("Total Space", SwingConstants.RIGHT);
        diskTotalSpaceHeaderLabel.setFont(Fonts.MONITOR_TABLE_HEADER);
        diskTotalSpaceHeaderLabel.setBorder(new EmptyBorder(0, 0, 0, 15));
        disksTableHeaderPanel.add(diskTotalSpaceHeaderLabel);

        // Add disk name header
        JLabel diskFreeSpaceHeaderLabel = new JLabel("Free Space", SwingConstants.RIGHT);
        diskFreeSpaceHeaderLabel.setFont(Fonts.MONITOR_TABLE_HEADER);
        disksTableHeaderPanel.add(diskFreeSpaceHeaderLabel);

        // Add disk space in use header
        JLabel diskSpaceInUseHeaderLabel = new JLabel("In Use", SwingConstants.RIGHT);
        diskSpaceInUseHeaderLabel.setFont(Fonts.MONITOR_TABLE_HEADER);
        disksTableHeaderPanel.add(diskSpaceInUseHeaderLabel);

        /* --- Create and populate table content panel --- */
        emptyDisksTable();
    }

    private void emptyDisksTable() {
        if (disksTableContentPanel != null) {
            disksTableContentPanel.removeAll();
        } else {
            disksTableContentPanel = new JPanel();
            this.add(disksTableContentPanel);
        }

        disksTableContentPanel.setLayout(new FlowLayout());

        JLabel loadingLabel = new JLabel("Loading...");
        loadingLabel.setFont(Fonts.MONITOR_TABLE_CONTENT);
        disksTableContentPanel.add(loadingLabel);
    }

    @Override
    public void onDisplay() {
        uptimeUpdater = Tasker.scheduleTask(new UptimeUpdater(), 0, 60); // 1-minute intervals
        cpuUsageUpdater = Tasker.scheduleTask(new CpuUsageUpdater(), 0, 1); // 1-second intervals
        disksUpdater = Tasker.scheduleTask(new DisksUpdater(), 0, 300); // 5-minute intervals
    }

    @Override
    public void onHide() {
        uptimeUpdater.cancel();
        cpuUsageUpdater.cancel();
        disksUpdater.cancel();

        uptimeUpdater = null;
        cpuUsageUpdater = null;
        disksUpdater = null;

        // Clear values
        systemUptimeValue.setText("Loading...");
        systemCpuUsageValue.setText("Loading...");

        // Clear disks table
        emptyDisksTable();
    }

    protected void addComponent(Component component) {
        this.add(component);
    }

    protected void removeComponent(Component component) {
        this.remove(component);
    }

    private class CpuUsageUpdater extends TimerTask {

        @Override
        public void run() {
            // Set CPU load
            systemCpuUsageValue.setText(NetworkMonitor.getCpuLoad() + "%");
        }
    }

    private class UptimeUpdater extends TimerTask {

        @Override
        public void run() {
            // Set uptime
            Duration duration = Duration.between(NetworkMonitor.getSystemLastBootUpTime(), Instant.now());
            systemUptimeValue.setText(formatDuration(duration));
        }

        private String formatDuration(Duration duration) {
            StringJoiner joiner = new StringJoiner(" ");

            String str = DurationFormatUtils.formatDuration(duration.toMillis(), "d' days 'H' hours 'm' minutes'");
            String[] strArr = str.split("\\s+");

            try {
                int days = Integer.parseInt(strArr[0]);
                if (days > 0) {
                    joiner.add(days + " days");
                }

                int hours = Integer.parseInt(strArr[2]);
                if (hours > 0) {
                    joiner.add(hours + " hours");
                }

                int minutes = Integer.parseInt(strArr[4]);
                if (minutes > 0) {
                    joiner.add(minutes + " minutes");
                }
            } catch (NumberFormatException ex) {
                Logger.error(ex, "Failed to format system uptime.");
            }

            String result = joiner.toString();
            result = StringUtils.replaceOnce(result, "1 minutes", "1 minute");
            result = StringUtils.replaceOnce(result, "1 hours", "1 hour");
            result = StringUtils.replaceOnce(result, "1 days", "1 day");
            return result;
        }
    }

    private class DisksUpdater extends TimerTask {

        @Override
        public void run() {
            removeComponent(disksTableContentPanel); // We have to first remove and then re-add the component for it to update

            disksTableContentPanel = new JPanel();
            disksTableContentPanel.setLayout(new GridLayout(1, 4));
            disksTableContentPanel.setBorder(new EmptyBorder(2, 5, 0, 5));
            disksTableContentPanel.setBackground(Colors.MONITOR_TABLE_CONTENT);
            addComponent(disksTableContentPanel);

            ArrayList<NetworkMonitor.DiskResult> disks = NetworkMonitor.getDisks();
            ((GridLayout) disksTableContentPanel.getLayout()).setRows(disks.size()); // Set amount of rows equal to amount of disks

            for (NetworkMonitor.DiskResult disk : disks) {
                // Add disk name
                JLabel diskNameLabel = new JLabel(disk.getName());
                diskNameLabel.setFont(Fonts.MONITOR_TABLE_CONTENT);
                disksTableContentPanel.add(diskNameLabel);

                // Add disk total space
                JLabel diskTotalSpaceLabel = new JLabel(String.format("%.2f GB", disk.getTotalSpace()), SwingConstants.RIGHT);
                diskTotalSpaceLabel.setFont(Fonts.MONITOR_TABLE_CONTENT);
                diskTotalSpaceLabel.setBorder(new EmptyBorder(0, 0, 0, 15));
                disksTableContentPanel.add(diskTotalSpaceLabel);

                // Add disk free space
                JLabel diskFreeSpaceLabel = new JLabel(String.format("%.2f GB", disk.getFreeSpace()), SwingConstants.RIGHT);
                diskFreeSpaceLabel.setFont(Fonts.MONITOR_TABLE_CONTENT);
                disksTableContentPanel.add(diskFreeSpaceLabel);

                // Add disk space in use
                JLabel diskSpaceInUseLabel = new JLabel(String.format("%.2f%%", 100 - (disk.getFreeSpace() / disk.getTotalSpace() * 100)), SwingConstants.RIGHT);
                diskSpaceInUseLabel.setFont(Fonts.MONITOR_TABLE_CONTENT);
                disksTableContentPanel.add(diskSpaceInUseLabel);
            }

            Main.getApplicationFrame().pack();
        }
    }
}
