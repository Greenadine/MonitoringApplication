package com.nerdygadgets.application.model;

import com.nerdygadgets.application.model.component.NetworkComponent;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.DoubleStream;

public class NetworkComponentList<T extends NetworkComponent> {

    private final ArrayList<T> networkComponents;

    public NetworkComponentList() {
        this(new ArrayList<>());
    }

    public NetworkComponentList(ArrayList<T> networkComponents) {
        this.networkComponents = networkComponents;
    }

    /**
     * Returns an {@link ArrayList} containing all the {@link NetworkComponent}s within the list.
     *
     * @return an {@link ArrayList} containing all the {@link NetworkComponent}s within the list.
     */
    public ArrayList<T> getComponents() {
        return networkComponents;
    }

    /**
     * Gets the {@link NetworkComponent} at the given index within the list.
     *
     * @return the {@link NetworkComponent} at the given index within the list.
     */
    public NetworkComponent getComponent(int index) {
        return networkComponents.get(index);
    }

    /**
     * Adds a network component to the list.
     *
     * @param networkComponent the {@link NetworkComponent} to add.
     */
    public void addComponent(T networkComponent) {
        this.networkComponents.add(networkComponent);
    }

    /**
     * Gets the {@link NetworkComponent} within the list with the given ID.
     *
     * @param id the ID of the {@link NetworkComponent}.
     *
     * @return the {@link NetworkComponent} within the list with the given ID.
     */
    public Optional<T> getComponentById(long id) {
        return networkComponents.stream().filter(networkComponent -> networkComponent.getId() == id).findFirst();
    }

    /**
     * Gets the joint availability of all the {@link NetworkComponent}s in this list combined.
     *
     * @return the joint availability of all the {@link NetworkComponent}s in this list combined.
     */
    public double getJointAvailability() {
        double jointDowntime = 1;

        for (T networkComponent : networkComponents) {
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
}
