package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.Settings;
import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.util.Colors;
import com.nerdygadgets.application.util.SwingUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.event.ActionEvent;

/**
 * A {@link JPanel} for changing certain settings within the application.
 *
 * @author Kevin Zuman
 */
public class SettingsPanel extends ApplicationPanel {

    private final JCheckBox backgroundMonitoringCheckBox;
    private final JCheckBox debugLoggingCheckBox;

    public SettingsPanel(@NotNull ApplicationScreen parentScreen) {
        super(parentScreen);

        // Configure panel
        this.setBackground(Colors.MAIN_BACKGROUND);
        this.setBorder(new MatteBorder(2, 2, 2, 2, Colors.MAIN_BACKGROUND_ACCENT));

        // Create wrapper panel
        final JPanel settingValuesPanel = new JPanel();
        settingValuesPanel.setLayout(new BoxLayout(settingValuesPanel, BoxLayout.Y_AXIS));
        settingValuesPanel.setBackground(Colors.MAIN_BACKGROUND);
        settingValuesPanel.setBorder(new EmptyBorder(15, 10, 15, 10));
        this.add(settingValuesPanel);

        /* Populate panel */

        // Add checkbox for background monitoring setting
        backgroundMonitoringCheckBox = new JCheckBox("Keep monitoring systems in the background");
        backgroundMonitoringCheckBox.setIconTextGap(15);
        backgroundMonitoringCheckBox.setAlignmentX(LEFT_ALIGNMENT);
        backgroundMonitoringCheckBox.setFocusPainted(false);
        backgroundMonitoringCheckBox.addActionListener(this::toggleBackgroundMonitoring);
        settingValuesPanel.add(backgroundMonitoringCheckBox);

        SwingUtils.addVerticalSpacer(settingValuesPanel, 10);

        // Add checkbox for debug logging setting
        debugLoggingCheckBox = new JCheckBox("Enable debug logging");
        debugLoggingCheckBox.setIconTextGap(15);
        debugLoggingCheckBox.setAlignmentX(LEFT_ALIGNMENT);
        debugLoggingCheckBox.setFocusPainted(false);
        debugLoggingCheckBox.addActionListener(this::toggleDebugLogging);
        settingValuesPanel.add(debugLoggingCheckBox);
    }

    /* Component actions */

    /**
     * The {@link java.awt.event.ActionListener} for when the background monitoring setting should be toggled.
     *
     * @param event The {@link ActionEvent}.
     */
    private void toggleBackgroundMonitoring(final ActionEvent event) {
        Settings.setBackgroundMonitoring(!Settings.isBackgroundMonitoringEnabled()); // Invert value to toggle
    }

    /**
     * The {@link java.awt.event.ActionListener} for when the debug logging setting should be toggled.
     *
     * @param event The {@link ActionEvent}.
     */
    private void toggleDebugLogging(final ActionEvent event) {
        Settings.setDebugLogging(!Settings.isDebugLoggingEnabled());
    }

    @Override
    public void onShowImpl() {
        backgroundMonitoringCheckBox.setSelected(Settings.isBackgroundMonitoringEnabled());
        debugLoggingCheckBox.setSelected(Settings.isDebugLoggingEnabled());
    }

    @Override
    public void onHideImpl() {
        // Do nothing
    }
}
