package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.ApplicationWindow;
import com.nerdygadgets.application.util.Fonts;
import com.nerdygadgets.application.util.SwingUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;

public class HomeScreen extends ApplicationScreen {

    public HomeScreen(@NotNull ApplicationWindow applicationWindow) {
        super(applicationWindow);

        // Configure screen
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(10, 25, 25, 25));

        // Populate screen
        JLabel titleLabel = new JLabel("NerdyGadgets Network Application");
        titleLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        titleLabel.setFont(Fonts.MAIN_TITLE);
        this.add(titleLabel);
        SwingUtils.addVerticalSpacer(this, 15);

        SwingUtils.addButton(this, "Open Network Configuration", null, 350, 50, this::actionOpenNetworkConfiguration);
        SwingUtils.addVerticalSpacer(this, 10);

        SwingUtils.addButton(this, "Create New Configuration", null, 350, 50, this::actionCreateNetworkConfiguration);
        SwingUtils.addVerticalSpacer(this, 20);

        this.add(new JSeparator()); // Add separator
        SwingUtils.addVerticalSpacer(this, 20);

        SwingUtils.addButton(this, "Network Monitor",  null, 350, 50, this::actionOpenNetworkMonitor);
    }

    /* Action listeners */

    /**
     * The {@link java.awt.event.ActionListener} for when the "Create New Configuration" button is clicked.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionCreateNetworkConfiguration(ActionEvent event) {
        window.getCreateNetworkConfigurationScreen().open();
    }

    /**
     * The {@link java.awt.event.ActionListener} for when the "Open Network Configuration" button is clicked.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionOpenNetworkConfiguration(ActionEvent event) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Open a network configuration file");
        FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Network Configuration Files (.json)", "json");
        chooser.addChoosableFileFilter(extensionFilter);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (chooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
            File selected = chooser.getSelectedFile();

            System.out.println(selected.getName());
            // TODO open network configuration functionality
        }
    }

    /**
     * The {@link java.awt.event.ActionListener} for when the "Network Monitor" button is clicked.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionOpenNetworkMonitor(ActionEvent event) {
        window.getNetworkMonitorScreen().open();
    }

    @Override
    public void onOpenImpl() {
        // Nothing
    }

    @Override
    public void onCloseImpl() {
        // Nothing
    }
}
