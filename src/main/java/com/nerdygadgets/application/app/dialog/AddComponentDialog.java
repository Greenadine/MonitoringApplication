package com.nerdygadgets.application.app.dialog;

import com.nerdygadgets.application.model.ComponentType;
import com.nerdygadgets.application.model.NetworkComponent;
import org.checkerframework.checker.units.qual.C;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AddComponentDialog extends JDialog implements ActionListener {

    //Buttons
    private JButton cancel;
    private JButton addButton;

    //Textfields
    private JTextField nameTextField;
    private JTextField typeTextField;
    private JTextField ipTextField;
    private JTextField subnetTextField;
    private JTextField availTextField;
    private JTextField priceTextField;

    public JComboBox<NetworkComponent> componentList;

    @SuppressWarnings("unchecked,rawtypes")
    public AddComponentDialog(boolean modal) {
        setModal(modal);
        setLayout(new GridLayout(9, 1));
        setSize(300, 300);
        setTitle("Add Network Component");

        JLabel name = new JLabel("Name");
        add(name);
        nameTextField = new JTextField(20);
        add(nameTextField);

        JLabel type = new JLabel("Type");
        add(type);

        componentList = new JComboBox(new String[]{"Webserver", "Database", "Firewall"});
        componentList.setSelectedIndex(2);
        componentList.addActionListener(this);
        add(componentList);

        JLabel ipAdres = new JLabel("IP address");
        add(ipAdres);
        ipTextField = new JTextField(20);
        add(ipTextField);

        JLabel subnetmask = new JLabel("Subnet mask");
        add(subnetmask);
        subnetTextField = new JTextField(20);
        add(subnetTextField);

        JLabel availability = new JLabel("Availability");
        add(availability);
        availTextField = new JTextField(20);
        add(availTextField);

        JLabel price = new JLabel("Price");
        add(price);
        priceTextField = new JTextField(20);
        add(priceTextField);

        cancel = new JButton("Cancel");
        add(cancel);
        cancel.addActionListener(this);

        addButton = new JButton("Add");
        add(addButton);
        addButton.addActionListener(this);

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
        if (check) {
            return true;
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton && checks()) {
            setVisible(false);
        }
    }

    @SuppressWarnings("ConstantConditions")
    public NetworkComponent getComponent() throws IOException {
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
        }
        return null;
    }
}
