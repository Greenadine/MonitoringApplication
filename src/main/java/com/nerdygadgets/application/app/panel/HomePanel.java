package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.frame.MainFrame;
import com.nerdygadgets.application.util.Fonts;
import com.nerdygadgets.application.util.Utils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class HomePanel extends JPanel {

    private final MainFrame mainFrame;

    public HomePanel(@NotNull MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.setBorder(new EmptyBorder(10, 25, 10, 25));

        // Configure panel
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        // Populate panel
        addButton("Create New Configuration", this::actionCreateNetworkConfiguration);
        addSpacer(10);

        addButton("Open Network Configuration", this::actionOpenNetworkConfiguration);
        addSpacer(20);

        this.add(new JSeparator()); // Add separator
        addSpacer(20);

        addButton("Network Monitor", this::actionOpenNetworkMonitor);
        addSpacer(10);

        addButton("Availability Calculator", this::actionOpenAvailabilityCalculator);
    }

    /* Utility methods */

    private void addButton(@NotNull String text, @NotNull ActionListener listener) {
        JButton button = new JButton(text);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(Fonts.DEFAULT);
        Utils.setSizeAll(button, new Dimension(350, 50));
        button.addActionListener(listener);
        this.add(button);
    }

    private void addSpacer(int height) {
        this.add(Box.createVerticalStrut(height));
    }

    /* Action methods */

    private void actionOpenNetworkMonitor(ActionEvent event) {
        // TODO
    }

    private void actionOpenAvailabilityCalculator(ActionEvent event) {
        // TODO
    }

    private void actionCreateNetworkConfiguration(ActionEvent event) {
        mainFrame.openCreateNetworkConfigurationPanel();
    }

    private void actionOpenNetworkConfiguration(ActionEvent event) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Open a network configuration file");
        FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Network Configuration Files (.json)", "json");
        chooser.addChoosableFileFilter(extensionFilter);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (chooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
            File selected = chooser.getSelectedFile();

            System.out.println(selected.getName());
            // TODO functionality
        }
    }
}
