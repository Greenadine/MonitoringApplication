package com.nerdygadgets.application.model.component;

import java.util.Locale;

/**
 * Super class for all components present in a {@link com.nerdygadgets.application.model.NetworkConfiguration}.
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

    public NetworkComponent(final long id, final String name, final double availability, final double price, final String ip, final String subnet) {
        this.id = id;
        this.name = name;
        this.availability = availability;
        this.price = price;
        this.score = availability * (1 / price);
        this.ip = ip;
        this.subnet = subnet;
    }

    /**
     * Gets the ID of the network component.
     *
     * @return the network component's ID.
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
     * @return the network component's availability.
     */
    public double getAvailability() {
        return availability;
    }

    /**
     * Gets the price of the network component.
     *
     * @return the network component's price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the score of the network component, based on its price and availability.
     *
     * @return the network component's score.
     */
    public double getScore() {
        return score;
    }

    /**
     * Gets the IP of the network component.
     *
     * @return the network component's IP.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Get the subnet mask of the network component.
     *
     * @return the network component's subnet mask.
     */
    public String getSubnet() {
        return subnet;
    }

    @Override
    public abstract boolean equals(Object o);

    @Override
    public String toString() {
        return String.format(Locale.US, "%s (ID: %d)\n- Availability: %.2f\n- Price: %.2f", name, id, availability, price);
    }

    @Override
    public int compareTo(NetworkComponent other) {
        return (int) (this.score - other.score);
    }
}
