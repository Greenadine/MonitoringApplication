package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.component.NetworkConfigurationComponent;
import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.model.NetworkComponentList;
import com.nerdygadgets.application.model.NetworkConfiguration;
import com.nerdygadgets.application.model.NetworkComponent;
import com.nerdygadgets.application.util.Colors;
import com.nerdygadgets.application.util.Fonts;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;

public class ConfigurationComponentsList extends ApplicationPanel {

    private final JPanel componentsListWrapper;
    private final NetworkConfiguration configuration;
    private final ArrayList<NetworkComponent> components;

    public ConfigurationComponentsList(@NotNull ApplicationScreen parentScreen, @NotNull final String header, @NotNull final NetworkConfiguration configuration) {
        super(parentScreen);
        this.configuration = configuration;
        this.components = new ArrayList<>();
        // Configure panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel grid1 = new JPanel();
        JPanel grid2 = new JPanel();
        grid1.setLayout(new GridLayout(1,2));
        grid2.setLayout(new GridLayout(1,2));

        // Create and add header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 10));
        headerPanel.setBackground(Colors.MAIN_BACKGROUND);
        headerPanel.setBorder(new MatteBorder(1,2,2,2, Colors.MAIN_BACKGROUND_ACCENT));

        JLabel componentLabel = new JLabel(header, SwingConstants.CENTER);
        componentLabel.setFont(Fonts.NETWORK_CONFIGURATION_HEADER);
        componentLabel.setAlignmentX(CENTER_ALIGNMENT);
        headerPanel.add(componentLabel, BorderLayout.CENTER);
        grid1.add(headerPanel);

        // Create and add scroll pane
        componentsListWrapper = new JPanel();
        componentsListWrapper.setLayout(new BoxLayout(componentsListWrapper, BoxLayout.Y_AXIS));
        JScrollPane scrollableList = new JScrollPane(componentsListWrapper);
        scrollableList.setPreferredSize(new Dimension(300, 300));
        scrollableList.setBorder(new MatteBorder(0,2,0,2, Colors.MAIN_BACKGROUND_ACCENT));
        scrollableList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        grid2.add(scrollableList);

        this.add(grid1);
        this.add(grid2);
    }

    public JPanel getComponentsListWrapper() {
        return componentsListWrapper;
    }

    public void setComponentList(@NotNull NetworkConfiguration configuration, @NotNull NetworkComponentList componentList) {
        if (!componentList.isEmpty()) {
            for (NetworkComponent component : componentList.getComponents()) {
                componentsListWrapper.add(new NetworkConfigurationComponent(this, configuration, component));
            }
        }
    }

    public void addComponent(@NotNull NetworkComponent component) {
        componentsListWrapper.add(new NetworkConfigurationComponent(this, configuration, component));
        components.add(component);
    }

    public ArrayList<NetworkComponent> getComponentsList() {
        return components;
    }

    @Override
    public void onShowImpl() {
        // Do nothing
    }

    @Override
    public void onHideImpl() {
        componentsListWrapper.removeAll();
    }
}

