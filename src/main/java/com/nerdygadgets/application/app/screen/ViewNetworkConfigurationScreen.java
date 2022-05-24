package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.model.ApplicationWindow;
import com.nerdygadgets.application.app.panel.*;
import com.nerdygadgets.application.model.NetworkConfiguration;
import com.nerdygadgets.application.model.component.Database;
import com.nerdygadgets.application.model.component.NetworkComponent;
import com.nerdygadgets.application.model.component.Webserver;
import com.nerdygadgets.application.util.Colors;
import com.nerdygadgets.application.util.SwingUtils;
import org.jdesktop.swingx.JXCollapsiblePane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ViewNetworkConfigurationScreen extends ApplicationScreen {

    private NetworkConfiguration configuration;
    private final NetworkComponentsListSidebar sidebar;
    private final ConfigurationDataPanel configurationDataPanel;

    private final FirewallPanel firewallPanel;
    private final ConfigurationComponentsList databasesComponentsList;
    private final ConfigurationComponentsList webserversComponentsList;
    private final ScreenHeaderPanel screenHeaderPanel;

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
//        sidebar.setPreferredSize(new Dimension(150, 500));
        this.add(sidebar, BorderLayout.LINE_START);

        JLabel label1 = new JLabel("Test1");
        label1.setBorder(new MatteBorder(5, 5, 5, 5, Color.GREEN));

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
        databasesComponentsList = new ConfigurationComponentsList(this, "Databases");
        webserversComponentsList = new ConfigurationComponentsList(this, "Webservers");
        componentWrapper.add(databasesComponentsList);
        componentWrapper.add(webserversComponentsList);

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
        buttonsPanel.add(saveButton);


    }

    /**
     * Sets the {@link NetworkConfiguration} to display on the screen.
     *
     * @param configuration The {@code NetworkConfiguration}.
     */
    public void setConfiguration(@Nullable final NetworkConfiguration configuration) {
        this.configuration = configuration;

        // set and add the name in the title for the screen
        screenHeaderPanel.setTitle(configuration.getName());

        // set and add the firewall for the firewall panel
        firewallPanel.setFirewall(configuration.getFirewall());

        // sets and add the components for databases & webservers panel
        databasesComponentsList.setComponentList(configuration.getDatabases());
        webserversComponentsList.setComponentList(configuration.getWebservers());

        // sets the configuration data panel values
        configurationDataPanel.setAvailabilityValue(configuration.getAvailability());
        configurationDataPanel.setPriceValue(configuration.getPrice());
    }

    /* Button Actions */

    private void actionReturn(ActionEvent event) {
        window.openScreen("network-configurations");
    }

    @Override
    protected void onOpenImpl() {

    }

    @Override
    protected void onCloseImpl() {
        this.configuration = null;

        firewallPanel.setFirewall(null);

        // sets and add the components for databases & webservers panel
        databasesComponentsList.clearComponentList();
        webserversComponentsList.clearComponentList();

        // sets the configuration data panel values
        configurationDataPanel.setAvailabilityValue(-1);
        configurationDataPanel.setPriceValue(-1);
    }
}
