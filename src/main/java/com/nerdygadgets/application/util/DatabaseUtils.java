package com.nerdygadgets.application.util;

import com.nerdygadgets.application.Main;
import com.nerdygadgets.application.exception.DatabaseException;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public final class DatabaseUtils {

    private static final String HOST, DATABASE, USER, PASSWORD;

    static {
        Properties databaseProperties = new Properties();

        try (InputStream is = Main.class.getResourceAsStream("database.properties")) {

            databaseProperties.load(is);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        HOST = databaseProperties.getProperty("host", "localhost");
        DATABASE = databaseProperties.getProperty("database", "nerdygadgets");
        USER = databaseProperties.getProperty("user", "admin");
        PASSWORD = databaseProperties.getProperty("password", "");
    }

    /**
     * Attempted to connect to both master databases, and returns a {@link Connection} to either of the two databases, based on whichever was reached first.
     *
     * @return A {@code Connection} to one of the master databases.
     *
     * @throws DatabaseException When an issue occurs while attempting to connect to one of the
     */
    public static Connection getConnection() throws DatabaseException {
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + HOST + "/" + DATABASE, USER, PASSWORD);
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Failed to establish connection with database '%s' at host '%s'. Please check credentials in 'databases.properties'.", DATABASE, HOST);
        }
    }
}
