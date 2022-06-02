package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.model.ApplicationWindow;
import com.nerdygadgets.application.app.panel.*;
import com.nerdygadgets.application.app.panel.NetworkComponentsListSidebar;
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
import java.io.IOException;

public class NetworkConfigurationScreen extends ApplicationScreen {

    protected NetworkConfiguration configuration;

    protected final ScreenHeaderPanel screenHeaderPanel;
    protected final NetworkComponentsListSidebar sidebar;

    protected final FirewallPanel firewallPanel;
    protected final ConfigurationComponentsList databaseList;
    protected final ConfigurationComponentsList webserverList;
    protected final ConfigurationDataPanel dataPanel;

    public NetworkConfigurationScreen(@NotNull ApplicationWindow window) {
        super(window);

        // Configure screen
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        // --- Populate screen ---

        // Add header
        screenHeaderPanel = new ScreenHeaderPanel(this, "(Network Configuration Name)", 1250, 50, this::actionReturn);
        this.add(screenHeaderPanel, BorderLayout.PAGE_START);

        // Create center wrapper panel
        JPanel centerWrapperPanel = new JPanel();
        centerWrapperPanel.setLayout(new BorderLayout());
        this.add(centerWrapperPanel, BorderLayout.CENTER);

        // Add firewall panel
        firewallPanel = new FirewallPanel(this);
        centerWrapperPanel.add(firewallPanel, BorderLayout.PAGE_START);

        // Create component lists
        JPanel componentListsWrapper = new JPanel();
        componentListsWrapper.setLayout(new GridLayout(1,2));
        centerWrapperPanel.add(componentListsWrapper, BorderLayout.CENTER);

        // Add configuration components list panels
        databaseList = new ConfigurationComponentsList(this, "Databases");
        webserverList = new ConfigurationComponentsList(this, "Webservers");
        componentListsWrapper.add(databaseList);
        componentListsWrapper.add(webserverList);

        // Create sidebar
        sidebar = new NetworkComponentsListSidebar(this);
        this.add(sidebar, BorderLayout.LINE_START);

        // Create configuration data wrapper
        JPanel pageEndWrapperPanel = new JPanel();
        pageEndWrapperPanel.setLayout(new BoxLayout(pageEndWrapperPanel, BoxLayout.Y_AXIS));
        centerWrapperPanel.add(pageEndWrapperPanel, BorderLayout.PAGE_END);

        // Configuration data panel
        dataPanel = new ConfigurationDataPanel(this);
        pageEndWrapperPanel.add(dataPanel);

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
        optimizeButton.addActionListener(this::actionOptimize);
        buttonsPanel.add(optimizeButton);

        // Create save button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this::actionSave);
        buttonsPanel.add(saveButton);
    }

    /**
     * Sets the {@link NetworkConfiguration} to display on the screen.
     *
     * @param configuration The {@code NetworkConfiguration}.
     */
    public void setConfiguration(@NotNull final NetworkConfiguration configuration) {
        if (this.configuration != null) {
            firewallPanel.onHideImpl();
            databaseList.onHideImpl();
            webserverList.onHideImpl();
            dataPanel.onHideImpl();
        }

        this.configuration = configuration;

        // Set and add the name in the title for the screen
        screenHeaderPanel.setTitle(configuration.getName());

        // Set the firewall for the firewall panel
        if (configuration.getFirewall() != null) {
            firewallPanel.setFirewall(configuration, configuration.getFirewall());
        } else {
            firewallPanel.clear();
        }

        // Sets and add the components for databases & webservers panel
        databaseList.setComponentList(configuration.getDatabases());
        webserverList.setComponentList(configuration.getWebservers());

        // Sets the configuration data panel values
        dataPanel.setAvailabilityValue(configuration.getAvailability());
        dataPanel.setPriceValue(configuration.getPrice());

        dataPanel.update();
    }

    public NetworkConfiguration getConfiguration() {
        return configuration;
    }

    public FirewallPanel getFirewallPanel() {
        return firewallPanel;
    }

    public ConfigurationComponentsList getDatabaseList() {
        return databaseList;
    }

    public ConfigurationComponentsList getWebserverList() {
        return webserverList;
    }

    /* Button Actions */

    /**
     * Returns to the network configuration menu.
     */
    private void actionReturn(ActionEvent event) {
        if (ApplicationUtils.showConfirmationDialog("Are you sure you want to leave?", "Are you sure you want to leave? Make sure you have first saved the configuration before returning.")) {
            window.openScreen("network-configurations-menu");
        }
    }

    /**
     * Optimizes the configuration to the cheapest possible configuration with the desired availability.
     */
    private void actionOptimize(ActionEvent event) {
        ApplicationUtils.showPopupInfoDialog("Work In Progress", "This feature is still being worked on. Please try again later.");
    }

    /**
     * Opens a dialog for selecting a save location for the {@link NetworkConfiguration} file, and saves it to the selected location.
     */
    private void actionSave(ActionEvent event) {
        // Check if a firewall has been set for the configuration
        if (configuration.getFirewall() == null) {
            ApplicationUtils.showPopupErrorDialog("Invalid configuration", "Please set a firewall for the configuration");
            return;
        }

        // Check whether there are any databases set for the configuration
        if (configuration.getDatabases().isEmpty()) {
            ApplicationUtils.showPopupErrorDialog("Invalid configuration", "Please set at least one database.");
            return;
        }

        // Check whether there are any webservers set for the configuration
        if (configuration.getWebservers().isEmpty()) {
            ApplicationUtils.showPopupErrorDialog("Invalid configuration", "Please set at least one webserver.");
            return;
        }

        // Open file chooser for selecting save location
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a saving location");
        final FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Network Configuration Files (.json)", "json");
        fileChooser.addChoosableFileFilter(extensionFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int userSelection = fileChooser.showSaveDialog(window);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File saveLocation = new File(fileChooser.getSelectedFile().getAbsolutePath());

            if (!saveLocation.getAbsolutePath().endsWith(".json")) {
                saveLocation = new File(saveLocation.getAbsolutePath() + ".json");
            }

            configuration.setName(saveLocation.getName().substring(0, saveLocation.getName().length() - 5));

            try {
                NetworkConfigurationUtils.serialize(configuration, saveLocation);
                ApplicationUtils.showPopupInfoDialog("Configuration saved", String.format("The configuration has been saved to '%s'.", saveLocation.getAbsolutePath()));
                window.openScreen("network-configurations-menu");
            } catch (IOException ex) {
                ApplicationUtils.showPopupErrorDialog("Failed to save configuration", "An error has occurred while attempting to save configuration. This is likely due to insufficient privileges at the designated save location.");
            }
        }
    }

    @Override
    protected void onOpenImpl() {
        // Do nothing
    }

    @Override
    protected void onCloseImpl() {
        // Reset header title
        screenHeaderPanel.setTitle("(Network Configuration Name)");
    }
}