package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.frame.MainFrame;
import com.nerdygadgets.application.app.model.NetworkComponentTableModel;
import com.nerdygadgets.application.util.Utils;

import javax.swing.*;
import java.awt.*;

public class ConfigurationCalculatorToolPanel extends JPanel {

    private final int panelWidth = MainFrame.BODY_WIDTH;
    private final int panelHeight = MainFrame.FRAME_HEIGHT;

    public ConfigurationCalculatorToolPanel() {
        // Create and configure panel
        Utils.setSizeMinMax(this, new Dimension(panelWidth, panelHeight));
        this.setLayout(null);

        final int spacing = 15;

        // Create table model
        NetworkComponentTableModel tableModel = new NetworkComponentTableModel(null);

        /* Databases table */
        JLabel databasesLabel = newLabel("Databases");

        JTable databasesTable = new JTable(tableModel);
    }

    private JLabel newLabel(String text) {
        JLabel label = new JLabel(text);
        label.setBounds(5, 0, panelWidth/2 - 20, 20);
        return label;
    }
}
