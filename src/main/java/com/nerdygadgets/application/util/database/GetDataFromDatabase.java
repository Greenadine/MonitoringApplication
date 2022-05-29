package com.nerdygadgets.application.util.database;

import com.nerdygadgets.application.model.component.Database;
import com.nerdygadgets.application.model.component.Firewall;
import com.nerdygadgets.application.model.component.Webserver;

import java.sql.*;
import java.util.ArrayList;

public class GetDataFromDatabase
{

    private long id;
    private String name;
    private double availability;
    private double price;
    private String ip;
    private String subnetmask;

    public Connection DatabaseConnection()
    {
        try
        {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/nerdygadgets", "root", "");
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Firewall> getFirewallFromDatabase()
    {
        ArrayList<Firewall> firewallArrayList = new ArrayList<>();
        try
        {
            Connection connection = DatabaseConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM firewall");


            while (resultSet.next())
            {
                id = Long.parseLong(resultSet.getString("id"));
                name = resultSet.getString("name");
                availability = Double.parseDouble(resultSet.getString("availability"));
                price = Double.parseDouble(resultSet.getString("price"));
                ip = resultSet.getString("ip");
                subnetmask = resultSet.getString("subnetmask");

                Firewall firewall = new Firewall(id, name, availability, price, ip, subnetmask);
                firewallArrayList.add(firewall);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return firewallArrayList;
    }

    public ArrayList<Webserver> getWebserverFromDatabase()
    {
        ArrayList<Webserver> webserverArrayList = new ArrayList<>();
        try
        {
            Connection connection = DatabaseConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM webserver");


            while (resultSet.next())
            {
                id = Long.parseLong(resultSet.getString("id"));
                name = resultSet.getString("name");
                availability = Double.parseDouble(resultSet.getString("availability"));
                price = Double.parseDouble(resultSet.getString("price"));
                ip = resultSet.getString("ip");
                subnetmask = resultSet.getString("subnet");

                Webserver webserver = new Webserver(id, name, availability, price, ip, subnetmask);
                webserverArrayList.add(webserver);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return webserverArrayList;
    }

    public ArrayList<Database> getDatabaseFromDatabase()
    {
        ArrayList<Database> databaseArrayList = new ArrayList<>();
        try
        {
            Connection connection = DatabaseConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM database1");

            while (resultSet.next())
            {
                id = Long.parseLong(resultSet.getString("id"));
                name = resultSet.getString("name");
                availability = Double.parseDouble(resultSet.getString("availability"));
                price = Double.parseDouble(resultSet.getString("price"));
                ip = resultSet.getString("ip");
                subnetmask = resultSet.getString("subnet");

                Database database = new Database(id, name, availability, price, ip, subnetmask);
                databaseArrayList.add(database);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return databaseArrayList;
    }
}
