package com.nerdygadgets.application.util.database;

import com.nerdygadgets.application.model.NetworkComponent;

public class PutDataInDatabase
{

    public static <T extends NetworkComponent> void updateData(T component)
    {
//        try
//        {
//            Connection connection = ConnectionToDatabase.DatabaseConnection();
//
//            String type = component.getType().name().toLowerCase();
//            if (type.equals("database"))
//            {
//                type = "database1";
//            }
//
//            String sql = String.format("INSERT INTO %s (name, availability, price, ip, subnetmask)", type) + "values (?,?,?,?,?)";
//
//            PreparedStatement preparedStmt = connection.prepareStatement(sql);
//            preparedStmt.setString(1, component.getName());
//            preparedStmt.setDouble(2, component.getAvailability());
//            preparedStmt.setDouble(3, component.getPrice());
//            preparedStmt.setString(4, component.getIp());
//            preparedStmt.setString(5, component.getSubnetMask());
//
//            preparedStmt.execute();
//            connection.close();
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
    }

}
