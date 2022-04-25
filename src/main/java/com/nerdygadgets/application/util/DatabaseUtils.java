package com.nerdygadgets.application.util;

import com.nerdygadgets.application.exception.DatabaseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseUtils {

    private static final String MASTER_1_HOST, MASTER_1_USER, MASTER_1_PASSWORD;
    private static final String MASTER_2_HOST, MASTER_2_USER, MASTER_2_PASSWORD;

    static {
        Properties databaseProperties = new Properties();

        try {
            databaseProperties.load(new FileInputStream("database.properties"));
        } catch (IOException e) {
            System.out.println("ERROR: Failed to load 'database.properties'.");
            e.printStackTrace();
        }

        MASTER_1_HOST = databaseProperties.getProperty("master1-host", null);
        MASTER_1_USER = databaseProperties.getProperty("master1-user", null);
        MASTER_1_PASSWORD = databaseProperties.getProperty("master1-password", null);

        MASTER_2_HOST = databaseProperties.getProperty("master2-host", null);
        MASTER_2_USER = databaseProperties.getProperty("master2-user", null);
        MASTER_2_PASSWORD = databaseProperties.getProperty("master2-password", null);
    }

    public static Connection getConnection() throws DatabaseException {
        try {
            return DriverManager.getConnection(MASTER_1_HOST, MASTER_1_USER, MASTER_1_PASSWORD);
        } catch (Exception ex) {
            Logger.warning("Failed to establish connection with 'master1', attempting to connect with 'master2'...");

            if (MASTER_1_HOST == null || MASTER_1_USER == null || MASTER_1_PASSWORD == null) {
                throw new DatabaseException("Could not retrieve database information from file. Please check 'database.properties' file for correct database host and credentials.");
            }

            try {
                return DriverManager.getConnection(MASTER_2_HOST, MASTER_2_USER, MASTER_2_PASSWORD);
            } catch (SQLException ex1) {
                throw new DatabaseException("Failed to establish a connection with database.", ex1);
            }
        }
    }
}
