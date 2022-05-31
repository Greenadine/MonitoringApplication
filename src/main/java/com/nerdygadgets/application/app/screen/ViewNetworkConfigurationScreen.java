package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.model.ApplicationWindow;
import com.nerdygadgets.application.app.panel.*;
import com.nerdygadgets.application.model.NetworkConfiguration;
import com.nerdygadgets.application.model.NetworkComponent;
import com.nerdygadgets.application.util.Colors;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collection;

public class ViewNetworkConfigurationScreen extends ApplicationScreen {

    private final NetworkComponentsListSidebar sidebar;
    private NetworkConfiguration configuration;

    private final ScreenHeaderPanel screenHeaderPanel;
//    private final NetworkComponentsListSidebar sidebar;

    private FirewallPanel firewallPanel;
    private ConfigurationComponentsList databasesComponentsList;
    private ConfigurationComponentsList webserversComponentsList;
    private ConfigurationDataPanel configurationDataPanel;

    public ViewNetworkConfigurationScreen(@NotNull final ApplicationWindow window) {
        super(window);

        // Configure screen
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Populate screen
        screenHeaderPanel = new ScreenHeaderPanel(this, "New Network Configuration", 1250, 50, this::actionReturn);
        this.add(screenHeaderPanel, BorderLayout.PAGE_START);

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

    /**
     * Sets the {@link NetworkConfiguration} to display on the screen.
     *
     * @param configuration The {@code NetworkConfiguration}.
     */
    public void setConfiguration(@NotNull final NetworkConfiguration configuration) {
        if (this.configuration != null) {
            firewallPanel.onHideImpl();
            databasesComponentsList.onHideImpl();
            webserversComponentsList.onHideImpl();
            configurationDataPanel.onHideImpl();
        }

//        this.add(new JLabel("Kanker1"), BorderLayout.PAGE_START);
//        this.add(new JLabel("Kanker2"), BorderLayout.CENTER);


        this.configuration = configuration;

        // set and add the name in the title for the screen
        screenHeaderPanel.setTitle(configuration.getName());

        // Set the firewall for the firewall panel
        if (configuration.getFirewall() != null) {
            firewallPanel.setFirewall(configuration, configuration.getFirewall());
        }

        // sets and add the components for databases & webservers panel
        databasesComponentsList.setComponentList(configuration, configuration.getDatabases());
        webserversComponentsList.setComponentList(configuration, configuration.getWebservers());

        // sets the configuration data panel values
        configurationDataPanel.setAvailabilityValue(configuration.getAvailability());
        configurationDataPanel.setPriceValue(configuration.getPrice());
    }

    /**
     * Populates the databases list in the sidebar with the given {@code Collection} of databases.
     *
     * @param databases A {@code Collection} of databases to populate the sidebar with.
     */
    public void populateDatabases(Collection<NetworkComponent> databases) {
        for (NetworkComponent database : databases) {
            // TODO add all databases to sidebar
        }
    }

    /**
     * Populates the webservers list in the sidebar with the given {@code Collection} of webservers.
     *
     * @param webservers A {@code Collection} of {@code Webservers} to populate the sidebar with.
     */
    public void populateWebservers(Collection<NetworkComponent> webservers) {
        for (NetworkComponent webserver : webservers) {
            // TODO add all webservers to sidebar
        }
    }

    /**
     * Populates the firewall {@link NetworkComponent} list in the sidebar with the given {@code Collection} of firewalls.
     *
     * @param firewalls A {@code Collection} of firewall {@code NetworkComponents} to populate the sidebar with.
     */
    public void populateFirewalls(Collection<NetworkComponent> firewalls) {
        for (NetworkComponent firewall : firewalls) {
            // TODO add all firewalls to sidebar
        }
    }

    /* Button Actions */

    private void actionReturn(ActionEvent event) {
        window.openScreen("network-configurations");
    }

    private void actionSave(ActionEvent event) {
        // TODO
    }

    @Override
    protected void onOpenImpl() {
        // Do nothing
    }

    @Override
    protected void onCloseImpl() {
        this.configuration = null; // Reset the configuration
    }
}
