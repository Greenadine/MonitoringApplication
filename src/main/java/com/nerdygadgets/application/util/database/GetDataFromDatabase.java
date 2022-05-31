package com.nerdygadgets.application.util.database;

import com.nerdygadgets.application.model.ComponentType;
import com.nerdygadgets.application.model.NetworkComponent;

import java.sql.*;
import java.util.ArrayList;

public class GetDataFromDatabase {

    private long id;
    private String name;
    private double availability;
    private double price;
    private String ip;
    private String subnetmask;


    public ArrayList<NetworkComponent> getFirewallFromDatabase() {
        ArrayList<NetworkComponent> firewallArrayList = new ArrayList<>();
        try {
            Connection connection = ConnectionToDatabase.DatabaseConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM firewall");


            while (resultSet.next()) {
                id = Long.parseLong(resultSet.getString("id"));
                name = resultSet.getString("name");
                availability = Double.parseDouble(resultSet.getString("availability"));
                price = Double.parseDouble(resultSet.getString("price"));
                ip = resultSet.getString("ip");
                subnetmask = resultSet.getString("subnetmask");

                NetworkComponent firewall = new NetworkComponent(id, ComponentType.FIREWALL, name, availability, price, ip, subnetmask);
                firewallArrayList.add(firewall);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return firewallArrayList;
    }

    public ArrayList<NetworkComponent> getWebserverFromDatabase() {
        ArrayList<NetworkComponent> webserverArrayList = new ArrayList<>();
        try {
            Connection connection = ConnectionToDatabase.DatabaseConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM webserver");


            while (resultSet.next()) {
                id = Long.parseLong(resultSet.getString("id"));
                name = resultSet.getString("name");
                availability = Double.parseDouble(resultSet.getString("availability"));
                price = Double.parseDouble(resultSet.getString("price"));
                ip = resultSet.getString("ip");
                subnetmask = resultSet.getString("subnetmask");

                NetworkComponent webserver = new NetworkComponent(id, ComponentType.WEBSERVER, name, availability, price, ip, subnetmask);
                webserverArrayList.add(webserver);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return webserverArrayList;
    }

    public ArrayList<NetworkComponent> getDatabaseFromDatabase() {
        ArrayList<NetworkComponent> databaseArrayList = new ArrayList<>();
        try {
            Connection connection = ConnectionToDatabase.DatabaseConnection();
            ;
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM database1");

            while (resultSet.next()) {
                id = Long.parseLong(resultSet.getString("id"));
                name = resultSet.getString("name");
                availability = Double.parseDouble(resultSet.getString("availability"));
                price = Double.parseDouble(resultSet.getString("price"));
                ip = resultSet.getString("ip");
                subnetmask = resultSet.getString("subnetmask");

                NetworkComponent database = new NetworkComponent(id, ComponentType.DATABASE, name, availability, price, ip, subnetmask);
                databaseArrayList.add(database);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseArrayList;
    }

    public NetworkComponent getDatabase(long id) {
        try {
            Connection connection = ConnectionToDatabase.DatabaseConnection();

            String sql = "SELECT * FROM database1 WHERE id = ?";

            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setLong(1, id);

            preparedStmt.execute();
            ResultSet resultSet = preparedStmt.getResultSet();
            if (resultSet == null) return null;
            id = Long.parseLong(resultSet.getString("id"));
            name = resultSet.getString("name");
            availability = Double.parseDouble(resultSet.getString("availability"));
            price = Double.parseDouble(resultSet.getString("price"));
            ip = resultSet.getString("ip");
            subnetmask = resultSet.getString("subnetmask");
            connection.close();
            return new NetworkComponent(id, ComponentType.DATABASE, name, availability, price, ip, subnetmask);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public NetworkComponent getWebserver(long id) {
        try {
            Connection connection = ConnectionToDatabase.DatabaseConnection();

            String sql = "SELECT * FROM webserver WHERE id = ?";

            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setLong(1, id);

            preparedStmt.execute();
            ResultSet resultSet = preparedStmt.getResultSet();
            if (resultSet == null) return null;
            id = Long.parseLong(resultSet.getString("id"));
            name = resultSet.getString("name");
            availability = Double.parseDouble(resultSet.getString("availability"));
            price = Double.parseDouble(resultSet.getString("price"));
            ip = resultSet.getString("ip");
            subnetmask = resultSet.getString("subnetmask");
            connection.close();
            return new NetworkComponent(id, ComponentType.WEBSERVER, name, availability, price, ip, subnetmask);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
