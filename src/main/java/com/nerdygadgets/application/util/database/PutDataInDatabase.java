package com.nerdygadgets.application.util.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PutDataInDatabase
{

    public void putDatabaseObjectInDatabase(String name, double availability, double price, String ip, String subnetmask){
        try{
            Connection connection = ConnectionToDatabase.DatabaseConnection();

            String sql = "INSERT INTO database1 (name, availability, price, ip, subnet)" + "values (?,?,?,?,?)";

            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString (1, name);
            preparedStmt.setDouble   (2, availability);
            preparedStmt.setDouble(3, price);
            preparedStmt.setString    (4, ip);
            preparedStmt.setString    (5, subnetmask);

            preparedStmt.execute();
            connection.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void putWebserverObjectInDatabase(String name, double availability, double price, String ip, String subnetmask){
        try {
            Connection connection = ConnectionToDatabase.DatabaseConnection();

            String sql = "INSERT INTO webserver (name, availability, price, ip, subnet)" + "values (?,?,?,?,?)";

            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString (1, name);
            preparedStmt.setDouble   (2, availability);
            preparedStmt.setDouble(3, price);
            preparedStmt.setString    (4, ip);
            preparedStmt.setString    (5, subnetmask);

            preparedStmt.execute();
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void putFirewallObjectInDatabase(String name, double availability, double price, String ip, String subnetmask)
    {
        try
        {
            Connection connection = ConnectionToDatabase.DatabaseConnection();
            String sql = "INSERT INTO firewall (name, availability, price, ip, subnetmask)" + "values (?,?,?,?,?)";

            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString(1, name);
            preparedStmt.setDouble(2, availability);
            preparedStmt.setDouble(3, price);
            preparedStmt.setString(4, ip);
            preparedStmt.setString(5, subnetmask);

            preparedStmt.execute();
            connection.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}


