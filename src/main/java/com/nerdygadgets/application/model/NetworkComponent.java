package com.nerdygadgets.application.model;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Locale;

/**
 * Super class for all components present in a {@link NetworkConfiguration}.
 *
 * @author Kevin Zuman
 */
public class NetworkComponent implements Comparable<NetworkComponent> {

    protected final long id;
    protected final ComponentType type;
    protected final String name;
    protected final double availability;
    protected final double price;
    protected final double score;
    protected final String ip;
    protected final String subnetMask;

    public NetworkComponent(final long id, @NotNull final ComponentType type, @NotNull final String name, final double availability, final double price, @NotNull final String ip, @NotNull final String subnetMask) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.availability = availability;
        this.price = price;
        this.score = availability * (1 / price);
        this.ip = ip;
        this.subnetMask = subnetMask;
    }

    /**
     * Gets the ID of the {@code NetworkComponent}.
     *
     * @return The {@code NetworkComponent}'s ID.
     */
    public long getId() {
        return id;
    }

    /**
     * Gets the {@link ComponentType} of the {@code NetworkComponent}.
     *
     * @return The {@code ComponentType} of the {@code NetworkComponent}.
     */
    public ComponentType getType() {
        return type;
    }

    /**
     * Gets the name of the {@code NetworkComponent}.
     *
     * @return the {@code NetworkComponent}'s name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the availability of the {@code NetworkComponent} in fraction.
     *
     * @return The {@code NetworkComponent}'s availability.
     */
    public double getAvailability() {
        return availability;
    }

    /**
     * Gets the price of the {@code NetworkComponent}.
     *
     * @return The {@code NetworkComponent}'s price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the score of the {@code NetworkComponent}, based on its price and availability.
     *
     * @return The {@code NetworkComponent}'s score.
     */
    public double getScore() {
        return score;
    }

    /**
     * Gets the IP of the {@code NetworkComponent}.
     *
     * @return The {@code NetworkComponent}'s IP.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Get the subnet mask of the {@code NetworkComponent}.
     *
     * @return The {@code NetworkComponent}'s subnet mask.
     */
    public String getSubnetMask() {
        return subnetMask;
    }

    /**
     * Gets the {@link Image} representing the {@code NetworkComponent}.
     *
     * @return The {@code NetworkComponent}'s image.
     */
    public Image getImage() {
        // TODO get image representing the component's type, maybe put this in ComponentType instead of here
        return null;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s (ID: %d)\n- IP: %s\n- Subnet: %s\n- Availability: %.2f\n- Price: %.2f", name, id, ip, subnetMask, availability, price);
    }

    @Override
    public int compareTo(NetworkComponent other) {
        return (int) (this.score - other.score);
    }
}
