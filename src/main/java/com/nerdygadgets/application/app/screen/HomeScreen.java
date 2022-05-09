package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.Main;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.model.ApplicationWindow;
import com.nerdygadgets.application.util.Fonts;
import com.nerdygadgets.application.util.SwingUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;

public class HomeScreen extends ApplicationScreen {

    public HomeScreen(@NotNull final ApplicationWindow window) {
        super(window);

        // Configure screen
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(10, 25, 25, 25));

        // Populate screen
        final JLabel titleLabel = new JLabel("Home");
        titleLabel.setFont(Fonts.MAIN_TITLE);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.add(titleLabel);
        SwingUtils.addVerticalSpacer(this, 15);

        SwingUtils.addButton(this, "Network Configurations", null, 350, 50, this::actionOpenNetworkConfigurations);
        SwingUtils.addVerticalSpacer(this, 10);

        SwingUtils.addButton(this, "Network Components", null, 350, 50, this::actionOpenViewNetworkComponents);
        SwingUtils.addVerticalSpacer(this, 10);

        this.add(new JSeparator()); // Add separator
        SwingUtils.addVerticalSpacer(this, 10);

        SwingUtils.addButton(this, "Network Monitor",  null, 350, 50, this::actionOpenNetworkMonitor);
        SwingUtils.addVerticalSpacer(this, 10);

        this.add(new JSeparator());
        SwingUtils.addVerticalSpacer(this, 10);

        // Add settings button
        SwingUtils.addButton(this, "",  "settings.png", 125, 35, this::actionOpenSettings);
    }

    /* Button actions */

    /**
     * The {@link java.awt.event.ActionListener} for when the "Network Configurations" button is clicked.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionOpenNetworkConfigurations(final ActionEvent event) {
        window.openScreen("network-configurations");
    }

    /**
     * The {@link java.awt.event.ActionListener} for when the "Network Components" button is clicked.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionOpenViewNetworkComponents(final ActionEvent event) {
        window.openScreen("view-network-components");
    }

    /**
     * The {@link java.awt.event.ActionListener} for when the "Network Monitor" button is clicked.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionOpenNetworkMonitor(final ActionEvent event) {
        window.openScreen("network-monitor");
    }

    /**
     * The {@link java.awt.event.ActionListener} for when the "Settings" button is clicked.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionOpenSettings(final ActionEvent event) {
        Main.settingsWindow.setVisible(true);
    }

    @Override
    public void onOpenImpl() {
        // Nothing
    }

    @Override
    public void onCloseImpl() {
        // Nothing
    }
}
