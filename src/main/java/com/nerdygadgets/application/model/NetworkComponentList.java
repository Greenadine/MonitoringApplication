package com.nerdygadgets.application.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Optional;

/**
 * A {@code NetworkComponentList} is an extension upon the {@link ArrayList} that adds some functionality to easily retrieve information of a collection of {@link NetworkComponent}s of the same type.
 *
 * @author Kevin Zuman
 */
public class NetworkComponentList {

    private final ArrayList<NetworkComponent> networkComponents;

    public NetworkComponentList() {
        this(new ArrayList<>());
    }

    public NetworkComponentList(final ArrayList<NetworkComponent> networkComponents) {
        this.networkComponents = networkComponents;
    }

    /**
     * Returns an {@link ArrayList} containing all the {@link NetworkComponent}s within the list.
     *
     * @return an {@link ArrayList} containing all the {@link NetworkComponent}s within the list.
     */
    public ArrayList<NetworkComponent> getComponents() {
        return networkComponents;
    }

    /**
     * Gets the {@link NetworkComponent} at the given index within the list.
     *
     * @return the {@link NetworkComponent} at the given index within the list.
     */
    public NetworkComponent getComponent(final int index) {
        return networkComponents.get(index);
    }

    /**
     * Adds a network component to the list.
     *
     * @param networkComponent the {@link NetworkComponent} to add.
     */
    public void addComponent(@NotNull final NetworkComponent networkComponent) {
        this.networkComponents.add(networkComponent);
    }

    /**
     * Adds a {@link NetworkComponent} to the list.
     *
     * @param networkComponent The {@code NetworkComponent}.
     */
    public void removeComponent(@NotNull final NetworkComponent networkComponent) {
        this.networkComponents.remove(networkComponent);
    }

    /**
     * Gets the {@link NetworkComponent} within the list with the given ID.
     *
     * @param id the ID of the {@link NetworkComponent}.
     *
     * @return the {@link NetworkComponent} within the list with the given ID.
     */
    public Optional<NetworkComponent> getComponentById(final long id) {
        return networkComponents.stream().filter(networkComponent -> networkComponent.getId() == id).findFirst();
    }

    /**
     * Gets the joint availability of all the {@link NetworkComponent}s in this list combined.
     *
     * @return the joint availability of all the {@link NetworkComponent}s in this list combined.
     */
    public double getJointAvailability() {
        double jointDowntime = 1;

        for (NetworkComponent networkComponent : networkComponents) {
            jointDowntime *= 1 - networkComponent.getAvailability();
        }

        return 1 - jointDowntime;
    }

    /**
     * Gets the total price of all the {@link NetworkComponent}s in this list combined.
     *
     * @return the total price of all the {@link NetworkComponent}s in this list combined.
     */
    public double getTotalPrice() {
        return networkComponents.stream().mapToDouble(NetworkComponent::getPrice).sum();
    }

    /**
     * Returns whether this list is empty.
     *
     * @return {@code true} if this list is empty, {@code false} if the list contains any {@link NetworkComponent}.
     */
    public boolean isEmpty() {
        return networkComponents.isEmpty();
    }
}
