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

    protected long id;
    protected final ComponentType type;
    protected String name;
    protected double availability;
    protected double price;
    protected String ip;
    protected String subnetMask;

    public NetworkComponent(@NotNull final ComponentType type, @NotNull final String name, final double availability,
                            final double price, @NotNull final String ip, @NotNull final String subnetMask) {
        this.id = -1;
        this.type = type;
        this.name = name;
        this.availability = availability;
        this.price = price;
        this.ip = ip;
        this.subnetMask = subnetMask;
    }

    public NetworkComponent(final long id, @NotNull final ComponentType type, @NotNull final String name, final double availability,
                            final double price, @NotNull final String ip, @NotNull final String subnetMask) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.availability = availability;
        this.price = price;
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
     * Sets the ID of the {@code NetworkComponent}.
     *
     * @param id The new ID.
     */
    public void setId(final long id) {
        this.id = id;
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

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the availability of the {@code NetworkComponent} in fraction.
     *
     * @return The {@code NetworkComponent}'s availability.
     */
    public double getAvailability() {
        return availability;
    }

    public void setAvailability(double availability) {
        this.availability = availability;
    }

    /**
     * Gets the price of the {@code NetworkComponent}.
     *
     * @return The {@code NetworkComponent}'s price
     */
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the score of the {@code NetworkComponent}, based on its price and availability.
     *
     * @return The {@code NetworkComponent}'s score.
     */
    public double getScore() {
        return availability * (1 / price);
    }

    /**
     * Gets the IP of the {@code NetworkComponent}.
     *
     * @return The {@code NetworkComponent}'s IP.
     */
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Get the subnet mask of the {@code NetworkComponent}.
     *
     * @return The {@code NetworkComponent}'s subnet mask.
     */
    public String getSubnetMask() {
        return subnetMask;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s (ID: %d)\n- IP: %s\n- Subnet: %s\n- Availability: %.2f\n- Price: %.2f", name, id, ip, subnetMask, availability, price);
    }

    @Override
    public int compareTo(NetworkComponent other) {
        return (int) (this.getScore() - other.getScore());
    }
}
