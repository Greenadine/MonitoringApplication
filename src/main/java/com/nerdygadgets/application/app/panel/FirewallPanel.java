package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.util.Colors;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class FirewallPanel extends ApplicationPanel {
    public FirewallPanel(@NotNull ApplicationScreen parentScreen) {
        super(parentScreen);
        // Configure panel
        this.setLayout(new GridLayout(1,2));
        this.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));

        // Populate panel
        addFirewallHeader();
        addFirewallContent();
    }

    private void addFirewallHeader() {
        JPanel firewallHeaderPanel = new JPanel();
        firewallHeaderPanel.setBackground(Colors.MAIN_BACKGROUND);
        firewallHeaderPanel.setBorder(new MatteBorder(0, 2, 2, 5, Colors.MAIN_BACKGROUND_ACCENT));
        this.add(firewallHeaderPanel);

        JLabel firewallHeaderLabel = new JLabel("Firewall");
        firewallHeaderPanel.add(firewallHeaderLabel);
    }

    private void addFirewallContent() {
        JPanel firewallContentPanel = new JPanel();

        this.add(firewallContentPanel);

        JLabel placeHolder = new JLabel("");
        firewallContentPanel.add(placeHolder);
    }

    @Override
    public void onShowImpl() {

    }

    @Override
    public void onHideImpl() {

    }
}
