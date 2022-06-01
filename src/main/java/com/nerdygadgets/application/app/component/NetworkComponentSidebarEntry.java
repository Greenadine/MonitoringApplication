package com.nerdygadgets.application.app.component;

import com.nerdygadgets.application.app.model.PanelComponent;
import com.nerdygadgets.application.app.panel.NetworkComponentsListSidebar;
import com.nerdygadgets.application.app.screen.NetworkConfigurationScreen;
import com.nerdygadgets.application.model.NetworkComponent;
import com.nerdygadgets.application.model.NetworkConfiguration;
import com.nerdygadgets.application.util.ApplicationUtils;
import com.nerdygadgets.application.util.Colors;
import com.nerdygadgets.application.util.SwingUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NetworkComponentSidebarEntry extends PanelComponent {

    private final NetworkConfigurationScreen configurationScreen;

    private final NetworkComponentsListSidebar sidebar;

    private final NetworkComponent component;
    private final NetworkConfiguration configuration;

    public NetworkComponentSidebarEntry(@NotNull NetworkComponentsListSidebar parentPanel, @NotNull final NetworkComponent component, @NotNull final NetworkConfiguration configuration) {
        super(parentPanel);

        this.configurationScreen = (NetworkConfigurationScreen) parentPanel.parentScreen;
        this.sidebar = parentPanel;
        this.configuration = configuration;
        this.component = component;

        // Configure panel
        this.setAlignmentY(TOP_ALIGNMENT);

        // Create button
        WrappedJButton button = new WrappedJButton(component.getName(), SwingUtils.getIconFromResource("add.png"));
        button.getButton().setPreferredSize(new Dimension(250, 35));
        button.getButton().addActionListener(this::actionAddToConfiguration);
        button.getButton().setBackground(Colors.MAIN_BACKGROUND_ACCENT);
        button.getButton().setHorizontalAlignment(SwingConstants.LEFT);
        button.getButton().setVerticalTextPosition(SwingConstants.CENTER);
        button.getButton().setIconTextGap(10);
        this.add(button);

        this.setMaximumSize(this.getPreferredSize());
    }

    private void actionAddToConfiguration(ActionEvent event) {
        switch (component.getType()) {
            case DATABASE -> {
                configuration.addDatabase(component);
                sidebar.getConfigurationDatabases().addComponent(component);
            }
            case WEBSERVER -> {
                configuration.addWebserver(component);
                sidebar.getConfigurationWebservers().addComponent(component);
            }
            case FIREWALL -> {
                if (configuration.getFirewall() != null) {
                    ApplicationUtils.showPopupInfoDialog("Configuration Already Has a Firewall", "First remove the current firewall before adding a new one.");
                } else {
                    configuration.setFirewall(component);
                    sidebar.getConfigurationFirewall().setFirewall(configuration, component);
                }
            }
        }

        configurationScreen.setConfiguration(configuration);
    }
    @Override
    public void onShow() {
        // Do nothing
    }

    @Override
    public void onHide() {
        // Do nothing
    }
}
