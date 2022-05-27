package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.model.ApplicationWindow;
import com.nerdygadgets.application.app.panel.networkcomponents.NetworkComponentButtonListPanel;
import com.nerdygadgets.application.app.panel.networkcomponents.NetworkComponentDetailsPanel;
import com.nerdygadgets.application.app.panel.ScreenHeaderPanel;
import com.nerdygadgets.application.model.component.Database;
import com.nerdygadgets.application.model.component.Firewall;
import com.nerdygadgets.application.model.component.Webserver;
import com.nerdygadgets.application.util.ApplicationActions;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class NewViewNetworkComponentsScreen extends ApplicationScreen
{

    private final NetworkComponentButtonListPanel databaseList;
    private final NetworkComponentButtonListPanel webserverList;
    private final NetworkComponentButtonListPanel firewallList;

    private final NetworkComponentDetailsPanel detailsPanel;

    public NewViewNetworkComponentsScreen(@NotNull ApplicationWindow window)
    {
        super(window);

        // Configure screen
        this.setLayout(new BorderLayout(3, 3));

        // Populate screen
        this.add(new ScreenHeaderPanel(this, "Network Components", 1250, 50, ApplicationActions::openHome), BorderLayout.PAGE_START);

        // Create wrapper grid panel for component lists
        JPanel wrapperGridPanel = new JPanel();
        wrapperGridPanel.setLayout(new GridLayout(1, 3));
        this.add(wrapperGridPanel, BorderLayout.CENTER);

        // Add component details panel as right sidebar
        detailsPanel = new NetworkComponentDetailsPanel(this);
        this.add(detailsPanel, BorderLayout.LINE_END);

        // Add component lists to wrapper panel
        databaseList = new NetworkComponentButtonListPanel(this, "Databases", detailsPanel);
        wrapperGridPanel.add(databaseList);
        webserverList = new NetworkComponentButtonListPanel(this, "Webservers", detailsPanel);
        wrapperGridPanel.add(webserverList);
        firewallList = new NetworkComponentButtonListPanel(this, "Firewalls", detailsPanel);
        wrapperGridPanel.add(firewallList);
    }

    public NetworkComponentDetailsPanel getDetailsPanel()
    {
        return detailsPanel;
    }

    @Override
    protected void onOpenImpl()
    {
        // TODO load components from database to lists

        GetDataFromDatabase databaseConnection = new GetDataFromDatabase();
        for (Database database: databaseConnection.getDatabaseFromDatabase()){
            databaseList.addComponent(database);
        }
        for (Firewall firewall: databaseConnection.getFirewallFromDatabase()){
            firewallList.addComponent(firewall);
        }
        for (Webserver webserver: databaseConnection.getWebserverFromDatabase()){
            webserverList.addComponent(webserver);
        }






//        try
//        {
//            // Add mock databases
//            for (int i = 0; i < 10; i++)
//            {
//                Database database = new Database("Database " + (i + 1), 90, 4000, "192.168.0." + (1 + i), "255.255.255.0");
//                databaseList.addComponent(database);
//            }
//
//            // Add mock webservers
//            for (int i = 0; i < 15; i++)
//            {
//                Webserver webserver = new Webserver("Webserver " + (i + 1), 90, 4000, "192.168.0." + (1 + i), "255.255.255.0");
//                webserverList.addComponent(webserver);
//            }
//
//            // Add mock firewalls
//            for (int i = 0; i < 3; i++)
//            {
//                Firewall firewall = new Firewall("Firewall " + (i + 1), 90, 4000, "192.168.0." + (1 + i), "255.255.255.0");
//                firewallList.addComponent(firewall);
//            }
//
//
//        } catch (IOException ignored)
//        {
//        }
    }

    @Override
    protected void onCloseImpl()
    {
        databaseList.clear();
        webserverList.clear();
        firewallList.clear();
    }


}

