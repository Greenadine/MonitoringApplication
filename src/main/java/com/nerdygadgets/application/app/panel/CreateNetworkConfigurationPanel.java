package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.frame.MainFrame;
import com.nerdygadgets.application.util.Fonts;
import com.nerdygadgets.application.util.Utils;

import javax.swing.*;
import java.awt.*;

public class CreateNetworkConfigurationPanel extends JPanel {

    private final MainFrame mainFrame;

    private final int panelWidth = MainFrame.BODY_WIDTH;
    private final int panelHeight = MainFrame.FRAME_HEIGHT;

    public CreateNetworkConfigurationPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        // Configure panel
        Utils.setSizeMinMax(this, new Dimension(panelWidth, panelHeight));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // TODO align everything to top

        // Create and add title label
        JLabel titleLabel = new JLabel("Create Network Configuration", JLabel.CENTER);
        titleLabel.setAlignmentY(CENTER_ALIGNMENT);
        titleLabel.setFont(Fonts.TITLE);
        this.add(titleLabel);

        // Spacing
        JLabel spacing = new JLabel();
        Utils.setSizeAll(spacing, new Dimension((int) (panelWidth * 0.92), 25));
        this.add(spacing);

        // Create input fields panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));
        Utils.setSizeMinMax(inputPanel, new Dimension(panelWidth, panelHeight));

        // Create and add name text field
        JLabel nameLabel = newLabel("Name");
        inputPanel.add(nameLabel);

        JTextField nameInputField = newInputField();
        inputPanel.add(nameInputField);

        // Create and add IP text field
        JLabel availabilityLabel = newLabel("Availability");
        inputPanel.add(availabilityLabel);

        JTextField availabilityInputField = newInputField();
        inputPanel.add(availabilityInputField);

        // Create and add price text field
        JLabel priceLabel = newLabel("Price");
        inputPanel.add(priceLabel);

        JTextField priceInputField = newInputField();
        inputPanel.add(priceInputField);

        // Create and add IP text field
        JLabel ipField = newLabel("IP");
        inputPanel.add(ipField);

        JTextField ipInputField = newInputField();
        inputPanel.add(ipInputField);

        // Create and add IP text field
        JLabel subnetLabel = newLabel("Subnet");
        inputPanel.add(subnetLabel);

        JTextField subnetInputField = newInputField();
        inputPanel.add(subnetInputField);



        // Add create button

        this.add(inputPanel);
    }

    private JLabel newLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Fonts.BIG);
        Utils.setSizeAll(label, new Dimension(70, 24));
        return label;
    }

    private JTextField newInputField() {
        JTextField inputField = new JTextField();
        inputField.setFont(Fonts.BIG);
        Utils.setSizeAll(inputField, new Dimension(20, 30));
        return inputField;
    }
}
