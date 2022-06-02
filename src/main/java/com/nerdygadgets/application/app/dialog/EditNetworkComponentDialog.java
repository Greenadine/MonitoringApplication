package com.nerdygadgets.application.app.dialog;

import com.nerdygadgets.application.model.ComponentType;
import com.nerdygadgets.application.model.NetworkComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class EditNetworkComponentDialog extends JDialog implements ActionListener {

    //Buttons
    private JButton cancel;
    private JButton editButton;

    //Textfields
    private JTextField nameTextField;
    private JTextField ipTextField;
    private JTextField subnetTextField;
    private JTextField availTextField;
    private JTextField priceTextField;

    public JComboBox<NetworkComponent> componentList;

    private final NetworkComponent component;

    @SuppressWarnings("rawtypes,unchecked")
    public EditNetworkComponentDialog(boolean modal, final NetworkComponent component) {
        this.component = component;

        setModal(modal);
        setLayout(new GridLayout(9, 1));
        setSize(300, 350);
        setTitle("Add Network Component");

        JLabel name = new JLabel("Name");
        add(name);
        nameTextField = new JTextField(20);
        nameTextField.setText(component.getName());
        add(nameTextField);

        JLabel type = new JLabel("Type");
        add(type);

        componentList = new JComboBox(new String[] { "Webserver", "Database", "Firewall" });
        componentList.setSelectedIndex(2);
        componentList.addActionListener(this);
        add(componentList);

        JLabel ipAdres = new JLabel("IP address");
        add(ipAdres);
        ipTextField = new JTextField(20);
        ipTextField.setText(component.getIp());
        add(ipTextField);

        JLabel subnetmask = new JLabel("Subnet mask");
        add(subnetmask);
        subnetTextField = new JTextField(20);
        subnetTextField.setText(component.getSubnetMask());
        add(subnetTextField);

        JLabel availability = new JLabel("Availability");
        add(availability);
        availTextField = new JTextField(20);
        availTextField.setText(String.valueOf(component.getAvailability()));
        add(availTextField);

        JLabel price = new JLabel("Price");
        add(price);
        priceTextField = new JTextField(20);
        priceTextField.setText(String.valueOf(component.getPrice()));
        add(priceTextField);

        cancel = new JButton("Cancel");
        add(cancel);
        cancel.addActionListener(this);

        editButton = new JButton("Edit");
        add(editButton);
        editButton.addActionListener(this);

        setVisible(true);
    }

    public boolean checks() {
        // Create default border
        nameTextField.setBorder(BorderFactory.createEmptyBorder());
        ipTextField.setBorder(BorderFactory.createEmptyBorder());
        availTextField.setBorder(BorderFactory.createEmptyBorder());
        priceTextField.setBorder(BorderFactory.createEmptyBorder());
        subnetTextField.setBorder(BorderFactory.createEmptyBorder());

        boolean check = true;

        if (nameTextField.getText().isEmpty()) {
            nameTextField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red));
            check = false;

        }
        if (ipTextField.getText().isEmpty()) {
            ipTextField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red));
            check = false;
        }

        if (priceTextField.getText().isEmpty()) {
            priceTextField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red));
            check = false;
        } else {
            try {
                Double.parseDouble(priceTextField.getText());
            } catch (NumberFormatException e) {
                priceTextField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red));
                check = false;
            }
        }
        if (subnetTextField.getText().isEmpty()) {
            subnetTextField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red));
            check = false;
        }
        if (availTextField.getText().isEmpty()) {
            availTextField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red));
            check = false;
        } else {
            try {
                Double.parseDouble(availTextField.getText());
            } catch (NumberFormatException e) {
                availTextField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.red));
                check = false;
            }
        }
        return check;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editButton && checks()) {
            component.setName(nameTextField.getText());
            component.setAvailability(Double.parseDouble(availTextField.getText()));
            component.setPrice(Double.parseDouble(priceTextField.getText()));
            component.setIp(ipTextField.getText());
            component.setSubnetMask(subnetTextField.getText());

            setVisible(false);
        } else if (e.getSource() == cancel) {
            setVisible(false);
        }
    }
}
