package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.model.ApplicationWindow;
import com.nerdygadgets.application.app.panel.NetworkComponentsListSidebar;
import com.nerdygadgets.application.app.panel.ScreenHeaderPanel;
import com.nerdygadgets.application.model.NetworkConfiguration;
import com.nerdygadgets.application.model.component.Database;
import com.nerdygadgets.application.model.component.NetworkComponent;
import com.nerdygadgets.application.model.component.Webserver;
import org.jdesktop.swingx.JXCollapsiblePane;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * A {@link JPanel} for creating new {@link NetworkConfiguration}s.
 *
 * @author Kevin Zuman
 */
public class CreateNetworkConfigurationScreen extends ApplicationScreen {

    private final NetworkComponentsListSidebar sidebar;

    public CreateNetworkConfigurationScreen(@NotNull final ApplicationWindow window) {
        super(window);

        // Configure screen
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Populate screen
        this.add(new ScreenHeaderPanel(this, "New Network Configuration", 1250, 50, this::actionReturn), BorderLayout.PAGE_START);

        //createSidebar(); // Create and populate sidebar panel
        sidebar = new NetworkComponentsListSidebar(this);
        this.add(sidebar, BorderLayout.LINE_START);

        JLabel label1 = new JLabel("Test1");
        label1.setBorder(new MatteBorder(5, 5, 5, 5, Color.GREEN));

        sidebar.getWebserversListPane().addComponent(label1);
        sidebar.getFirewallListPane().addComponent(new JLabel("Test1"));

        for (int i = 0; i < 5; i++) {
            JLabel testLabel = new JLabel("Test " + (i + 1));
            sidebar.getDatabasesListPane().addComponent(testLabel);
        }
    }

    /* Button Actions */

    private void actionReturn(ActionEvent event) {
        window.openScreen("network-configurations");
    }

    /* Utility methods */

    /**
     * Toggles the given {@link JXCollapsiblePane}, as well as updates the pane's toggle {@link JButton}.
     *
     * @param collapsiblePane The {@code JXCollapsiblePane}.
     * @param toggleButton The {@code JButton} that toggles the {@code JXCollapsiblePane}.
     */
    private void togglePane(@NotNull final JXCollapsiblePane collapsiblePane, @NotNull final JButton toggleButton) {
        collapsiblePane.setCollapsed(!collapsiblePane.isCollapsed()); // Toggle collapsed

        if (collapsiblePane.isCollapsed()) {
            toggleButton.setIcon(new ImageIcon(getClass().getResource("/assets/icons/arrow-down.png")));
        } else {
            toggleButton.setIcon(new ImageIcon(getClass().getResource("/assets/icons/arrow-up.png")));
        }
    }

    @Override
    public void onOpenImpl() {
        // Do nothing
    }

    @Override
    public void onCloseImpl() {
        // Do nothing
    }
}
