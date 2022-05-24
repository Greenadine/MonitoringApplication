package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.component.DropdownListComponent;
import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.util.Colors;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
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
        webserverPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        JLabel webserversLabel = new JLabel("Webservers");
        webserverPanel.add(webserversLabel);
        add(webserverPanel);

        JPanel webserversWrapper = new JPanel();
        webserversWrapper.setLayout(new BoxLayout(webserversWrapper, BoxLayout.Y_AXIS));
        scrollableWebserversList = new JScrollPane(webserversWrapper);
        webserversWrapper.add(new JLabel("test1"));
        scrollableWebserversList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.add(scrollableWebserversList);

        // Create and add databases list
        JPanel databasePanel = new JPanel();
        databasePanel.setBackground(Colors.MAIN_BACKGROUND);

        databasePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        JLabel databasesLabel = new JLabel("Databases");
        databasePanel.add(databasesLabel);
        add(databasePanel);

        JPanel databasesWrapper = new JPanel();
        databasesWrapper.setLayout(new BoxLayout(databasesWrapper, BoxLayout.Y_AXIS));
        databasesWrapper.setPreferredSize(new Dimension(100, 50));
        scrollableDatabasesList = new JScrollPane(databasesWrapper);
        for (int i = 0; i < 20; i++) {
            databasesWrapper.add(new JLabel("test " + (i + 1)));
//            JButton addBtn = new JButton("+");
//            addBtn.setBounds(10, 100, 30, 25);
//            addBtn.setBorder(new RoundedBorder(10)); //10 is the radius
//            addBtn.setForeground(Color.BLUE);
//            databasesWrapper.add(addBtn);
        }
        scrollableDatabasesList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollableDatabasesList);

        // Create and add firewalls list
        JPanel firewallPanel = new JPanel();
        firewallPanel.setBackground(Colors.MAIN_BACKGROUND);

        firewallPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        JLabel firewallLabel = new JLabel("Firewalls");
        firewallPanel.add(firewallLabel);
        add(firewallPanel);

        JPanel firewallsWrapper = new JPanel();
        scrollableFirewallsList = new JScrollPane(firewallsWrapper);
        scrollableFirewallsList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.add(scrollableFirewallsList);
    }

    @Override
    public void onShowImpl() {

    }

    @Override
    public void onHideImpl() {

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

    private static class RoundedBorder implements Border {

        private int radius;


        RoundedBorder(int radius) {
            this.radius = radius;
        }


        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }


        public boolean isBorderOpaque() {
            return true;
        }


        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }
}
