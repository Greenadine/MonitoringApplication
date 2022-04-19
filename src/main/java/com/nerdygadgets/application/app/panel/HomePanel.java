package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.frame.MainFrame;
import com.nerdygadgets.application.util.Fonts;
import com.nerdygadgets.application.util.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel {

    private final MainFrame mainFrame;

    private final int panelWidth = MainFrame.BODY_WIDTH;
    private final int panelHeight = MainFrame.FRAME_HEIGHT;

    public HomePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        // Configure panel
        Utils.setSizeMinMax(this, new Dimension(panelWidth, panelHeight));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Create and add title label
        JLabel titleLabel = new JLabel("No Network Selected", JLabel.CENTER);
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        titleLabel.setFont(Fonts.TITLE);
        titleLabel.setBorder(new EmptyBorder(40, 0, 40, 0));
        this.add(titleLabel);

        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.X_AXIS));

        // Create "open recent" panel
        JPanel openRecentPanel = newPanel();

        JLabel openRecentLabel = newLabel("Open Recent");
        openRecentPanel.add(openRecentLabel);
        selectionPanel.add(openRecentPanel);

        // Create "open network" panel
        JPanel openNetworkPanel = newPanel();

        JLabel openNetworkLabel = newLabel("Open Configuration from File");
        openNetworkPanel.add(openNetworkLabel);

        JButton openNetworkButton = newButton("Open file...", this::actionOpenNetwork);
        openNetworkPanel.add(openNetworkButton);

        selectionPanel.add(openNetworkPanel);

        // Create "create network" panel
        JPanel createNetworkPanel = newPanel();

        JLabel createNetworkLabel = newLabel("Create Network Configuration");
        createNetworkPanel.add(createNetworkLabel);

        JButton createNetworkButton = newButton("Create New Configuration", this::actionCreateNetwork);
        createNetworkPanel.add(createNetworkButton);

        selectionPanel.add(createNetworkPanel);

        this.add(selectionPanel);
    }

    private JPanel newPanel() {
        JPanel panel = new JPanel();
        Utils.setSizeAll(panel, new Dimension((int) Math.floor(panelWidth / 3.04), panelHeight - 150)); // For some reason breaks when given anything below 3.04

        return panel;
    }

    private JLabel newLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(Fonts.DEFAULT);
        label.setBorder(new EmptyBorder(0, 0, 10, 0));
        return label;
    }

    private JButton newButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        Utils.setSizeAll(button, new Dimension(200, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(actionListener);

        return button;
    }

    /* Button actions */

    private void actionOpenNetwork(ActionEvent event) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Open a network configuration file");
        FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Network Configuration Files (.csv)", "csv");
        chooser.addChoosableFileFilter(extensionFilter);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (chooser.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
            System.out.println(chooser.getSelectedFile().getName());
        }
    }

    private void actionCreateNetwork(ActionEvent event) {
        mainFrame.openCreateNetworkConfigurationPanel();
    }
}
