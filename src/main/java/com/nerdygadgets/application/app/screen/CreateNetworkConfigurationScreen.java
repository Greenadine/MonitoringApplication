package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.ApplicationFrame;
import com.nerdygadgets.application.model.NetworkConfiguration;
import com.nerdygadgets.application.model.component.Database;
import com.nerdygadgets.application.model.component.NetworkComponent;
import com.nerdygadgets.application.model.component.Webserver;
import com.nerdygadgets.application.util.Colors;
import com.nerdygadgets.application.util.Fonts;
import com.nerdygadgets.application.util.SwingUtils;
import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.table.LabelProperties;
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
public class CreateNetworkConfigurationScreen extends AbstractApplicationScreen {

    private final ApplicationFrame applicationFrame; // Main window

    private JPanel sidebar;

    private JXCollapsiblePane webserversListPane;
    private JXCollapsiblePane databasesListPane;
    private JXCollapsiblePane miscListPane;

    private JButton webserversListToggleButton;
    private JButton databasesListToggleButton;
    private JButton miscListToggleButton;

    public CreateNetworkConfigurationScreen(@NotNull final ApplicationFrame applicationFrame) {
        super(applicationFrame);
        this.applicationFrame = applicationFrame;

        // Configure screen
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        createHeader(); // Create and populate header panel
        createSidebar(); // Create and populate sidebar panel
    }

    /**
     * Creates and populates the header {@link JPanel}.
     */
    private void createHeader() {
        // Create and configure panel
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
        header.setBackground(Colors.BACKGROUND);
        header.setPreferredSize(new Dimension(1250, 50));
        this.add(header, BorderLayout.PAGE_START);

        /* Populate panel */

        // Add home button
        JButton homeButton = SwingUtils.createButton("Home", new ImageIcon("assets\\icons\\home.png"), this::actionReturnToHome);
        homeButton.setBackground(Colors.ACCENT);
        homeButton.setForeground(Color.WHITE);
        homeButton.setIconTextGap(15);
        homeButton.setBorder(new EmptyBorder(15, 10, 15, 10));
        header.add(homeButton);

        SwingUtils.addVerticalSeparator(header);  // Add separator

        // Add title label
        JLabel titleLabel = new JLabel("Create Network Configuration");
        titleLabel.setFont(Fonts.TITLE);
        titleLabel.setBorder(new EmptyBorder(5, 5, 5, 450)); // TODO center label properly (without this)
        header.add(titleLabel);
    }

    /**
     * Creates and populates the sidebar {@link JPanel}.
     */
    private void createSidebar() {
        sidebar = new JPanel();
        sidebar.setBorder(new MatteBorder(0, 0, 0, 3, Colors.BACKGROUND_ACCENT));
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

        // Create and add toggle button for collapsible pane
        webserversListToggleButton = SwingUtils.createButton("Webservers", new ImageIcon("assets\\icons\\arrow-up.png"), 250, 40, this::actionToggleWebserversList);
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

        // Create and add toggle button for collapsible pane
        databasesListToggleButton = SwingUtils.createButton("Databases", new ImageIcon("assets\\icons\\arrow-up.png"), 250, 40, this::actionToggleDatabasesList);
        databasesListToggleButton.setHorizontalAlignment(SwingConstants.LEFT);
        sidebar.add(databasesListToggleButton);
        sidebar.add(databasesListPane);

        /* Add contents */
        // TODO populate pane with all databases options

        JLabel testLabel = new JLabel("Test");
        databasesListPane.add(testLabel);
    }

    /**
     * Creates and populates the miscellaneous {@link NetworkComponent}s list.
     */
    private void createMiscList() {
        // Create new collapsible pane
        miscListPane = new JXCollapsiblePane();
        miscListPane.setLayout(new BoxLayout(miscListPane.getContentPane(), BoxLayout.Y_AXIS));

        // Create and add toggle button for collapsible pane
        miscListToggleButton = SwingUtils.createButton("Misc.", new ImageIcon("assets\\icons\\arrow-up.png"), 250, 40, this::actionToggleMiscList);
        miscListToggleButton.setHorizontalAlignment(SwingConstants.LEFT);
        sidebar.add(miscListToggleButton);
        sidebar.add(miscListPane);

        /* Add contents */
        // TODO populate pane with all databases options

        JLabel testLabel = new JLabel("Test");
        miscListPane.add(testLabel);
    }

    /* Button Actions */

    /**
     * The {@link Action} that is performed when the "Return to home" button is pressed.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionReturnToHome(ActionEvent event) {
        applicationFrame.getHomeScreen().open();
    }

    /**
     * The {@link Action} that is performed when the {@link Webserver}s list is expanded or collapsed.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionToggleWebserversList(ActionEvent event) {
        webserversListPane.setCollapsed(!webserversListPane.isCollapsed()); // Toggle collapsed

        if (webserversListPane.isCollapsed()) {
            webserversListToggleButton.setIcon(new ImageIcon("assets\\icons\\arrow-down.png"));
        } else {
            webserversListToggleButton.setIcon(new ImageIcon("assets\\icons\\arrow-up.png"));
        }
    }

    /**
     * The {@link Action} that is performed when the {@link Database}s list is expanded or collapsed.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionToggleDatabasesList(ActionEvent event) {
        databasesListPane.setCollapsed(!databasesListPane.isCollapsed()); // Toggle collapsed

        if (databasesListPane.isCollapsed()) {
            databasesListToggleButton.setIcon(new ImageIcon("assets\\icons\\arrow-down.png"));
        } else {
            databasesListToggleButton.setIcon(new ImageIcon("assets\\icons\\arrow-up.png"));
        }
    }

    /**
     * The {@link Action} that is performed when the miscellaneous {@link NetworkComponent}s list is expanded or collapsed.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionToggleMiscList(ActionEvent event) {
        miscListPane.setCollapsed(!miscListPane.isCollapsed()); // Toggle collapsed

        if (miscListPane.isCollapsed()) {
            miscListToggleButton.setIcon(new ImageIcon("assets\\icons\\arrow-down.png"));
        } else {
            miscListToggleButton.setIcon(new ImageIcon("assets\\icons\\arrow-up.png"));
        }
    }

    /**
     * Method to be executed prior to every time this panel will be displayed.
     */
    public void preOpen() {
        webserversListPane.setCollapsed(false);
        databasesListPane.setCollapsed(false);
        miscListPane.setCollapsed(false);
    }
}
