package com.nerdygadgets.application.model;

import com.nerdygadgets.application.model.component.Database;
import com.nerdygadgets.application.model.component.Firewall;
import com.nerdygadgets.application.model.component.Webserver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A {@code NetworkConfiguration} is a collection of a {@link Firewall} and one or more {@link Database}s and {@link Webserver}s that make up an entire network.
 *
 * @author Kevin Zuman
 */
public class NetworkConfiguration {

    private final String name;
    private final String ip;
    private final String subnet;

    private Firewall firewall;
    private final NetworkComponentList<Database> databases;
    private final NetworkComponentList<Webserver> webservers;

    public NetworkConfiguration(@NotNull final String name, @NotNull final String ip, @NotNull final String subnet) {
        this(name, ip, subnet, null, new NetworkComponentList<>(), new NetworkComponentList<>());
    }

    public NetworkConfiguration(@NotNull final String name, @NotNull final String ip, @NotNull final String subnet, @Nullable final Firewall firewall,
                                @NotNull final NetworkComponentList<Database> databases, @NotNull final NetworkComponentList<Webserver> webservers) {
        this.name = name;
        this.ip = ip;
        this.subnet = subnet;
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
     * Gets the IP of the network configuration.
     *
     * @return The network configuration's IP.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Gets the subnet mask of the network configuration.
     *
     * @return The network configuration's subnet mask.
     */
    public String getSubnet() {
        return subnet;
    }

    /**
     * Gets the {@link Firewall} configured for the network.
     *
     * @return The {@code NetworkConfiguration}'s {@link Firewall}.
     */
    public Firewall getFirewall() {
        return firewall;
    }

    /**
     * Sets the {@link Firewall} configured for the network.
     *
     * @param firewall The {@code Firewall}.
     */
    public void setFirewall(@Nullable final Firewall firewall) {
        this.firewall = firewall;
    }

    /**
     * Gets a {@link NetworkComponentList} containing all the {@link Database}s configured for the network.
     *
     * @return The {@code NetworkConfiguration}'s {@code Database}s.
     */
    public NetworkComponentList<Database> getDatabases() {
        return databases;
    }

    /**
     * Adds a {@link Database} to the list of {@code Database}s.
     *
     * @param database The {@code Database} to add.
     */
    public void addDatabase(@NotNull final Database database) {
        this.databases.addComponent(database);
    }

    /**
     * Removes a {@link Database} from the list of {@code Database}s.
     *
     * @param database The {@code Database} to remove.
     */
    public void removeDatabase(@NotNull final Database database) {
        this.databases.removeComponent(database);
    }

    /**
     * Gets a {@link NetworkComponentList} containing all the {@link Webserver}s configured for the network.
     *
     * @return the {@code NetworkConfiguration}'s {@link Webserver}s.
     */
    public NetworkComponentList<Webserver> getWebservers() {
        return webservers;
    }

    /**
     * Adds a {@link Webserver} to the list of {@code Webserver}s.
     *
     * @param webserver The {@code Webserver} to add.
     */
    public void addWebserver(@NotNull final Webserver webserver) {
        this.webservers.addComponent(webserver);
    }

    /**
     * Removes a {@link Webserver} from the list of {@code Webserver}s.
     *
     * @param webserver The {@code Webserver} to remove.
     */
    public void removeWebserver(@NotNull final Webserver webserver) {
        this.webservers.removeComponent(webserver);
    }

    /**
     * Gets the joint availability of the network's {@link Firewall}, {@link Database}s and {@link Webserver}s.
     *
     * @return the availability of the entire {@code NetworkConfiguration}.
     */
    public double getAvailability() {
        return firewall.getAvailability() * databases.getJointAvailability() * webservers.getJointAvailability();
    }

    /**
     * Gets the sum of the {@code Network}'s {@link Firewall}s'-, {@link Database}s'- and {@link Webserver}s' prices.
     *
     * @return the price of the entire {@code NetworkConfiguration}.
     */
    public double getPrice() {
        return firewall.getPrice() + databases.getTotalPrice() + webservers.getTotalPrice();
    }
}
