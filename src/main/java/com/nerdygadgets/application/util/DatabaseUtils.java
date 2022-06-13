package com.nerdygadgets.application.util;

import com.nerdygadgets.application.exception.DatabaseException;
import com.nerdygadgets.application.model.ComponentType;
import com.nerdygadgets.application.model.NetworkComponent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public final class DatabaseUtils {

    private static final String HOST, PORT, DATABASE, USER, PASSWORD;

    static {
        Properties databaseProperties = new Properties();
        File propertiesFile = new File("database.properties");

        // TODO check if properties file already exists, and if not copy file from jar into current folder

//        try (InputStream is = Main.class.getResourceAsStream("database.properties")) {
//            databaseProperties.load(is);
//        } catch (IOException ex) {
//            Logger.error(ex, "Failed to load database properties.");
//        }

        HOST = databaseProperties.getProperty("host", "192.168.1.1");
        PORT = databaseProperties.getProperty("port", "3306");
        DATABASE = databaseProperties.getProperty("database", "nerdygadgets_application");
        USER = databaseProperties.getProperty("user", "myuser");
        PASSWORD = databaseProperties.getProperty("password", "mypass");
    }

    /**
     * Attempted to connect to the firewall, and returns a {@link Connection} to either of the two databases, based on to which the firewall forwards the request.
     *
     * @return A {@code Connection} to one of the master databases.
     *
     * @throws DatabaseException When an issue occurs while attempting to connect to the database.
     */
    private static Connection newConnection() throws DatabaseException {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE, USER, PASSWORD);
        } catch (SQLException ex) {
            ApplicationUtils.showPopupErrorDialog("Failed to reach database", "This could be due to connection issues, invalidly defined database host and credentials, or due to technical issues with the database. " +
                    "Please check the database information defined in the 'database.properties' file.");
            throw new DatabaseException(ex, "Failed to establish connection with database '%s' at host '%s'. Please check credentials in 'databases.properties'.", DATABASE, HOST);
        }
    }

    /**
     * Gets all the {@link NetworkComponent}s of the given {@link ComponentType} from the database.
     *
     * @param type The type of components to retrieve.
     *
     * @return An {@code ArrayList} containing all the retrieved {@code NetworkComponents}.
     */
    public static ArrayList<NetworkComponent> getComponents(@NotNull final ComponentType type) {
        final ArrayList<NetworkComponent> components = new ArrayList<>();
        Connection connection = newConnection();

        long id;
        String name, ip, subnetMask;
        double availability, price;

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s;", type.getTableName()));

            while (resultSet.next()) {
                id = resultSet.getLong("id");
                name = resultSet.getString("name");
                availability = resultSet.getDouble("availability");
                price = resultSet.getDouble("price");
                ip = resultSet.getString("ip");
                subnetMask = resultSet.getString("subnetmask");

                components.add(new NetworkComponent(id, type, name, availability, price, ip, subnetMask));
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, ex.getMessage());
        }

        try {
            connection.close();
        } catch (SQLException ex) {

            Logger.error(ex, "Failed to close connection to database.");
        }

        return components;
    }

    /**
     * Gets a firewall component from the database by ID.
     *
     * @param id The ID of the component.
     *
     * @return The firewall component with the given ID.
     */
    public static NetworkComponent getComponentById(@NotNull final ComponentType type, final long id) {
        Connection connection = newConnection();
        String query = String.format("SELECT * FROM %s WHERE id = ?;", type.getTableName());

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                final String name = resultSet.getString("name");
                final double availability = resultSet.getDouble("availability");
                final double price = resultSet.getDouble("price");
                final String ip = resultSet.getString("ip");
                final String subnetMask = resultSet.getString("subnetmask");

                return new NetworkComponent(id, type, name, availability, price, ip, subnetMask);
            }

            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.error(ex, "Failed to close connection to database.");
            }
            return null;
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Could not retrieve %s with ID '%d'.", type.name().toLowerCase(), id);
        }
    }

    /**
     * Inserts a new {@link NetworkComponent} into the database.
     *
     * @param component The {@code NetworkComponent} to insert.
     */
    public static long insertComponent(@NotNull final NetworkComponent component) {
        Connection connection = newConnection();
        String query = String.format("INSERT INTO %s (name, availability, price, ip, subnetmask) ", component.getType().getTableName()) + "values (?,?,?,?,?);";

        long id;

        try {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, component.getName());
            statement.setDouble(2, component.getAvailability());
            statement.setDouble(3, component.getPrice());
            statement.setString(4, component.getIp());
            statement.setString(5, component.getSubnetMask());

            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            id = generatedKeys.getInt(1);
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Failed to insert network component into database.");
        }

        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.error(ex, "Failed to close connection to database.");
        }

        return id;
    }

    /**
     * Deletes a {@link NetworkComponent} from the database.
     *
     * @param component The {@code NetworkComponent} to delete.
     */
    public static void deleteComponent(@NotNull final NetworkComponent component) {
        Connection connection = newConnection();
        String query = String.format("DELETE FROM %s WHERE id = ?;", component.getType().getTableName());

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, component.getId());

            statement.execute();
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Failed to delete %s with ID '%d' from database.", component.getType().name().toLowerCase(), component.getId());
        }

        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.error(ex, "Failed to close connection to database.");
        }
    }

    /**
     * Updates the {@link NetworkComponent}'s data in the database.
     *
     * @param component The {@code NetworkComponent} to update.
     */
    public static void updateComponent(@NotNull final NetworkComponent component) {
        Connection connection = newConnection();

        try {
            String query = String.format("UPDATE %s SET name = ?, availability = ?, price = ?, ip = ?, subnetmask = ? WHERE id = ?;", component.getType().getTableName());

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, component.getName());
            statement.setDouble(2, component.getAvailability());
            statement.setDouble(3, component.getPrice());
            statement.setString(4, component.getIp());
            statement.setString(5, component.getSubnetMask());
            statement.setLong(6, component.getId());

            statement.execute();
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Failed to update %s with ID '%d' in database.", component.getType().name().toLowerCase(), component.getId());
        }

        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.error(ex, "Failed to close connection to database.");
        }
    }
}
