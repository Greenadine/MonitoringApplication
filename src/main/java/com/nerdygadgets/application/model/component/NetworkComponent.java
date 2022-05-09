package com.nerdygadgets.application.model.component;

import com.nerdygadgets.application.model.NetworkConfiguration;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Locale;

/**
 * Super class for all components present in a {@link NetworkConfiguration}.
 *
 * @author Kevin Zuman
 */
public abstract class NetworkComponent implements Comparable<NetworkComponent> {

    protected final long id;
    protected final String name;
    protected final double availability;
    protected final double price;
    protected final double score;
    protected final String ip;
    protected final String subnet;
    protected final Image image;

    public NetworkComponent(final long id, @NotNull final String name, final double availability, final double price, @NotNull final String ip, @NotNull final String subnet, @NotNull final Image image) {
        this.id = id;
        this.name = name;
        this.availability = availability;
        this.price = price;
        this.score = availability * (1 / price);
        this.ip = ip;
        this.subnet = subnet;
        this.image = image;
    }

    /**
     * Gets the type identifier of the network component.
     *
     * @return The network component's type identifier.
     */
    public String getType() {
        return getClass().getSimpleName().toLowerCase();
    }

    /**
     * Gets the ID of the network component.
     *
     * @return The network component's ID.
     */
    public long getId() {
        return id;
    }

    /**
     * Gets the name of the network component.
     *
     * @return the network component's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the availability of the network component in fraction.
     *
     * @return The network component's availability.
     */
    public double getAvailability() {
        return availability;
    }

    /**
     * Gets the price of the network component.
     *
     * @return The network component's price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the score of the network component, based on its price and availability.
     *
     * @return The network component's score.
     */
    public double getScore() {
        return score;
    }

    /**
     * Gets the IP of the network component.
     *
     * @return The network component's IP.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Get the subnet mask of the network component.
     *
     * @return The network component's subnet mask.
     */
    public String getSubnet() {
        return subnet;
    }

    /**
     * Gets the {@link Image} representing the network component.
     *
     * @return The network component's image.
     */
    public Image getImage() {
        return image;
    }

    @Override
    public abstract boolean equals(Object o);

    @Override
    public String toString() {
        return String.format(Locale.US, "%s (ID: %d)\n- IP: %s\n- Subnet: %s\n- Availability: %.2f\n- Price: %.2f", name, id, ip, subnet, availability, price);
    }

    @Override
    public int compareTo(NetworkComponent other) {
        return (int) (this.score - other.score);
    }
}
