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

    @SuppressWarnings("rawtypes,unchecked")
    public EditNetworkComponentDialog(boolean modal, String name1, double price1, double availability1, String ip1, String subnetmask1) {
        setModal(modal);
        setLayout(new GridLayout(9, 1));
        setSize(300, 350);
        setTitle("Add Network Component");

        JLabel name = new JLabel("Name");
        add(name);
        nameTextField = new JTextField(20);
        nameTextField.setText(name1);
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
        ipTextField.setText(ip1);
        add(ipTextField);

        JLabel subnetmask = new JLabel("Subnet mask");
        add(subnetmask);
        subnetTextField = new JTextField(20);
        subnetTextField.setText(subnetmask1);
        add(subnetTextField);

        JLabel availability = new JLabel("Availability");
        add(availability);
        availTextField = new JTextField(20);
        availTextField.setText(String.valueOf(availability1));
        add(availTextField);

        JLabel price = new JLabel("Price");
        add(price);
        priceTextField = new JTextField(20);
        priceTextField.setText(String.valueOf(price1));
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

    @SuppressWarnings("ConstantConditions")
    public NetworkComponent getComponent() {
        if (checks()) {
            final String name = nameTextField.getText();
            ComponentType type = switch ((String) componentList.getSelectedItem()) {
                case "database" -> ComponentType.DATABASE;
                case "webserver" -> ComponentType.WEBSERVER;
                case "firewall" -> ComponentType.FIREWALL;
                default -> null;
            };
            final double availability = Double.parseDouble(availTextField.getText());
            final double price = Double.parseDouble(priceTextField.getText());
            final String ip = ipTextField.getText();
            final String subnetMask = subnetTextField.getText();

            return new NetworkComponent(type, name, availability, price, ip, subnetMask);
        } else {
            return null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editButton && checks()) {
            setVisible(false);
        } else if (e.getSource() == cancel) {
            setVisible(false);
        }
    }
}
