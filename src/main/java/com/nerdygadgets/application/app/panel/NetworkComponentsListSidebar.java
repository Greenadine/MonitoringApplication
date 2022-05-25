package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.util.Colors;
import com.nerdygadgets.application.util.Fonts;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class NetworkComponentsListSidebar extends ApplicationPanel {

    private final JScrollPane databasesList;
    private final JScrollPane webserversList;
    private final JScrollPane firewallsList;

    public NetworkComponentsListSidebar(@NotNull ApplicationScreen parentScreen) {
        super(parentScreen);

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

        JPanel databasesWrapper = new JPanel();
        databasesWrapper.setLayout(new BoxLayout(databasesWrapper, BoxLayout.Y_AXIS));
        databasesList = new JScrollPane(databasesWrapper);
        databasesList.setPreferredSize(new Dimension(100, 50));
        databasesList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(databasesList);

        // Create and add webservers list
        JPanel webserverPanel = new JPanel();
        webserverPanel.setBackground(Colors.MAIN_BACKGROUND);
        webserverPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        JLabel webserversLabel = new JLabel("Webservers");
        webserversLabel.setFont(Fonts.MAIN_SIDEBAR_HEADER);
        webserverPanel.add(webserversLabel);
        this.add(webserverPanel);

        JPanel webserversWrapper = new JPanel();
        webserversWrapper.setLayout(new BoxLayout(webserversWrapper, BoxLayout.Y_AXIS));

        webserversList = new JScrollPane(webserversWrapper);
        webserversList.setPreferredSize(new Dimension(100, 50));
        webserversList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(webserversList);

        // Create and add firewalls list
        JPanel firewallPanel = new JPanel();
        firewallPanel.setBackground(Colors.MAIN_BACKGROUND);

        firewallPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        JLabel firewallLabel = new JLabel("Firewalls");
        firewallLabel.setFont(Fonts.MAIN_SIDEBAR_HEADER);
        firewallPanel.add(firewallLabel);
        this.add(firewallPanel);

        JPanel firewallsWrapper = new JPanel();
        firewallsList = new JScrollPane(firewallsWrapper);
        firewallsList.setPreferredSize(new Dimension(100, 50));
        firewallsList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(firewallsList);
    }

    @Override
    public void onShowImpl() {
        // Do nothing
    }

    @Override
    public void onHideImpl() {
        databasesList.removeAll();
        webserversList.removeAll();
        firewallsList.removeAll();
    }
}
