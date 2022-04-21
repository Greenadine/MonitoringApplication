package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.frame.MainFrame;
import com.nerdygadgets.application.util.Fonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CreateNetworkConfigurationPanel extends JPanel {

    private final MainFrame mainFrame;

    public CreateNetworkConfigurationPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        JLabel label = new JLabel("W.I.P.");
        label.setBorder(new EmptyBorder(50, 50, 50, 50));
        label.setFont(Fonts.TITLE);

        JButton homeButton = new JButton("Home");

        this.add(label);
    }


}
