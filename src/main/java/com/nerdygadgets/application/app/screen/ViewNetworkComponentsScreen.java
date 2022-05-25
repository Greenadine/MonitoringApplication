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


        getFirewallFromDatabase();
        getDatabaseFromDatabase();
        getWebserverFromDatabase();
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

    public void getFirewallFromDatabase(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nerdygadgets", "root","");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM firewall");

            long id = 0;
            String name = "";
            double availability = 0.0;
            double price = 0;
            String ip = "";
            String subnetmask = "";


            while (resultSet.next()){
                id = Long.parseLong(resultSet.getString("id"));
                name = resultSet.getString("name");
                availability = Double.parseDouble(resultSet.getString("availability"));
                price = Double.parseDouble(resultSet.getString("price"));
                ip = resultSet.getString("ip");
                subnetmask = resultSet.getString("subnetmask");

                Firewall tijdelijk = new Firewall(id, name, availability, price, ip, subnetmask);
                firewallList.add(tijdelijk);

            }




        }

        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void getDatabaseFromDatabase(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nerdygadgets", "root","");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM database1");

            long id = 0;
            String name = "";
            double availability = 0.0;
            double price = 0;
            String ip = "";
            String subnetmask = "";


            while (resultSet.next()){
                id = Long.parseLong(resultSet.getString("id"));
                name = resultSet.getString("name");
                availability = Double.parseDouble(resultSet.getString("availability"));
                price = Double.parseDouble(resultSet.getString("price"));
                ip = resultSet.getString("ip");
                subnetmask = resultSet.getString("subnet");

                Database tijdelijk = new Database(id, name, availability, price, ip, subnetmask);
               databaseList.add(tijdelijk);

            }




        }

        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getWebserverFromDatabase(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nerdygadgets", "root","");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM webserver");

            long id = 0;
            String name = "";
            double availability = 0.0;
            double price = 0;
            String ip = "";
            String subnetmask = "";


            while (resultSet.next()){
                id = Long.parseLong(resultSet.getString("id"));
                name = resultSet.getString("name");
                availability = Double.parseDouble(resultSet.getString("availability"));
                price = Double.parseDouble(resultSet.getString("price"));
                ip = resultSet.getString("ip");
                subnetmask = resultSet.getString("subnet");

                Webserver tijdelijk = new Webserver(id, name, availability, price, ip, subnetmask);
                webserverList.add(tijdelijk);

            }

        }

        catch (Exception e){
            e.printStackTrace();
        }
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

                System.out.println("d1 is toegevoegd");
                    JButton j1 = new JButton(d1.getName());
                    j1.addActionListener(this);
                    firewallPanel.add(j1);
                    firewallPanel.revalidate();
                    firewallPanel.repaint();





            } catch (IOException ex)
            {
                throw new RuntimeException(ex);
            }
            System.out.println("test");
        }
       else{
            System.out.println("Dit is Firewall 3");
        }


    }
}
