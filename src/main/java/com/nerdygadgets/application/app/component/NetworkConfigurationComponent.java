package com.nerdygadgets.application.app.component;

import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.PanelComponent;
import com.nerdygadgets.application.app.screen.NetworkConfigurationScreen;
import com.nerdygadgets.application.model.NetworkComponent;
import com.nerdygadgets.application.model.NetworkConfiguration;
import com.nerdygadgets.application.util.Colors;
import com.nerdygadgets.application.util.SwingUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NetworkConfigurationComponent extends PanelComponent {

    private final NetworkConfiguration configuration;
    private final NetworkComponent component;

    public NetworkConfigurationComponent(@NotNull final ApplicationPanel parentPanel, @NotNull final NetworkConfiguration configuration,
                                         @NotNull final NetworkComponent component) {
        super(parentPanel);

        this.configuration = configuration;
        this.component = component;

        // Configure panel
        this.setAlignmentY(TOP_ALIGNMENT);

        // Create button
        WrappedJButton button = new WrappedJButton(component.getName(), SwingUtils.getIconFromResource("remove.png"));
        button.getButton().setPreferredSize(new Dimension(400, 35));
        button.getButton().addActionListener(this::actionOnRemove);
        button.getButton().setBackground(Colors.MAIN_BACKGROUND_ACCENT);
        button.getButton().setHorizontalAlignment(SwingConstants.LEFT);
        button.getButton().setVerticalTextPosition(SwingConstants.CENTER);
        button.getButton().setIconTextGap(10);
        this.add(button);

        this.setMaximumSize(this.getPreferredSize());
    }

    /* Button actions */

    /**
     * The {@link java.awt.event.ActionListener} for when the remove button is clicked.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionOnRemove(ActionEvent event) {
        switch (component.getType()) {
            case FIREWALL -> configuration.setFirewall(null);
            case DATABASE -> configuration.removeDatabase(component);
            case WEBSERVER -> configuration.removeWebserver(component);
        }

        ((NetworkConfigurationScreen) parentPanel.parentScreen).setConfiguration(configuration);
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
