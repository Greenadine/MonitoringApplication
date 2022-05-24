package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.model.NetworkComponentListTableModel;
import com.nerdygadgets.application.app.panel.ScreenHeaderPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.model.ApplicationWindow;
import com.nerdygadgets.application.model.component.Database;
import com.nerdygadgets.application.model.component.Firewall;
import com.nerdygadgets.application.model.component.NetworkComponent;
import com.nerdygadgets.application.model.component.Webserver;
import com.nerdygadgets.application.util.ApplicationActions;
import com.nerdygadgets.application.util.DatabaseUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;

public class ViewNetworkComponentsScreen extends ApplicationScreen implements ActionListener
{
    private JPanel databasePanel;
    private JPanel webserverPanel;
    private JPanel firewallPanel;

    private JPanel gridPanel;

    private JPanel propertiesPanel;


    // Buttons
    private JButton web1;
    private JButton web2;
    private JButton web3;
    private JButton web4;

    private JButton db1;
    private JButton db2;
    private JButton db3;
    private JButton db4;

    private JButton fw1;
    private JButton fw2;

    private JButton deleteProperties;
    private JButton editProperties;

    // Properties labels
    private JLabel firewallLabel;
    private JLabel nameOutputLabel;
    private JLabel idOutputLabel;
    private JLabel availabilityOutputLabel;
    private JLabel priceOutputLabel;
    private JLabel ipOutputLabel;
    private JLabel subnetmaskOutputLabel;

    private JButton addComponent;



    public ArrayList<JButton> buttons;
    private ArrayList<Database> databaseList;
    private ArrayList<Firewall> firewallList;
    private ArrayList<Webserver> webserverList;

    public ViewNetworkComponentsScreen(@NotNull final ApplicationWindow window) throws IOException
    {
        super(window);

        buttons = new ArrayList<>();
        databaseList = new ArrayList<>();
        firewallList = new ArrayList<>();
        webserverList = new ArrayList<>();
        // Configure screen
        this.setLayout(new BorderLayout(20, 15));

        // Populate screen
        this.add(new ScreenHeaderPanel(this, "Network Components", 1250, 50, ApplicationActions::openHome), BorderLayout.PAGE_START);

        firewallPanel = new JPanel();
        firewallPanel.setLayout(new GridLayout(0,1));
        firewallPanel.setBorder(BorderFactory.createLineBorder(Color.white));

        databasePanel = new JPanel();
        databasePanel.setLayout(new GridLayout(0,1));
        databasePanel.setBorder(BorderFactory.createLineBorder(Color.white));

        webserverPanel = new JPanel();
        webserverPanel.setLayout(new GridLayout(0,1));
        webserverPanel.setBorder(BorderFactory.createLineBorder(Color.white));

        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(1,3));
        webserverPanel.setBorder(BorderFactory.createLineBorder(Color.white));

        propertiesPanel = new JPanel();
        propertiesPanel.setLayout(new GridLayout(7,2));
        propertiesPanel.setBorder(BorderFactory.createLineBorder(Color.white));



        // Database
        JLabel databaseLabel = new JLabel("Database");
        databasePanel.add(databaseLabel, BorderLayout.LINE_START);
        db1 = new JButton("Database 1");
        db1.addActionListener(this);
        buttons.add(db1);


        db2 = new JButton("Database 2");
        db2.addActionListener(this);
        buttons.add(db2);


        db3 = new JButton("Database 3");
        db3.addActionListener(this);
        buttons.add(db3);

        db4 = new JButton("Database 4");
        db4.addActionListener(this);
        buttons.add(db4);


        // Webserver
        JLabel webserverLabel = new JLabel("Webserver");
        webserverPanel.add(webserverLabel, BorderLayout.CENTER);

        web1 = new JButton("Webserver 1");
        webserverPanel.add(web1);
        web1.addActionListener(this);
        web2 = new JButton("Webserver 2");
        webserverPanel.add(web2);
        web2.addActionListener(this);
        web3 = new JButton("Webserver 3");
        webserverPanel.add(web3);
        web3.addActionListener(this);
        web4 = new JButton("Webserver 4");
        webserverPanel.add(web4);
        web4.addActionListener(this);



        // Firewall
        firewallLabel = new JLabel("Firewall");
        firewallPanel.add(firewallLabel, BorderLayout.LINE_END);

        fw1 = new JButton("Firewall 1");
        firewallPanel.add(fw1);
        fw1.addActionListener(this);


        fw2 = new JButton("Firewall 2");
        firewallPanel.add(fw2);
        fw2.addActionListener(this);

        // Properties
        nameOutputLabel = new JLabel("(No component selected)");
        propertiesPanel.add(nameOutputLabel);
        JLabel test = new JLabel(" ");
        propertiesPanel.add(test);

