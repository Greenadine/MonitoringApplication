package com.nerdygadgets.application.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A {@code NetworkConfiguration} is a collection of a firewall and one or more databases and webservers that make up an entire network.
 *
 * @author Kevin Zuman
 */
public class NetworkConfiguration {

    private final String name;

    private NetworkComponent firewall;
    private final NetworkComponentList databases;
    private final NetworkComponentList webservers;

    public NetworkConfiguration(){
        this.name = "";
        this.databases = new NetworkComponentList();
        this.webservers = new NetworkComponentList();
    }

    public NetworkConfiguration(@NotNull final String name, @NotNull final NetworkComponent firewall) {
        this(name, firewall, new NetworkComponentList(), new NetworkComponentList());
    }

    public NetworkConfiguration(@NotNull final String name, @Nullable final NetworkComponent firewall,
                                @NotNull final NetworkComponentList databases, @NotNull final NetworkComponentList webservers) {
        this.name = name;
        this.firewall = firewall;
        this.databases = databases;
        this.webservers = webservers;
    }

    /**
     * Gets the name of the network configuration.
     *
     * @return The network configuration's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the firewall configured for the network.
     *
     * @return The {@code NetworkConfiguration}'s firewall.
     */
    public NetworkComponent getFirewall() {
        return firewall;
    }

    /**
     * Sets the firewall configured for the network.
     *
     * @param firewall The firewall.
     */
    public void setFirewall(@Nullable final NetworkComponent firewall) {
        this.firewall = firewall;

        if (firewall != null) {
            if (firewall.getType() != ComponentType.FIREWALL) {
                throw new IllegalArgumentException("Provided component is not a firewall.");
            }
        }
    }

    /**
     * Gets a {@link NetworkComponentList} containing all the databases configured for the network.
     *
     * @return The {@code NetworkConfiguration}'s databases.
     */
    public NetworkComponentList getDatabases() {
        return databases;
    }

    /**
     * Adds a database to the list of databases.
     *
     * @param database The database to add.
     */
    public void addDatabase(@NotNull final NetworkComponent database) {
        if (database.getType() != ComponentType.DATABASE) {
            throw new IllegalArgumentException("Provided component is not a database.");
        }
        this.databases.addComponent(database);
    }

    /**
     * Removes a database from the list of databases.
     *
     * @param database The database to remove.
     */
    public void removeDatabase(@NotNull final NetworkComponent database) {
        if (database.getType() != ComponentType.DATABASE) {
            throw new IllegalArgumentException("Provided component is not a database.");
        }
        this.databases.removeComponent(database);
    }

    /**
     * Gets a {@link NetworkComponentList} containing all the webservers configured for the network.
     *
     * @return the {@code NetworkConfiguration}'s webservers.
     */
    public NetworkComponentList getWebservers() {
        return webservers;
    }

    /**
     * Adds a webserver to the list of webservers.
     *
     * @param webserver The {@code Webserver} to add.
     */
    public void addWebserver(@NotNull final NetworkComponent webserver) {
        if (webserver.getType() != ComponentType.WEBSERVER) {
            throw new IllegalArgumentException("Provided component is not a webserver.");
        }
        this.webservers.addComponent(webserver);
    }

    /**
     * Removes a webserver from the list of webserves.
     *
     * @param webserver The webserver to remove.
     */
    public void removeWebserver(@NotNull final NetworkComponent webserver) {
        if (webserver.getType() != ComponentType.WEBSERVER) {
            throw new IllegalArgumentException("Provided component is not a webserver.");
        }
        this.webservers.removeComponent(webserver);
    }

    /**
     * Gets the joint availability of the network's firewall, databases and webservers.
     *
     * @return The availability of the entire {@code NetworkConfiguration}.
     */
    public double getAvailability() {
        if (firewall == null || databases.isEmpty() || webservers.isEmpty()) {
            return 0;
        }
        return firewall.getAvailability() * databases.getJointAvailability() * webservers.getJointAvailability();
    }

    /**
     * Gets the sum of the {@link NetworkConfiguration}'s firewalls'-, databases'- and webservers' prices.
     *
     * @return The price of the entire {@code NetworkConfiguration}.
     */
    public double getPrice() {
        if (firewall == null && databases.isEmpty() && webservers.isEmpty()) {
            return 0;
        }
        return (firewall != null ? firewall.getPrice() : 0) + databases.getTotalPrice() + webservers.getTotalPrice();
    }
}
