package com.nerdygadgets.application.app.dialog;

import com.nerdygadgets.application.app.component.WrappedJTextField;
import com.nerdygadgets.application.app.model.ApplicationDialog;
import com.nerdygadgets.application.app.model.ApplicationWindow;
import com.nerdygadgets.application.app.model.Identifier;
import com.nerdygadgets.application.model.ComponentType;
import com.nerdygadgets.application.model.NetworkComponent;
import com.nerdygadgets.application.util.Fonts;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

@Identifier("component")
public class NewAddComponentDialog extends ApplicationDialog {

    private final WrappedJTextField nameField;
    private final JComboBox<ComponentType> componentTypeBox;
    private final WrappedJTextField availabilityField;
    private final WrappedJTextField priceField;
    private final WrappedJTextField ipField;
    private final WrappedJTextField subnetMaskField;

    public NewAddComponentDialog(@NotNull final ApplicationWindow window, final boolean modal) {
        super(window, modal);

        // Configure dialog
        this.setTitle("Add New Network Component");
        this.setLayout(new BorderLayout());

        // Create and add wrapper panel for margin
        final JPanel wrapperPanel = new JPanel();
        wrapperPanel.setBorder(new EmptyBorder(20, 10, 0, 10));
        this.add(wrapperPanel, BorderLayout.CENTER);

        // Create form grid panel
        final JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2, 50, 10));
        wrapperPanel.add(formPanel);

        // Add label and field for name
        final JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(Fonts.DIALOG_TABLE_PARAGRAPH);
        formPanel.add(nameLabel);

        nameField = new WrappedJTextField();
        nameField.getTextField().setFont(Fonts.DIALOG_TABLE_PARAGRAPH);
        formPanel.add(nameField);

        // Add label and selection box for component type
        final JLabel componentTypeLabel = new JLabel("Component Type");
        componentTypeLabel.setFont(Fonts.DIALOG_TABLE_PARAGRAPH);
        formPanel.add(componentTypeLabel);

        componentTypeBox = new JComboBox<>(ComponentType.values());
        componentTypeBox.setFont(Fonts.DIALOG_TABLE_PARAGRAPH);
        formPanel.add(componentTypeBox);

        // Add label and field for availability
        final JLabel availabilityLabel = new JLabel("Availability");
        availabilityLabel.setFont(Fonts.DIALOG_TABLE_PARAGRAPH);
        formPanel.add(availabilityLabel);

        availabilityField = new WrappedJTextField();
        availabilityField.getTextField().setFont(Fonts.DIALOG_TABLE_PARAGRAPH);
        formPanel.add(availabilityField);

        // Add label and field for price
        final JLabel priceLabel = new JLabel("Price");
        priceLabel.setFont(Fonts.DIALOG_TABLE_PARAGRAPH);
        formPanel.add(priceLabel);

        priceField = new WrappedJTextField();
        priceField.getTextField().setFont(Fonts.DIALOG_TABLE_PARAGRAPH);
        formPanel.add(priceField);

        // Add label and field for IP-address
        final JLabel ipLabel = new JLabel("IP");
        ipLabel.setFont(Fonts.DIALOG_TABLE_PARAGRAPH);
        formPanel.add(ipLabel);

        ipField = new WrappedJTextField();
        ipField.getTextField().setFont(Fonts.DIALOG_TABLE_PARAGRAPH);
        formPanel.add(ipField);

        // Add label and field for subnet mask
        final JLabel subnetMaskLabel = new JLabel("Subnet Mask");
        subnetMaskLabel.setFont(Fonts.DIALOG_TABLE_PARAGRAPH);
        formPanel.add(subnetMaskLabel);

        subnetMaskField = new WrappedJTextField();
        subnetMaskField.getTextField().setFont(Fonts.DIALOG_TABLE_PARAGRAPH);
        formPanel.add(subnetMaskField);

        // Create buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        this.add(buttonsPanel, BorderLayout.PAGE_END);

        // Add cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this::actionCancel);
        cancelButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        buttonsPanel.add(cancelButton);

        // Add confirm button
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(this::onConfirm);
        confirmButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        buttonsPanel.add(confirmButton);

        this.center();
    }

    private boolean checkValues() {
        // Reset borders
        nameField.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        availabilityField.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        priceField.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        ipField.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        subnetMaskField.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        boolean valid = true;

        // Check whether value of name field of name is not empty & is alphanumerical
        if (nameField.getTextField().getText().isEmpty() || !nameField.getTextField().getText().matches("^[a-zA-Z\\d\\s++]++$")) {
            nameField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.RED));
            valid = false;
        }

        // Check whether value of availability field is not empty & and is numeric (with decimals)
        if (availabilityField.getTextField().getText().isEmpty() || !availabilityField.getTextField().getText().matches("^(\\d+\\.?)+$")) {
            availabilityField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.RED));
            valid = false;
        }

        // Check whether value of price field is not empty & is numeric (with decimals)
        if (priceField.getTextField().getText().isEmpty() || !priceField.getTextField().getText().matches("^(\\d+\\.?)+$")) {
            priceField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.RED));
            valid = false;
        }

        // Check whether value of IP address field of name is not empty & is alphanumerical
        if (ipField.getTextField().getText().isEmpty() || !ipField.getTextField().getText().matches("^(\\d+\\.?)+$")) {
            ipField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.RED));
            valid = false;
        }

        // Check whether value of subnet mask field of name is not empty & is alphanumerical
        if (subnetMaskField.getTextField().getText().isEmpty() || !subnetMaskField.getTextField().getText().matches("^(\\d+\\.?)+$")) {
            subnetMaskField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.RED));
            valid = false;
        }

        return valid;
    }

    /* Button actions */

    private void actionCancel(ActionEvent event) {
        hideDialog();
    }

    private void onConfirm(ActionEvent event) {
        if (checkValues()) {
            hideDialog();
        }
    }

    @SuppressWarnings("ConstantConditions")
    public NetworkComponent getComponent() {
        if (checkValues()) {
            final String name = nameField.getTextField().getText();
            ComponentType type = (ComponentType) componentTypeBox.getSelectedItem();
            final double availability = Double.parseDouble(availabilityField.getTextField().getText());
            final double price = Double.parseDouble(priceField.getTextField().getText());
            final String ip = ipField.getTextField().getText();
            final String subnetMask = subnetMaskField.getTextField().getText();

            return new NetworkComponent(type, name, availability, price, ip, subnetMask);
        }
        return null;
    }

    @Override
    protected void onShow() {
        // Reset borders
        nameField.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        availabilityField.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        priceField.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        ipField.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        subnetMaskField.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        this.pack();
    }

    @Override
    protected void onHide() {
        nameField.getTextField().setText("");
        componentTypeBox.setSelectedIndex(0);
        availabilityField.getTextField().setText("");
        priceField.getTextField().setText("");
        ipField.getTextField().setText("");
        subnetMaskField.getTextField().setText("");
    }
}
