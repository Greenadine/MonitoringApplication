package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.model.ApplicationWindow;
import com.nerdygadgets.application.model.NetworkConfiguration;
import com.nerdygadgets.application.util.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class NetworkConfigurationsMenuScreen extends ApplicationScreen {

    public NetworkConfigurationsMenuScreen(@NotNull final ApplicationWindow window) {
        super(window);

        // Configure screen
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(10, 25, 25, 25));

        // Populate screen
        final JLabel titleLabel = new JLabel("Network Configurations");
        titleLabel.setFont(Fonts.MAIN_TITLE);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.add(titleLabel);
        SwingUtils.addVerticalSpacer(this, 15);

        // Add button for opening existing network configurations
        SwingUtils.addButton(this, "Open Existing Configuration", null, 350, 50, this::actionOpenExistingConfiguration);
        SwingUtils.addVerticalSpacer(this, 10);

        // Add button for creating new network configurations
        SwingUtils.addButton(this, "Create New Configuration", null, 350, 50, this::actionCreateNewConfiguration);
        SwingUtils.addVerticalSpacer(this, 10);

        this.add(new JSeparator());
        SwingUtils.addVerticalSpacer(this, 10);

        // Add return button
        JButton backButton = SwingUtils.addButton(this, "",  "return.png", 125, 35, ApplicationActions::openHome);
        backButton.setBackground(Colors.MAIN_ACCENT);
        backButton.setForeground(Color.WHITE);
    }

    /* Button actions */

    /**
     * The {@link java.awt.event.ActionListener} for when the "Open Network Configuration" button is clicked.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionOpenExistingConfiguration(ActionEvent event) {
        final JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Open a network configuration file");
        final FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Network Configuration Files (.json)", "json");
        chooser.addChoosableFileFilter(extensionFilter);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (chooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
            final File selected = chooser.getSelectedFile();
            NetworkConfiguration configuration = NetworkConfigurationUtils.deserialize(selected);

            ((NetworkConfigurationScreen) window.getScreen("network-configuration")).setConfiguration(configuration);
            window.openScreen("network-configuration");
        }
    }

    /**
     * The {@link java.awt.event.ActionListener} for when the "Create New Configuration" button is clicked.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionCreateNewConfiguration(ActionEvent event) {
        // Pass an empty network configuration to the screen, and open the screen
        ((NetworkConfigurationScreen) window.getScreen("network-configuration")).setConfiguration(new NetworkConfiguration("Create New Configuration"));
        window.openScreen("network-configuration");
    }

    @Override
    protected void onOpenImpl() {
        // Do nothing
    }

    @Override
    protected void onCloseImpl() {
        // Do nothing
    }
}
