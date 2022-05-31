package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.component.NetworkConfigurationComponent;
import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.model.NetworkConfiguration;
import com.nerdygadgets.application.model.NetworkComponent;
import com.nerdygadgets.application.util.Colors;
import com.nerdygadgets.application.util.Fonts;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class FirewallPanel extends ApplicationPanel {

    private JPanel firewallContentPanel;
    private NetworkComponent firewall;

    public FirewallPanel(@NotNull ApplicationScreen parentScreen) {
        super(parentScreen);
        // Configure panel
        this.setLayout(new GridLayout(1,2));
        this.setPreferredSize(new Dimension(100, 50));

        // Populate panel
        addFirewallHeader();
        addFirewallContent();
    }

    private void addFirewallHeader() {
        JPanel firewallHeaderPanel = new JPanel();
        firewallHeaderPanel.setLayout(new BorderLayout());
        firewallHeaderPanel.setBackground(Colors.MAIN_BACKGROUND);
        firewallHeaderPanel.setBorder(new MatteBorder(0, 2, 2, 2, Colors.MAIN_BACKGROUND_ACCENT));
        this.add(firewallHeaderPanel);

        JLabel firewallHeaderLabel = new JLabel("Firewall", SwingConstants.CENTER);
        firewallHeaderLabel.setFont(Fonts.NETWORK_CONFIGURATION_HEADER);
        firewallHeaderPanel.add(firewallHeaderLabel, BorderLayout.CENTER);
    }

    private void addFirewallContent() {
        JPanel firewallContentWrapper = new JPanel();
        firewallContentWrapper.setBorder(new MatteBorder(-10,0,2,0,Colors.MAIN_BACKGROUND_ACCENT));
        firewallContentPanel = new JPanel();

        firewallContentWrapper.add(firewallContentPanel);
        this.add(firewallContentWrapper);
    }

    public void setFirewall(@NotNull final NetworkConfiguration configuration, @NotNull final NetworkComponent firewall) {
        firewallContentPanel.add(new NetworkConfigurationComponent(this, configuration, firewall));
        this.firewall = firewall;

        firewallContentPanel.revalidate();
        firewallContentPanel.repaint();
    }

    public void clear() {
        this.firewall = null;
        firewallContentPanel.removeAll();
        firewallContentPanel.revalidate();
        firewallContentPanel.repaint();
    }

    public NetworkComponent getFirewall(){
        return firewall;
    }

    @Override
    public void onShowImpl() {
        // Do nothing
    }

    @Override
    public void onHideImpl() {
        this.firewall = null;
        firewallContentPanel.removeAll();
    }
}
