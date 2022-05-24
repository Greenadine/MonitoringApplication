package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.util.Colors;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class NetworkComponentsListSidebar extends ApplicationPanel {

    private final  JScrollPane scrollableWebserversList;
    private final  JScrollPane scrollableDatabasesList;
    private final  JScrollPane scrollableFirewallsList;


    public NetworkComponentsListSidebar(@NotNull ApplicationScreen parentScreen) {
        super(parentScreen);

        // Configure panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(300, 500));

        // Create and add webservers list
        JPanel webserverPanel = new JPanel();
        webserverPanel.setBackground(Colors.MAIN_BACKGROUND);
        webserverPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        JLabel webserversLabel = new JLabel("Webservers");
        webserverPanel.add(webserversLabel);
        this.add(webserverPanel);

        JPanel webserversWrapper = new JPanel();
        webserversWrapper.setLayout(new BoxLayout(webserversWrapper, BoxLayout.Y_AXIS));

        scrollableWebserversList = new JScrollPane(webserversWrapper);
        scrollableWebserversList.setPreferredSize(new Dimension(100, 50));
        scrollableWebserversList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollableWebserversList);

        // Create and add databases list
        JPanel databasePanel = new JPanel();
        databasePanel.setBackground(Colors.MAIN_BACKGROUND);

        databasePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        JLabel databasesLabel = new JLabel("Databases");
        databasePanel.add(databasesLabel);
        this.add(databasePanel);

        JPanel databasesWrapper = new JPanel();
        databasesWrapper.setLayout(new BoxLayout(databasesWrapper, BoxLayout.Y_AXIS));
        scrollableDatabasesList = new JScrollPane(databasesWrapper);
        scrollableDatabasesList.setPreferredSize(new Dimension(100, 50));
        scrollableDatabasesList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollableDatabasesList);

        // Create and add firewalls list
        JPanel firewallPanel = new JPanel();
        firewallPanel.setBackground(Colors.MAIN_BACKGROUND);

        firewallPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        JLabel firewallLabel = new JLabel("Firewalls");
        firewallPanel.add(firewallLabel);
        this.add(firewallPanel);

        JPanel firewallsWrapper = new JPanel();
        scrollableFirewallsList = new JScrollPane(firewallsWrapper);
        scrollableFirewallsList.setPreferredSize(new Dimension(100, 50));
        scrollableFirewallsList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollableFirewallsList);
    }

    @Override
    public void onShowImpl() {
        // TODO
    }

    @Override
    public void onHideImpl() {
        // TODO
    }

    public JScrollPane getScrollableWebserversList() {
        return scrollableWebserversList;
    }

    public JScrollPane getScrollableDatabasesList() {
        return scrollableDatabasesList;
    }

    public JScrollPane getScrollableFirewallsList() {
        return scrollableFirewallsList;
    }
}
