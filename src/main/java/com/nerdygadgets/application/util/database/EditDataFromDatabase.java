package com.nerdygadgets.application.util.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;

public class EditDataFromDatabase
{
    public void FromDatabase(String id, String type, String name, double availability, double price, String ip, String subnetmask)
    {
        if (Objects.equals(type, "database")){
            type = "database1";
        }

        try {
            Connection connection = ConnectionToDatabase.DatabaseConnection();

            String sql = "UPDATE ? SET name = ?, availability = ?, price = ?, ip = ?, subnetmask = ? WHERE id = ?";

            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString (1, type);
            preparedStmt.setString (2, name);
            preparedStmt.setDouble (3, availability);
            preparedStmt.setDouble (4, price);
            preparedStmt.setString (5, ip);
            preparedStmt.setString (6, subnetmask);
            preparedStmt.setString (7, id);

            preparedStmt.execute();
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
