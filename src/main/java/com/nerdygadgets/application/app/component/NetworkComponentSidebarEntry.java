package com.nerdygadgets.application.app.component;

import com.nerdygadgets.application.app.model.PanelComponent;
import com.nerdygadgets.application.app.panel.NetworkComponentsListSidebar;
import com.nerdygadgets.application.app.screen.NetworkConfigurationScreen;
import com.nerdygadgets.application.model.NetworkComponent;
import com.nerdygadgets.application.model.NetworkConfiguration;
import com.nerdygadgets.application.util.Colors;
import com.nerdygadgets.application.util.SwingUtils;
import org.jetbrains.annotations.NotNull;

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

        // Configure panel
        this.setAlignmentY(TOP_ALIGNMENT);

        this.component = component;

        // Create button
        WrappedJButton button = new WrappedJButton(component.getName(), SwingUtils.getIconFromResource("add.png"));
        button.getButton().setPreferredSize(new Dimension(250, 35));
        button.getButton().addActionListener(this::actionAddToConfiguration);
        button.getButton().setBackground(Colors.MAIN_BACKGROUND_ACCENT);
        this.add(button);

        this.setMaximumSize(this.getPreferredSize());
    }

    private void actionAddToConfiguration(ActionEvent event) {
        switch (component.getType()){
            case DATABASE:
                System.out.println("hoi");
                configuration.addDatabase(component);
                sidebar.getConfigurationDatabases().addComponent(component);
                break;
            case WEBSERVER:
                configuration.addWebserver(component);
                sidebar.getConfigurationWebservers().addComponent(component);
                break;
            case FIREWALL:
                configuration.setFirewall(component);
                sidebar.getConfigurationFirewall().setFirewall(configuration, component);
                break;
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
