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
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

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

    public CreateNetworkConfigurationScreen(@NotNull final ApplicationFrame applicationFrame) throws IOException {
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
    private void createHeader() throws IOException {
        // Create and configure panel
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
        header.setBorder(new MatteBorder(0, 0, 5, 0, Colors.BACKGROUND_ACCENT));
        header.setBackground(Colors.BACKGROUND);
        header.setPreferredSize(new Dimension(1250, 50));
        this.add(header, BorderLayout.PAGE_START);

        /* Populate panel */

        // Add home button
        JButton homeButton = SwingUtils.createButton("Home", new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/assets/icons/home.png"))), this::actionReturnToHome);
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
        miscListToggleButton = SwingUtils.createButton("Misc.", new ImageIcon(getClass().getResource("/assets/icons/arrow-up.png")), 250, 40, this::actionToggleMiscList);
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
    public void preOpen() {
        webserversListPane.setCollapsed(false);
        databasesListPane.setCollapsed(false);
        miscListPane.setCollapsed(false);
    }

    @Override
    public void postClose() {
        // Nothing
    }
}
