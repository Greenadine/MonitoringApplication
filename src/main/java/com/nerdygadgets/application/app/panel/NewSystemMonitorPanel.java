package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.Main;
import com.nerdygadgets.application.app.component.LineGraphComponent;
import com.nerdygadgets.application.app.component.WrappedJLabel;
import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.util.Colors;
import com.nerdygadgets.application.util.Fonts;
import com.nerdygadgets.application.util.SwingUtils;
import com.nerdygadgets.application.util.SystemMonitor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.Instant;

public class NewSystemMonitorPanel extends ApplicationPanel {

    private WrappedJLabel systemNameLabel;
    private WrappedJLabel systemUptimeValue;
    private LineGraphComponent cpuUsageGraphPanel;
    private JPanel disksTableContentPanel;

    private boolean previousOnlineStatus; // TODO
    private boolean onlineStatus;

    public NewSystemMonitorPanel(@NotNull final ApplicationScreen parentScreen, @NotNull final String systemName) {
        super(parentScreen);

        // Configure panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(0, 5, 5, 5));
        this.setBackground(Colors.MAIN_BACKGROUND);

        // Populate panel
        addSystemName(systemName);
        addSystemUptime();
        addCpuUsageGraph();
        addStorageDisksInformationTable();
    }

    /**
     * Adds the system name as a header of the panel.
     *
     * @param systemName The name of the system.
     */
    private void addSystemName(final String systemName) {
        systemNameLabel = new WrappedJLabel(systemName);
        systemNameLabel.getWrapperPanel().setBackground(Colors.MONITOR_BACKGROUND);
        systemNameLabel.setFont(Fonts.MONITOR_TITLE);
        this.add(systemNameLabel);
    }

    /**
     * Adds the components for displaying system uptime.
     */
    private void addSystemUptime() {
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
        constraints.weightx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        systemInformationPanel.add(systemUptimeLabel.getWrapperPanel(), constraints);

        // Create and add uptime value label
        systemUptimeValue = new WrappedJLabel("Loading...", SwingConstants.RIGHT);
        systemUptimeValue.setFont(Fonts.MONITOR_LABEL);
        systemUptimeValue.setBackground(Colors.MONITOR_TABLE_CONTENT);
        systemUptimeValue.setAlignmentX(RIGHT_ALIGNMENT);
        constraints.gridx = 1;
        constraints.weightx = 3;
        constraints.anchor = GridBagConstraints.EAST;
        systemInformationPanel.add(systemUptimeValue.getWrapperPanel(), constraints);
    }

    private void addCpuUsageGraph() {
        // Add header
        final WrappedJLabel cpuUsageHeader = new WrappedJLabel("CPU Load", SwingUtils.getIconFromResource("cpu.png"));
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

    private void addStorageDisksInformationTable() {
        // Add header
        final WrappedJLabel disksHeader = new WrappedJLabel("Storage Drives", SwingUtils.getIconFromResource("storage-drive.png"));
        disksHeader.getWrapperPanel().setBackground(Colors.MONITOR_BACKGROUND);
        disksHeader.setFont(Fonts.MONITOR_SUBTITLE);
        this.add(disksHeader);

        /* Create and populate table header panel */

        // Create header panel
        final JPanel disksTableHeaderPanel = new JPanel();
        disksTableHeaderPanel.setLayout(new GridLayout(1, 4));
        disksTableHeaderPanel.setBorder(new EmptyBorder(2, 5, 0, 5));
        disksTableHeaderPanel.setBackground(Colors.MONITOR_TABLE_HEADER);
        this.add(disksTableHeaderPanel);

        // Add disk name header label
        final JLabel diskNameHeaderLabel = new JLabel("Name");
        diskNameHeaderLabel.setFont(Fonts.MONITOR_TABLE_HEADER);
        disksTableHeaderPanel.add(diskNameHeaderLabel);

        // Add disk total space header label
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

        addEmptyDiskTable("Loading...");
    }

    /**
     * Resets the contents of the disks information panel.
     *
     * @param placeholderText The text to have in the table as placeholder until data is added.
     */
    private void addEmptyDiskTable(@NotNull final String placeholderText) {
        if (disksTableContentPanel == null) {
            disksTableContentPanel = new JPanel();
            this.add(disksTableContentPanel);
        } else {
            disksTableContentPanel.removeAll();
        }

        disksTableContentPanel.setLayout(new FlowLayout()); // TODO moet dit?

        final JLabel placeholderLabel = new JLabel(placeholderText);
        placeholderLabel.setFont(Fonts.MONITOR_TABLE_CONTENT);
        disksTableContentPanel.add(placeholderLabel);
    }

    /* Panel management methods */

    /**
     * Sets the uptime value of the monitoring panel
     *
     * @param uptime The system's uptime.
     */
    public void setUptimeValue(@NotNull final String uptime) {
        systemUptimeValue.setText(uptime);
    }

    /**
     * Appends the given CPU usage value to the graph.
     *
     * @param cpu The CPU usage value.
     */
    public void appendCpuValueToGraph(@NotNull final double cpu){
        cpuUsageGraphPanel.appendValue(cpu);
    }

    /**
     * Adds the information about a storage disk to the table.
     *
     * @param disk The disk.
     */
    public void addDiskInformation(SystemMonitor.DiskResult disk) {
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

        Main.mainWindow.pack(); // Resize window
    }

    @Override
    public void onShowImpl() {
        // Do nothing
    }

    @Override
    public void onHideImpl() {
        // Reset values & clear disks' information table
        systemUptimeValue.setText("Loading...");
        addEmptyDiskTable("Loading...");
    }
}
