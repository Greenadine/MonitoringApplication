package com.nerdygadgets.application.app.component;

import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.PanelComponent;
import com.nerdygadgets.application.app.panel.networkcomponents.NetworkComponentDetailsPanel;
import com.nerdygadgets.application.model.component.NetworkComponent;
import com.nerdygadgets.application.util.Colors;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.ActionEvent;

public class NetworkComponentsEntry extends PanelComponent {

    private final NetworkComponent component;

    private final NetworkComponentDetailsPanel detailsPanel;

    public <T extends NetworkComponent> NetworkComponentsEntry(@NotNull ApplicationPanel parentPanel, @NotNull final T component, @NotNull final NetworkComponentDetailsPanel detailsPanel) {
        super(parentPanel);

        // Configure panel
        this.setAlignmentY(TOP_ALIGNMENT);

        this.component = component;
        this.detailsPanel = detailsPanel;

        // Create button
        WrappedJButton button = new WrappedJButton(component.getName());
        button.setPreferredSize(new Dimension(290, 35));
        button.getButton().addActionListener(this::actionShowDetails);
        button.getButton().setBackground(Colors.NETWORK_COMPONENTS_LIST_ENTRY_BACKGROUND);
        this.add(button);

        this.setMaximumSize(this.getPreferredSize());
    }

    private void actionShowDetails(ActionEvent event) {
        detailsPanel.displayComponent(component);
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
