package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.model.ApplicationWindow;
import com.nerdygadgets.application.model.NetworkConfiguration;
import com.nerdygadgets.application.model.component.Database;
import com.nerdygadgets.application.model.component.Firewall;
import com.nerdygadgets.application.model.component.Webserver;
import com.nerdygadgets.application.util.*;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class NetworkConfigurationsScreen extends ApplicationScreen {

    public NetworkConfigurationsScreen(@NotNull final ApplicationWindow window) {
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
        try {
            Firewall firewall = new Firewall("Firewall1", 0.9999, 3000, "192.168.0.1", "255.255.255.0");
            Database database1 = new Database("Database1", 0.95, 5000, "192.168.0.2", "255.255.255.0");
            Database database2 = new Database("Database2", 0.90, 5000, "192.168.0.3", "255.255.255.0");
            Webserver webserver1 = new Webserver("Webserver1", 0.98, 5000, "192.168.0.4", "255.255.255.0");
            Webserver webserver2 = new Webserver("Webserver2", 0.95, 5000, "192.168.0.5", "255.255.255.0");

            NetworkConfiguration configuration = new NetworkConfiguration("Test Network Configuration", firewall);
            configuration.addDatabase(database1);
            configuration.addDatabase(database2);
            configuration.addWebserver(webserver1);
            configuration.addWebserver(webserver2);

            // TODO pass configuration to view network configuration screen
            ((ViewNetworkConfigurationScreen)window.getScreen("view-network-configuration")).setConfiguration(configuration);
        } catch (IOException ex) {
            Logger.error(ex, "Failed to create mock network configuration.");
        }
        window.openScreen("view-network-configuration");

//        if (chooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
//            final File selected = chooser.getSelectedFile();
//
//            System.out.println(selected.getName());
//            // TODO open network configuration functionality
//        }

    }

    /**
     * The {@link java.awt.event.ActionListener} for when the "Create New Configuration" button is clicked.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionCreateNewConfiguration(ActionEvent event) {
        window.openScreen("create-network-configuration");
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
