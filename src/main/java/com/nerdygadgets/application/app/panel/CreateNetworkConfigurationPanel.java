package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.frame.MainFrame;
import com.nerdygadgets.application.util.Fonts;

import javax.swing.*;

public class CreateNetworkConfigurationPanel extends JPanel {

    private final MainFrame mainFrame;

    public CreateNetworkConfigurationPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        JLabel label = new JLabel("W.I.P.");
        label.setFont(Fonts.TITLE);
        this.add(label);
    }
}
