package com.nerdygadgets.application.util.database;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDatabase
{
    public static java.sql.Connection DatabaseConnection()
    {
        try
        {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/nerdygadgets", "root", "");
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
