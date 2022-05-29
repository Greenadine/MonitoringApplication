package com.nerdygadgets.application.app.dialog;

import com.nerdygadgets.application.model.component.Database;
import com.nerdygadgets.application.model.component.Firewall;
import com.nerdygadgets.application.model.component.NetworkComponent;
import com.nerdygadgets.application.model.component.Webserver;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

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
        setLayout(new GridLayout(9,1));
        setSize(200,300);
        setTitle("Add Network Component");

        JLabel name = new JLabel("Name");
        add(name);
        nameTextField = new JTextField(20);
        add(nameTextField);

        JLabel type = new JLabel("Type");
        add(type);
        String[] componenten = { "webserver", "database", "firewall"};

        //Create the combo box, select item at index 4.
        //Indices start at 0, so 4 specifies the pig.
        componentList = new JComboBox(componenten);
        componentList.setSelectedIndex(2);
        componentList.addActionListener(this);
        add(componentList);

//        typeTextField = new JTextField(20);
//        add(typeTextField);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton){
            setVisible(false);
        }

    }

    @SuppressWarnings("unchecked,ConstantConditions")
    public <T extends NetworkComponent> T getComponent() throws IOException {
        switch ((String) componentList.getSelectedItem()) {
            case "webserver":
                return (T) new Webserver(nameTextField.getText(), Integer.parseInt(availTextField.getText()),Integer.parseInt(priceTextField.getText()), ipTextField.getText(), subnetTextField.getText());
            case "database":
                return (T) new Database(nameTextField.getText(), Integer.parseInt(availTextField.getText()),Integer.parseInt(priceTextField.getText()), ipTextField.getText(), subnetTextField.getText());
            case "firewall":
                return (T) new Firewall(nameTextField.getText(), Integer.parseInt(availTextField.getText()),Integer.parseInt(priceTextField.getText()), ipTextField.getText(), subnetTextField.getText());
            default:
                throw new IllegalArgumentException();
        }
    }

}
