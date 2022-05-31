package com.nerdygadgets.application.app.screen;


import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.model.ApplicationWindow;
import com.nerdygadgets.application.app.panel.*;
import com.nerdygadgets.application.model.NetworkComponent;
import com.nerdygadgets.application.model.NetworkConfiguration;
import com.nerdygadgets.application.util.ApplicationUtils;
import com.nerdygadgets.application.util.Colors;
import com.nerdygadgets.application.util.NetworkConfigurationUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * A {@link JPanel} for creating new {@link NetworkConfiguration}s.
 *
 * @author Kevin Zuman
 */
public class CreateNetworkConfigurationScreen extends ApplicationScreen {

    private final NetworkComponentsListSidebar sidebar;
    private final ConfigurationDataPanel configurationDataPanel;

    private final FirewallPanel firewallPanel;
    private final ConfigurationComponentsList databasesComponentsList;
    private final ConfigurationComponentsList webserversComponentsList;
    private final NetworkConfiguration configuration;

    public CreateNetworkConfigurationScreen(@NotNull final ApplicationWindow window) {
        super(window);
        this.configuration = new NetworkConfiguration();

        // Configure screen
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Populate screen
        this.add(new ScreenHeaderPanel(this, "New Network Configuration", 1250, 50, this::actionReturn), BorderLayout.PAGE_START);

        // Create center wrapper
        JPanel contentWrapperPanel = new JPanel();
        contentWrapperPanel.setLayout(new BorderLayout());
        this.add(contentWrapperPanel, BorderLayout.CENTER);

        // Firewall panel
        firewallPanel = new FirewallPanel(this);
        contentWrapperPanel.add(firewallPanel, BorderLayout.PAGE_START);

        // Create component list wrapper
        JPanel componentWrapper = new JPanel();
        componentWrapper.setLayout(new GridLayout(1, 2));
        contentWrapperPanel.add(componentWrapper, BorderLayout.CENTER);

        // Configuration components list panels
        databasesComponentsList = new ConfigurationComponentsList(this, "Databases", configuration);
        webserversComponentsList = new ConfigurationComponentsList(this, "Webservers", configuration);
        componentWrapper.add(databasesComponentsList);
        componentWrapper.add(webserversComponentsList);


        // Create sidebar
        sidebar = new NetworkComponentsListSidebar(this, configuration, firewallPanel, databasesComponentsList, webserversComponentsList);
//        sidebar.setPreferredSize(new Dimension(150, 500));
        this.add(sidebar, BorderLayout.LINE_START);

        // Create configuration data wrapper
        JPanel pageEndWrapperPanel = new JPanel();
        pageEndWrapperPanel.setLayout(new BoxLayout(pageEndWrapperPanel, BoxLayout.Y_AXIS));
        contentWrapperPanel.add(pageEndWrapperPanel, BorderLayout.PAGE_END);

        // Configuration data panel
        configurationDataPanel = new ConfigurationDataPanel(this);
        pageEndWrapperPanel.add(configurationDataPanel);

        // Create buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 7));
        buttonsPanel.setBackground(Colors.MAIN_BACKGROUND_ACCENT);
        pageEndWrapperPanel.add(buttonsPanel);

        // Fill up the grid layout for spacing
        for (int i = 0; i < 5; i++) {
            buttonsPanel.add(new JLabel());
        }

        // Create optimize button
        JButton optimizeButton = new JButton("Optimize");
        buttonsPanel.add(optimizeButton);

        // Create save button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this::actionSave);
        buttonsPanel.add(saveButton);
    }

    /* Button Actions */

    private void actionReturn(ActionEvent event) {
        window.openScreen("network-configurations");
    }

    private void actionSave(ActionEvent event) {
        if (firewallPanel.getFirewall() == null) {
            ApplicationUtils.showPopupErrorMessage("Invalid configuration", "Please set an firewall for the configuration");
            return;
        }

        if (databasesComponentsList.getComponentsList().isEmpty()) {
            ApplicationUtils.showPopupErrorMessage("Invalid configuration", "Please set at least one database");
            return;
        }

        if (webserversComponentsList.getComponentsList().isEmpty()) {
            ApplicationUtils.showPopupErrorMessage("Invalid configuration", "Please set at least one webserver");
            return;
        }

        configuration.setFirewall(firewallPanel.getFirewall());
        for (NetworkComponent database: databasesComponentsList.getComponentsList()){
            configuration.addDatabase(database);
        }

        for (NetworkComponent webserver: webserversComponentsList.getComponentsList()){
            configuration.addWebserver(webserver);
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a saving location");
        final FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Network Configuration Files (.json)", "json");
        fileChooser.addChoosableFileFilter(extensionFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int userSelection = fileChooser.showSaveDialog(window);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            NetworkConfigurationUtils.serialize(configuration, fileToSave);
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());
        }
    }

    @Override
    public void onOpenImpl() {
        // Do nothing
    }

    @Override
    public void onCloseImpl() {
        // Do nothing
    }
}
