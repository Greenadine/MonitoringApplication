package com.nerdygadgets.application.util.database;

import com.nerdygadgets.application.model.component.NetworkComponent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PutDataInDatabase {

    public static <T extends NetworkComponent> void updateData(T component) {
        try {
            Connection connection = ConnectionToDatabase.DatabaseConnection();

            String type = component.getType().name().toLowerCase();
            if (type.equals("database")) {
                type = "database1";
            }

            String sql = String.format("INSERT INTO %s (name, availability, price, ip, subnet)", type) + "values (?,?,?,?,?)";

            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString(1, component.getName());
            preparedStmt.setDouble(2, component.getAvailability());
            preparedStmt.setDouble(3, component.getPrice());
            preparedStmt.setString(4, component.getIp());
            preparedStmt.setString(5, component.getSubnetMask());

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