        JLabel idLabel = new JLabel("ID:");
        propertiesPanel.add(idLabel);
        idOutputLabel = new JLabel("test");
        propertiesPanel.add(idOutputLabel);

        JLabel availability = new JLabel("Availability");
        propertiesPanel.add(availability);
        availabilityOutputLabel = new JLabel("test");
        propertiesPanel.add(availabilityOutputLabel);

        JLabel price = new JLabel("Price");
        propertiesPanel.add(price);
        priceOutputLabel = new JLabel("test");
        propertiesPanel.add(priceOutputLabel);

        JLabel ip = new JLabel("IP");
        propertiesPanel.add(ip);
        ipOutputLabel = new JLabel("test");
        propertiesPanel.add(ipOutputLabel);

        JLabel subnetmask = new JLabel("Subnet mask");
        propertiesPanel.add(subnetmask);
        subnetmaskOutputLabel = new JLabel("test");
        propertiesPanel.add(subnetmaskOutputLabel);

        addComponent = new JButton("add new component");
        add(addComponent, BorderLayout.PAGE_END);
        addComponent.addActionListener(this);





        addDatabaseButtons();
        componentInObject();


        gridPanel.add(webserverPanel);
        gridPanel.add(databasePanel);
        gridPanel.add(firewallPanel);

        add(gridPanel, BorderLayout.CENTER);
        add(propertiesPanel, BorderLayout.LINE_END);

