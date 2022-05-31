package com.nerdygadgets.application.app.component;

import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.PanelComponent;
import com.nerdygadgets.application.app.screen.ViewNetworkConfigurationScreen;
import com.nerdygadgets.application.model.NetworkConfiguration;
import com.nerdygadgets.application.model.NetworkComponent;
import com.nerdygadgets.application.util.Colors;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NetworkConfigurationComponent extends PanelComponent {

    private final NetworkConfiguration configuration;
    private final NetworkComponent component;

    private final JLabel componentName;
    private final JButton removeBtn;

    public NetworkConfigurationComponent(@NotNull final ApplicationPanel parentPanel, @NotNull final NetworkConfiguration configuration, @NotNull final NetworkComponent component) {
        super(parentPanel);

        this.configuration = configuration;
        this.component = component;

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BorderLayout());
        wrapper.setPreferredSize(new Dimension(200, 50));
        wrapper.setBorder(new EmptyBorder(0,0,6,0));
        wrapper.setBackground(Colors.MAIN_TABLE_HEADER);

        componentName = new JLabel(component.getName());
        componentName.setBorder(new EmptyBorder(0,5, 0, 0));
        wrapper.add(componentName, BorderLayout.CENTER);

        JPanel buttonWrapperPanel = new JPanel();
        buttonWrapperPanel.setBackground(Colors.MAIN_TABLE_HEADER);

        removeBtn = new JButton("-");
        removeBtn.addActionListener(this::actionOnRemove);
        removeBtn.setAlignmentX(LEFT_ALIGNMENT);
        buttonWrapperPanel.add(removeBtn);
        wrapper.add(buttonWrapperPanel, BorderLayout.LINE_END);

        this.add(wrapper);
    }

    /* Button actions */

    /**
     * The {@link java.awt.event.ActionListener} for when the remove button is clicked.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionOnRemove(ActionEvent event) {
        switch (component.getType()) {
            case FIREWALL:
                configuration.setFirewall(null);
            case DATABASE:
                configuration.removeDatabase(component);
                break;
            case WEBSERVER:
                configuration.removeWebserver(component);
        }

        ((ViewNetworkConfigurationScreen) parentPanel.parentScreen).setConfiguration(configuration);
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
