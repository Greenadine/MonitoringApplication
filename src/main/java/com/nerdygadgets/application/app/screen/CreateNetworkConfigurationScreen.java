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

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * A {@link JPanel} for creating new {@link NetworkConfiguration}s.
 *
 * @author Kevin Zuman
 */
public class CreateNetworkConfigurationScreen extends ApplicationScreen {

    private final NetworkComponentsListSidebar sidebar;

    public CreateNetworkConfigurationScreen(@NotNull final ApplicationWindow window) {
        super(window);

        // Configure screen
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Populate screen
        this.add(new ScreenHeaderPanel(this, "New Network Configuration", 1250, 50, this::actionReturn), BorderLayout.PAGE_START);

        // Create sidebar
        sidebar = new NetworkComponentsListSidebar(this);
        this.add(sidebar, BorderLayout.LINE_START);

        JLabel label1 = new JLabel("Test1");
        label1.setBorder(new MatteBorder(5, 5, 5, 5, Color.GREEN));

        sidebar.getWebserversListPane().addComponent(label1);
        sidebar.getFirewallListPane().addComponent(new JLabel("Test1"));

        for (int i = 0; i < 5; i++) {
            JLabel testLabel = new JLabel("Test " + (i + 1));
            sidebar.getDatabasesListPane().addComponent(testLabel);
        }

        // Create center wrapper
        JPanel contentWrapperPanel = new JPanel();
        contentWrapperPanel.setLayout(new BorderLayout());
        this.add(contentWrapperPanel, BorderLayout.CENTER);

        // Firewall panel
        FirewallPanel firewallPanel = new FirewallPanel(this);
        contentWrapperPanel.add(firewallPanel, BorderLayout.PAGE_START);

        // Create component list wrapper
        JPanel componentWrapper = new JPanel();
        componentWrapper.setLayout(new GridLayout(1, 2));
        contentWrapperPanel.add(componentWrapper, BorderLayout.CENTER);

        // Configuration components list panels
        ConfigurationComponentsList configurationComponentDatabases = new ConfigurationComponentsList(this, "Databases");
        ConfigurationComponentsList configurationComponentWebservers = new ConfigurationComponentsList(this, "Webservers");
        componentWrapper.add(configurationComponentDatabases);
        componentWrapper.add(configurationComponentWebservers);

        // Create configuration data wrapper
        JPanel pageEndWrapperPanel = new JPanel();
        pageEndWrapperPanel.setLayout(new BoxLayout(pageEndWrapperPanel, BoxLayout.Y_AXIS));
        contentWrapperPanel.add(pageEndWrapperPanel, BorderLayout.PAGE_END);

        // Configuration data panel
        ConfigurationDataPanel configurationDataPanel = new ConfigurationDataPanel(this);
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

    /* Button Actions */

    private void actionReturn(ActionEvent event) {
        window.openScreen("network-configurations");
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
