package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.component.DropdownListComponent;
import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class NetworkComponentsListSidebar extends ApplicationPanel {

    private final DropdownListComponent webserversListPane;
    private final DropdownListComponent databasesListPane;
    private final DropdownListComponent firewallListPane;

    public NetworkComponentsListSidebar(@NotNull ApplicationScreen parentScreen) {
        super(parentScreen);

        // Configure panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Create and add webservers list
        webserversListPane = new DropdownListComponent(this, "Webservers");
        this.add(webserversListPane);

        // Create and add databases list
        databasesListPane = new DropdownListComponent(this, "Databases");
        this.add(databasesListPane);

        // Create and add firewalls list
        firewallListPane = new DropdownListComponent(this, "Firewalls");
        this.add(firewallListPane);
    }

    public DropdownListComponent getWebserversListPane() {
        return webserversListPane;
    }

    public DropdownListComponent getDatabasesListPane() {
        return databasesListPane;
    }

    public DropdownListComponent getFirewallListPane() {
        return firewallListPane;
    }

    @Override
    public void onShowImpl() {

    }

    @Override
    public void onHideImpl() {

    }
}
