package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.component.NetworkConfigurationComponent;
import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.model.component.Firewall;
import com.nerdygadgets.application.util.Colors;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class FirewallPanel extends ApplicationPanel {
    private JPanel firewallContentPanel;
    private JLabel firewallName;
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
        firewallHeaderPanel.setBorder(new MatteBorder(0, 2, 2, 2, Colors.MAIN_BACKGROUND_ACCENT));
        this.add(firewallHeaderPanel);

        JLabel firewallHeaderLabel = new JLabel("Firewall");
        firewallHeaderPanel.add(firewallHeaderLabel);
    }

    private void addFirewallContent() {
        JPanel firewallContentWrapper = new JPanel();
        firewallContentWrapper.setBorder(new MatteBorder(0,0,2,0,Colors.MAIN_BACKGROUND_ACCENT));
        firewallContentPanel = new JPanel();
        firewallContentPanel.setBorder(new EmptyBorder(0,0,-10,0));

        firewallContentWrapper.add(firewallContentPanel);
        this.add(firewallContentWrapper);

        firewallName = new JLabel("");
        firewallContentPanel.add(firewallName);
    }

    public void setFirewall(Firewall firewall) {
        if (firewall == null){
            firewallContentPanel.removeAll();
        } else{
            firewallContentPanel.add(new NetworkConfigurationComponent(this, firewall));
        }
    }

    @Override
    public void onShowImpl() {

    }

    @Override
    public void onHideImpl() {

    }
}
