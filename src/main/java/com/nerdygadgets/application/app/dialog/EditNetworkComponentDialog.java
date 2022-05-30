package com.nerdygadgets.application.app.dialog;

import com.nerdygadgets.application.model.component.Database;
import com.nerdygadgets.application.model.component.Firewall;
import com.nerdygadgets.application.model.component.NetworkComponent;
import com.nerdygadgets.application.model.component.Webserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class EditNetworkComponentDialog extends JDialog implements ActionListener {

    //Buttons
    private JButton cancel;
    private JButton addButton;

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
        setLayout(new GridLayout(9,1));
        setSize(200,300);
        setTitle("Add Network Component");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel name = new JLabel("Name");
        add(name);
        nameTextField = new JTextField(20);
        nameTextField.setText(name1);
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

        addButton = new JButton("Add");
        add(addButton);
        addButton.addActionListener(this);

        setVisible(true);
    }

    @SuppressWarnings("unchecked,ConstantConditions")
    public <T extends NetworkComponent> T getComponent() throws IOException
    {
        switch ((String) componentList.getSelectedItem()) {
            case "webserver":
                return (T) new Webserver(nameTextField.getText(), Double.parseDouble(availTextField.getText()),Double.parseDouble(priceTextField.getText()), ipTextField.getText(), subnetTextField.getText());
            case "database":
                return (T) new Database(nameTextField.getText(), Double.parseDouble(availTextField.getText()),Double.parseDouble(priceTextField.getText()), ipTextField.getText(), subnetTextField.getText());
            case "firewall":
                return (T) new Firewall(nameTextField.getText(), Double.parseDouble(availTextField.getText()),Double.parseDouble(priceTextField.getText()), ipTextField.getText(), subnetTextField.getText());
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton){
            setVisible(false);
        }

    }
}
