package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.component.NetworkComponentSidebarEntry;
import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.model.NetworkComponent;
import com.nerdygadgets.application.model.NetworkConfiguration;
import com.nerdygadgets.application.util.Colors;
import com.nerdygadgets.application.util.Fonts;
import com.nerdygadgets.application.util.database.GetDataFromDatabase;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class NetworkComponentsListSidebar extends ApplicationPanel {

    private final JPanel databaseList;
    private final JPanel webserversList;
    private final JPanel firewallList;
    private final NetworkConfiguration configuration;

    private final FirewallPanel configurationFirewall;
    private final ConfigurationComponentsList configurationDatabases;
    private final ConfigurationComponentsList configurationWebservers;

    public NetworkComponentsListSidebar(@NotNull ApplicationScreen parentScreen, @NotNull NetworkConfiguration configuration,
                                        @NotNull FirewallPanel configurationFirewall, @NotNull ConfigurationComponentsList configurationDatabases,
                                        @NotNull ConfigurationComponentsList configurationWebservers) {
        super(parentScreen);

        this.configuration = configuration;
        this.configurationFirewall = configurationFirewall;
        this.configurationDatabases = configurationDatabases;
        this.configurationWebservers = configurationWebservers;

        // Configure panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(300, 500));

        // Create and add databases list
        JPanel databasePanel = new JPanel();
        databasePanel.setBackground(Colors.MAIN_BACKGROUND);
        databasePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel databasesLabel = new JLabel("Databases");
        databasesLabel.setFont(Fonts.MAIN_SIDEBAR_HEADER);
        databasePanel.add(databasesLabel);
        this.add(databasePanel);

        databaseList = new JPanel();
        databaseList.setLayout(new BoxLayout(databaseList, BoxLayout.Y_AXIS));

        JScrollPane databaseListScrollPane = new JScrollPane(databaseList);
        databaseListScrollPane.setPreferredSize(new Dimension(100, 50));
        databaseListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(databaseListScrollPane);

        // Create and add webservers list
        JPanel webserverPanel = new JPanel();
        webserverPanel.setBackground(Colors.MAIN_BACKGROUND);
        webserverPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel webserversLabel = new JLabel("Webservers");
        webserversLabel.setFont(Fonts.MAIN_SIDEBAR_HEADER);
        webserverPanel.add(webserversLabel);
        this.add(webserverPanel);

        webserversList = new JPanel();
        webserversList.setLayout(new BoxLayout(webserversList, BoxLayout.Y_AXIS));

        JScrollPane webserverListScrollPane = new JScrollPane(webserversList);
        webserverListScrollPane.setPreferredSize(new Dimension(100, 50));
        webserverListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(webserverListScrollPane);

        // Create and add firewalls list
        JPanel firewallPanel = new JPanel();
        firewallPanel.setBackground(Colors.MAIN_BACKGROUND);

        firewallPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        JLabel firewallLabel = new JLabel("Firewalls");
        firewallLabel.setFont(Fonts.MAIN_SIDEBAR_HEADER);
        firewallPanel.add(firewallLabel);
        this.add(firewallPanel);

        firewallList = new JPanel();
        firewallList.setLayout(new BoxLayout(firewallList, BoxLayout.Y_AXIS));

        JScrollPane firewallListScrollPane = new JScrollPane(firewallList);
        firewallListScrollPane.setPreferredSize(new Dimension(100, 50));
        firewallListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(firewallListScrollPane);
    }

    public void getDatabases() {
        GetDataFromDatabase databaseConnection = new GetDataFromDatabase();
        for (NetworkComponent database : databaseConnection.getDatabaseFromDatabase()) {
            databaseList.add(new NetworkComponentSidebarEntry(this, database, configuration));
        }
    }

    public void getWebservers() {
        GetDataFromDatabase databaseConnection = new GetDataFromDatabase();
        for (NetworkComponent webserver : databaseConnection.getWebserverFromDatabase()) {
            webserversList.add(new NetworkComponentSidebarEntry(this, webserver, configuration));
        }
    }

    public void getFirewalls() {
        GetDataFromDatabase databaseConnection = new GetDataFromDatabase();
        for (NetworkComponent firewall : databaseConnection.getFirewallFromDatabase()) {
            firewallList.add(new NetworkComponentSidebarEntry(this, firewall, configuration));
        }
    }

    public FirewallPanel getConfigurationFirewall() {
        return configurationFirewall;
    }

    public ConfigurationComponentsList getConfigurationDatabases() {
        return configurationDatabases;
    }

    public ConfigurationComponentsList getConfigurationWebservers() {
        return configurationWebservers;
    }

    @Override
    public void onShowImpl() {
        // Retrieve components from database and add to sidebar
        getDatabases();
        getFirewalls();
        getWebservers();
    }

    @Override
    public void onHideImpl() {
        // Clear components from sidebar
        databaseList.removeAll();
        webserversList.removeAll();
        firewallList.removeAll();
    }
}
