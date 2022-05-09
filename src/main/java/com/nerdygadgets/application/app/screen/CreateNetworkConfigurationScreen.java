package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.panel.ScreenHeaderPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.model.ApplicationWindow;
import com.nerdygadgets.application.model.NetworkConfiguration;
import com.nerdygadgets.application.model.component.Database;
import com.nerdygadgets.application.model.component.NetworkComponent;
import com.nerdygadgets.application.model.component.Webserver;
import com.nerdygadgets.application.util.Colors;
import com.nerdygadgets.application.util.SwingUtils;
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

    private JPanel sidebar;

    private JXCollapsiblePane webserversListPane;
    private JXCollapsiblePane databasesListPane;
    private JXCollapsiblePane miscListPane;

    private JButton webserversListToggleButton;
    private JButton databasesListToggleButton;
    private JButton miscListToggleButton;

    public CreateNetworkConfigurationScreen(@NotNull final ApplicationWindow window) {
        super(window);

        // Configure screen
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Populate screen
        this.add(new ScreenHeaderPanel(this, "New Network Configuration", 1250, 50, this::actionReturn), BorderLayout.PAGE_START);
        createSidebar(); // Create and populate sidebar panel
    }

    /**
     * Creates and populates the sidebar {@link JPanel}.
     */
    private void createSidebar() {
        sidebar = new JPanel();
        sidebar.setBorder(new MatteBorder(0, 0, 0, 3, Colors.MAIN_BACKGROUND_ACCENT));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        this.add(sidebar, BorderLayout.LINE_START);

        createWebserversList();
        SwingUtils.addHorizontalSeparator(sidebar);
        createDatabasesList();
        SwingUtils.addHorizontalSeparator(sidebar);
        createMiscList();
    }

    /**
     * Creates and populates the {@link Webserver}s list.
     */
    private void createWebserversList() {
        // Create new collapsible pane
        webserversListPane = new JXCollapsiblePane();
        webserversListPane.setLayout(new BoxLayout(webserversListPane.getContentPane(), BoxLayout.Y_AXIS));
        webserversListPane.setBorder(new EmptyBorder(5, 10, 5, 10));
        webserversListPane.setAlignmentX(LEFT_ALIGNMENT);

        // Create and add toggle button for collapsible pane
        webserversListToggleButton = SwingUtils.createButton("Webservers", new ImageIcon(getClass().getResource("/assets/icons/arrow-up.png")), 250, 40, this::actionToggleWebserversList);
        webserversListToggleButton.setHorizontalAlignment(SwingConstants.LEFT);
        sidebar.add(webserversListToggleButton);
        sidebar.add(webserversListPane);

        /* Add contents */
        // TODO populate pane with all webserver options

        JLabel testLabel = new JLabel("Test");
        webserversListPane.add(testLabel);
    }

    /**
     * Creates and populates the {@link Database}s list.
     */
    private void createDatabasesList() {
        // Create new collapsible pane
        databasesListPane = new JXCollapsiblePane();
        databasesListPane.setLayout(new BoxLayout(databasesListPane.getContentPane(), BoxLayout.Y_AXIS));
        databasesListPane.setBorder(new EmptyBorder(5, 10, 5, 10));
        databasesListPane.setAlignmentX(LEFT_ALIGNMENT);

        // Create and add toggle button for collapsible pane
        databasesListToggleButton = SwingUtils.createButton("Databases", new ImageIcon(getClass().getResource("/assets/icons/arrow-up.png")), 250, 40, this::actionToggleDatabasesList);
        databasesListToggleButton.setHorizontalAlignment(SwingConstants.LEFT);
        sidebar.add(databasesListToggleButton);
        sidebar.add(databasesListPane);

        /* Add contents */
        // TODO populate pane with all databases options

        for (int i = 0; i < 5; i++) {
            JLabel testLabel = new JLabel("Test " + (i + 1));
            databasesListPane.add(testLabel);
        }
    }

    /**
     * Creates and populates the miscellaneous {@link NetworkComponent}s list.
     */
    private void createMiscList() {
        // Create new collapsible pane
        miscListPane = new JXCollapsiblePane();
        miscListPane.setLayout(new BoxLayout(miscListPane.getContentPane(), BoxLayout.Y_AXIS));
        miscListPane.setBorder(new EmptyBorder(5, 10, 5, 10));
        miscListPane.setAlignmentX(LEFT_ALIGNMENT);

        // Create and add toggle button for collapsible pane
        miscListToggleButton = SwingUtils.createButton("Miscellaneous", new ImageIcon(getClass().getResource("/assets/icons/arrow-up.png")), 250, 40, this::actionToggleMiscList);
        miscListToggleButton.setHorizontalAlignment(SwingConstants.LEFT);
        sidebar.add(miscListToggleButton);
        sidebar.add(miscListPane);

        /* Add contents */
        // TODO populate pane with all databases options

        JLabel testLabel = new JLabel("Test");
        miscListPane.add(testLabel);
    }

    /* Button Actions */

    private void actionReturn(ActionEvent event) {
        window.openScreen("network-configurations");
    }

    /**
     * The {@link Action} that is performed when the {@link Webserver}s list is expanded or collapsed.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionToggleWebserversList(ActionEvent event) {
        togglePane(webserversListPane, webserversListToggleButton);
    }

    /**
     * The {@link Action} that is performed when the {@link Database}s list is expanded or collapsed.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionToggleDatabasesList(ActionEvent event) {
        togglePane(databasesListPane, databasesListToggleButton);
    }

    /**
     * The {@link Action} that is performed when the miscellaneous {@link NetworkComponent}s list is expanded or collapsed.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionToggleMiscList(ActionEvent event) {
        togglePane(miscListPane, miscListToggleButton);
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
        // Temporarily disable animations to avoid having to wait for the animation to finish
        webserversListPane.setAnimated(false);
        databasesListPane.setAnimated(false);
        miscListPane.setAnimated(false);

        // (Re)expand all lists
        webserversListPane.setCollapsed(false);
        databasesListPane.setCollapsed(false);
        miscListPane.setCollapsed(false);

        // Re-enable animations
        webserversListPane.setAnimated(true);
        databasesListPane.setAnimated(true);
        miscListPane.setAnimated(true);
    }

    @Override
    public void onCloseImpl() {
        // Nothing
    }
}
