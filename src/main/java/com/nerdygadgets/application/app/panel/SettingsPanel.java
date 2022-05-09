package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.Settings;
import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.util.Colors;
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

    private final JCheckBox enableBackgroundMonitoringCheckBox;

    public SettingsPanel(@NotNull ApplicationScreen parentScreen) {
        super(parentScreen);

        // Configure panel
        this.setBackground(Colors.MAIN_BACKGROUND);
        this.setBorder(new MatteBorder(2, 2, 2, 2, Colors.MAIN_BACKGROUND_ACCENT));

        // Create wrapper panel
        JPanel settingValuesPanel = new JPanel();
        settingValuesPanel.setBackground(Colors.MAIN_BACKGROUND);
        settingValuesPanel.setBorder(new EmptyBorder(15, 10, 15, 10));
        this.add(settingValuesPanel);

        // Populate panel
        enableBackgroundMonitoringCheckBox = new JCheckBox("Keep monitoring systems in the background");
        enableBackgroundMonitoringCheckBox.setIconTextGap(35);
        enableBackgroundMonitoringCheckBox.setAlignmentX(CENTER_ALIGNMENT);
        enableBackgroundMonitoringCheckBox.addActionListener(this::toggleBackgroundMonitoring);
        settingValuesPanel.add(enableBackgroundMonitoringCheckBox);
    }

    /* Component actions */

    /**
     * The {@link java.awt.event.ActionListener} for when background monitoring should be toggled.
     *
     * @param event The {@link ActionEvent}.
     */
    private void toggleBackgroundMonitoring(final ActionEvent event) {
        Settings.setBackgroundMonitoring(!Settings.isBackgroundMonitoringEnabled()); // Invert value to toggle
    }

    @Override
    public void onShowImpl() {
        enableBackgroundMonitoringCheckBox.setSelected(Settings.isBackgroundMonitoringEnabled());
    }

    @Override
    public void onHideImpl() {
        // Do nothing
    }
}
