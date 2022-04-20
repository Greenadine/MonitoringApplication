package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.frame.MainFrame;
import com.nerdygadgets.application.util.Fonts;
import com.nerdygadgets.application.util.SwingUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;

public class HomePanel extends JPanel {

    private final MainFrame mainFrame;

    public HomePanel(@NotNull MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.setBorder(new EmptyBorder(10, 25, 25, 25));

        // Configure panel
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);

        // Populate panel
        JLabel titleLabel = new JLabel("NerdyGadgets Network Application");
        titleLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        titleLabel.setFont(Fonts.TITLE);
        this.add(titleLabel);
        SwingUtils.addVerticalSpacer(this, 15);

        this.add(new JSeparator()); // Add separator
        SwingUtils.addVerticalSpacer(this, 15);

        SwingUtils.addButton(this, "Create New Configuration", 350, 50, this::actionCreateNetworkConfiguration);
        SwingUtils.addVerticalSpacer(this, 10);

        SwingUtils.addButton(this, "Open Network Configuration", 350, 50, this::actionOpenNetworkConfiguration);
        SwingUtils.addVerticalSpacer(this, 20);

        this.add(new JSeparator()); // Add separator
        SwingUtils.addVerticalSpacer(this, 20);

        SwingUtils.addButton(this, "Network Monitor", 350, 50, this::actionOpenNetworkMonitor);
        SwingUtils.addVerticalSpacer(this, 10);

        SwingUtils.addButton(this, "Availability Calculator", 350, 50, this::actionOpenAvailabilityCalculator);
    }

    /* Action listeners */

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