        databaseConnection();
        setVisible(true);
    }

    @Override
    protected void onOpenImpl()
    {

    }

    @Override
    protected void onCloseImpl()
    {

    }

    private void addDatabaseButtons()
    {
        for (JButton j: buttons){
            databasePanel.add(j);
        }

    }

    public void databaseConnection (){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nerdygadgets", "root","");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM firewall");

            while (resultSet.next()){
                System.out.println(resultSet.getString("id"));
                System.out.println(resultSet.getString("name"));
                System.out.println(resultSet.getString("availability"));
                System.out.println(resultSet.getString("price"));
                System.out.println(resultSet.getString("ip"));
                System.out.println(resultSet.getString("subnetmask"));
            }
        }

        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void componentInObject() throws IOException
    {
        Firewall firewall1 = new Firewall(1,"Firewall 1", 99, 4000, "192.168.1.1", "255.255.255.0");
        Firewall firewall2 = new Firewall(1,"Firewall 2", 95, 4400, "192.168.1.5", "255.255.255.0");

        firewallList.add(firewall1);
        firewallList.add(firewall2);

        Database database1 = new Database(1,"Database 1", 99, 4000, "192.168.1.1", "255.255.255.0");
        Database database2 = new Database(1,"Database 2", 97, 4000, "192.168.1.1", "255.255.255.0");
        Database database3 = new Database(1,"Database 3", 69, 4000, "192.168.1.1", "255.255.255.0");
        Database database4 = new Database(1,"Database 4", 69, 4000, "192.168.1.1", "255.255.255.0");

        databaseList.add(database1);
        databaseList.add(database2);
        databaseList.add(database3);
        databaseList.add(database4);

        Webserver webserver1 = new Webserver(1,"Webserver 1", 99, 4000, "192.168.1.1", "255.255.255.0");
        Webserver webserver2 = new Webserver(1,"Webserver 2", 99, 4000, "192.168.1.1", "255.255.255.0");
        Webserver webserver3 = new Webserver(1,"Webserver 3", 99, 4000, "192.168.1.1", "255.255.255.0");
        Webserver webserver4 = new Webserver(1,"Webserver 4", 99, 4000, "192.168.1.1", "255.255.255.0");

        webserverList.add(webserver1);
        webserverList.add(webserver2);
        webserverList.add(webserver3);
        webserverList.add(webserver4);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == fw1){
            System.out.println("Firewall 1");
            nameOutputLabel.setText(firewallList.get(0).getName());
            idOutputLabel.setText(firewallList.get(0).getName());
            availabilityOutputLabel.setText(String.valueOf(firewallList.get(0).getAvailability()));
            priceOutputLabel.setText(String.valueOf(firewallList.get(0).getPrice()));
            ipOutputLabel.setText(firewallList.get(0).getIp());
            subnetmaskOutputLabel.setText(firewallList.get(0).getSubnet());
        }
        else if (e.getSource() == fw2){
            System.out.println("Firewall 2");
            nameOutputLabel.setText(firewallList.get(1).getName());
            idOutputLabel.setText(firewallList.get(1).getName());
            availabilityOutputLabel.setText(String.valueOf(firewallList.get(1).getAvailability()));
            priceOutputLabel.setText(String.valueOf(firewallList.get(1).getPrice()));
            ipOutputLabel.setText(firewallList.get(1).getIp());
            subnetmaskOutputLabel.setText(firewallList.get(1).getSubnet());
        }
        else if (e.getSource() == db1){
            System.out.println("Database 1");
            nameOutputLabel.setText(databaseList.get(0).getName());
            idOutputLabel.setText(databaseList.get(0).getName());
            availabilityOutputLabel.setText(String.valueOf(databaseList.get(0).getAvailability()));
            priceOutputLabel.setText(String.valueOf(databaseList.get(0).getPrice()));
            ipOutputLabel.setText(databaseList.get(0).getIp());
            subnetmaskOutputLabel.setText(databaseList.get(0).getSubnet());
        }
        else if (e.getSource() == db2){
            System.out.println("Database 2");
            nameOutputLabel.setText(databaseList.get(1).getName());
            idOutputLabel.setText(databaseList.get(1).getName());
            availabilityOutputLabel.setText(String.valueOf(databaseList.get(1).getAvailability()));
            priceOutputLabel.setText(String.valueOf(databaseList.get(1).getPrice()));
            ipOutputLabel.setText(databaseList.get(1).getIp());
            subnetmaskOutputLabel.setText(databaseList.get(1).getSubnet());
        }
        else if (e.getSource() == db3){
            System.out.println("Database 3");
            nameOutputLabel.setText(databaseList.get(2).getName());
            idOutputLabel.setText(databaseList.get(2).getName());
            availabilityOutputLabel.setText(String.valueOf(databaseList.get(2).getAvailability()));
            priceOutputLabel.setText(String.valueOf(databaseList.get(2).getPrice()));
            ipOutputLabel.setText(databaseList.get(2).getIp());
            subnetmaskOutputLabel.setText(databaseList.get(2).getSubnet());
        }
        else if (e.getSource() == db4){
            System.out.println("Database 4");
            nameOutputLabel.setText(databaseList.get(3).getName());
            idOutputLabel.setText(databaseList.get(3).getName());
            availabilityOutputLabel.setText(String.valueOf(databaseList.get(3).getAvailability()));
            priceOutputLabel.setText(String.valueOf(databaseList.get(3).getPrice()));
            ipOutputLabel.setText(databaseList.get(3).getIp());
            subnetmaskOutputLabel.setText(databaseList.get(3).getSubnet());
        }

        else if (e.getSource() == web1){
            System.out.println("Webserver 1");
            nameOutputLabel.setText(webserverList.get(0).getName());
            idOutputLabel.setText(webserverList.get(0).getName());
            availabilityOutputLabel.setText(String.valueOf(webserverList.get(0).getAvailability()));
            priceOutputLabel.setText(String.valueOf(webserverList.get(0).getPrice()));
            ipOutputLabel.setText(webserverList.get(0).getIp());
            subnetmaskOutputLabel.setText(webserverList.get(0).getSubnet());
        }
        else if (e.getSource() == web2){
            System.out.println("Webserver 2");
            nameOutputLabel.setText(webserverList.get(1).getName());
            idOutputLabel.setText(webserverList.get(1).getName());
            availabilityOutputLabel.setText(String.valueOf(webserverList.get(1).getAvailability()));
            priceOutputLabel.setText(String.valueOf(webserverList.get(1).getPrice()));
            ipOutputLabel.setText(webserverList.get(1).getIp());
            subnetmaskOutputLabel.setText(webserverList.get(1).getSubnet());
        }
        else if (e.getSource() == web3){
            System.out.println("Webserver 3");
            nameOutputLabel.setText(webserverList.get(2).getName());
            idOutputLabel.setText(webserverList.get(2).getName());
            availabilityOutputLabel.setText(String.valueOf(webserverList.get(2).getAvailability()));
            priceOutputLabel.setText(String.valueOf(webserverList.get(2).getPrice()));
            ipOutputLabel.setText(webserverList.get(2).getIp());
            subnetmaskOutputLabel.setText(webserverList.get(2).getSubnet());
        }
        else if (e.getSource() == web4){
            System.out.println("Webserver 4");
            nameOutputLabel.setText(webserverList.get(3).getName());
            idOutputLabel.setText(webserverList.get(3).getName());
            availabilityOutputLabel.setText(String.valueOf(webserverList.get(3).getAvailability()));
            priceOutputLabel.setText(String.valueOf(webserverList.get(3).getPrice()));
            ipOutputLabel.setText(webserverList.get(3).getIp());
            subnetmaskOutputLabel.setText(webserverList.get(3).getSubnet());
        }

        //Haalt de ingevoerde gegevens van het dialoog op en zet het in d1 (alleen database)
        else if (e.getSource() == addComponent){
            AddComponentDialog dialoog = new AddComponentDialog(true);
            try
            {
                Database d1 = dialoog.getWaarde();
                System.out.println(d1.getId());

                if (Objects.equals(d1.getType(), "firewall")){
                    JButton button = new JButton();

                }


            } catch (IOException ex)
            {
                throw new RuntimeException(ex);
            }
            System.out.println("test");
        }

    }
}
