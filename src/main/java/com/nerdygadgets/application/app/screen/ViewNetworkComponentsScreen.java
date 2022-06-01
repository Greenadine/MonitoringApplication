package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.dialog.AddComponentDialog;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.model.ApplicationWindow;
import com.nerdygadgets.application.app.panel.ScreenHeaderPanel;
import com.nerdygadgets.application.app.panel.networkcomponents.NetworkComponentButtonListPanel;
import com.nerdygadgets.application.app.panel.networkcomponents.NetworkComponentDetailsPanel;
import com.nerdygadgets.application.model.ComponentType;
import com.nerdygadgets.application.model.NetworkComponent;
import com.nerdygadgets.application.util.ApplicationActions;
import com.nerdygadgets.application.util.DatabaseUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ViewNetworkComponentsScreen extends ApplicationScreen implements ActionListener {

    private final NetworkComponentButtonListPanel databaseList;
    private final NetworkComponentButtonListPanel webserverList;
    private final NetworkComponentButtonListPanel firewallList;

    public ViewNetworkComponentsScreen(@NotNull ApplicationWindow window) {
        super(window);

        // Configure screen
        this.setLayout(new BorderLayout(3, 3));

        // Populate screen
        this.add(new ScreenHeaderPanel(this, "Network Components", 1250, 50, ApplicationActions::openHome), BorderLayout.PAGE_START);
        //Add dialog button
        JButton dialogAddComponent = new JButton("Add component");
        this.add(dialogAddComponent, BorderLayout.PAGE_END);
        dialogAddComponent.addActionListener(this);

        // Create wrapper grid panel for component lists
        JPanel wrapperGridPanel = new JPanel();
        wrapperGridPanel.setLayout(new GridLayout(1, 3));
        this.add(wrapperGridPanel, BorderLayout.CENTER);

        // Add component details panel as right sidebar
        NetworkComponentDetailsPanel detailsPanel = new NetworkComponentDetailsPanel(this);
        this.add(detailsPanel, BorderLayout.LINE_END);

        // Add component lists to wrapper panel
        databaseList = new NetworkComponentButtonListPanel(this, "Databases", detailsPanel);
        wrapperGridPanel.add(databaseList);
        webserverList = new NetworkComponentButtonListPanel(this, "Webservers", detailsPanel);
        wrapperGridPanel.add(webserverList);
        firewallList = new NetworkComponentButtonListPanel(this, "Firewalls", detailsPanel);
        wrapperGridPanel.add(firewallList);
    }

    @Override
    protected void onOpenImpl() {
        for (NetworkComponent database : DatabaseUtils.getComponents(ComponentType.DATABASE)) {
            databaseList.addComponent(database);
        }
        for (NetworkComponent webserver : DatabaseUtils.getComponents(ComponentType.WEBSERVER)) {
            webserverList.addComponent(webserver);
        }
        for (NetworkComponent firewall : DatabaseUtils.getComponents(ComponentType.FIREWALL)) {
            firewallList.addComponent(firewall);
        }
    }

    @Override
    protected void onCloseImpl() {
        databaseList.clear();
        webserverList.clear();
        firewallList.clear();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Retrieves component data from dialog and updates data in database
        AddComponentDialog addDialog = new AddComponentDialog(true);

        try {
            if (addDialog.getComponent() != null) {
                try {
                    NetworkComponent component = addDialog.getComponent();
                    DatabaseUtils.updateComponent(component);

                    switch (component.getType()) {
                        case DATABASE -> databaseList.addComponent(component);
                        case WEBSERVER -> webserverList.addComponent(component);
                        case FIREWALL -> firewallList.addComponent(component);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
