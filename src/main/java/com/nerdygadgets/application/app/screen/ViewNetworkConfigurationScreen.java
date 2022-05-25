package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.model.ApplicationWindow;
import com.nerdygadgets.application.app.panel.*;
import com.nerdygadgets.application.model.NetworkConfiguration;
import com.nerdygadgets.application.model.component.Database;
import com.nerdygadgets.application.model.component.Firewall;
import com.nerdygadgets.application.model.component.Webserver;
import com.nerdygadgets.application.util.Colors;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collection;

public class ViewNetworkConfigurationScreen extends ApplicationScreen {

    private NetworkConfiguration configuration;

    private final ScreenHeaderPanel screenHeaderPanel;
    private final NetworkComponentsListSidebar sidebar;

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
        screenHeaderPanel = new ScreenHeaderPanel(this, "", 1250, 50, this::actionReturn);
        this.add(screenHeaderPanel, BorderLayout.PAGE_START);

        // Create sidebar
        sidebar = new NetworkComponentsListSidebar(this);
        this.add(sidebar, BorderLayout.LINE_START);

        populateCenter();
    }

    private void populateCenter() {
        // Create and add center wrapper panel
        JPanel centerWrapperPanel = new JPanel();
        centerWrapperPanel.setLayout(new BorderLayout());
        this.add(centerWrapperPanel, BorderLayout.CENTER);

        // Firewall panel
        firewallPanel = new FirewallPanel(this);
        centerWrapperPanel.add(firewallPanel, BorderLayout.PAGE_START);

        // Create component list wrapper
        JPanel componentWrapper = new JPanel();
        componentWrapper.setLayout(new GridLayout(1, 2));
        centerWrapperPanel.add(componentWrapper, BorderLayout.CENTER);

        // Configuration components list panels
        webserversComponentsList = new ConfigurationComponentsList(this, "Webservers");
        databasesComponentsList = new ConfigurationComponentsList(this, "Databases");
        componentWrapper.add(databasesComponentsList);
        componentWrapper.add(webserversComponentsList);

        // Create configuration data wrapper
        JPanel pageEndWrapperPanel = new JPanel();
        pageEndWrapperPanel.setLayout(new BoxLayout(pageEndWrapperPanel, BoxLayout.Y_AXIS));
        centerWrapperPanel.add(pageEndWrapperPanel, BorderLayout.PAGE_END);

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
     * Populates the {@link Database}s list in the sidebar with the given {@code Collection} of {@code Database}s.
     *
     * @param databases A {@code Collection} of {@code Database} to populate the sidebar with.
     */
    public void populateDatabases(Collection<Database> databases) {
        for (Database database : databases) {
            // TODO add all databases to sidebar
        }
    }

    /**
     * Populates the {@link Webserver}s list in the sidebar with the given {@code Collection} of {@code Webserver}s.
     *
     * @param webservers A {@code Collection} of {@code Webservers} to populate the sidebar with.
     */
    public void populateWebservers(Collection<Webserver> webservers) {
        for (Webserver webserver : webservers) {
            // TODO add all webservers to sidebar
        }
    }

    /**
     * Populates the {@link Firewall}s list in the sidebar with the given {@code Collection} of {@code Firewall}s.
     *
     * @param firewalls A {@code Collection} of {@code Firewall} to populate the sidebar with.
     */
    public void populateFirewalls(Collection<Firewall> firewalls) {
        for (Firewall firewall : firewalls) {
            // TODO add all firewalls to sidebar
        }
    }

    /* Button Actions */

    private void actionReturn(ActionEvent event) {
        window.openScreen("network-configurations");
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
