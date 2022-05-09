package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.window.MainWindow;
import com.nerdygadgets.application.util.ApplicationActions;
import com.nerdygadgets.application.util.Colors;
import com.nerdygadgets.application.util.Fonts;
import com.nerdygadgets.application.util.SwingUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NetworkComponentsScreen extends ApplicationScreen {

    private final MainWindow mainWindow;

    public NetworkComponentsScreen(@NotNull final MainWindow mainWindow) {
        super(mainWindow);
        this.mainWindow = mainWindow;

        // Configure screen
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(10, 25, 25, 25));

        // Populate screen
        final JLabel titleLabel = new JLabel("Network Components");
        titleLabel.setFont(Fonts.MAIN_TITLE);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.add(titleLabel);
        SwingUtils.addVerticalSpacer(this, 15);

        SwingUtils.addButton(this, "View Components", null, 350, 50, this::actionOpenViewComponents);
        SwingUtils.addVerticalSpacer(this, 10);

        SwingUtils.addButton(this, "Add New Component", null, 350, 50, this::actionOpenAddNewComponent);
        SwingUtils.addVerticalSpacer(this, 10);

        this.add(new JSeparator());
        SwingUtils.addVerticalSpacer(this, 10);

        JButton homeButton = SwingUtils.addButton(this, "Return",  null, 125, 35, ApplicationActions::openHome);
        homeButton.setBackground(Colors.MAIN_ACCENT);
        homeButton.setForeground(Color.WHITE);
    }

    /* Button actions */

    /**
     * The {@link java.awt.event.ActionListener} for when the "View Components" button is clicked.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionOpenViewComponents(ActionEvent event) {
        // TODO
    }

    /**
     * The {@link java.awt.event.ActionListener} for when the "Add New Component" button is clicked.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionOpenAddNewComponent(ActionEvent event) {
        // TODO
    }

    @Override
    protected void onOpenImpl() {
        // Do nothing
    }

    @Override
    protected void onCloseImpl() {
        // Do nothing
    }
}
