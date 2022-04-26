package com.nerdygadgets.application.util;

import com.google.common.primitives.Longs;
import com.nerdygadgets.application.Main;
import com.nerdygadgets.application.model.component.Database;
import com.nerdygadgets.application.model.component.NetworkComponent;
import com.nerdygadgets.application.model.component.Webserver;

import java.util.ArrayList;
import java.util.Collection;

public final class Utils {

    /**
     * Returns an {@code array} containing the IDs of the provided {@code Collection} of {@link NetworkComponent}s.
     *
     * @param networkComponents The {@code Collection} containing the {@code NetworkComponent}s.
     *
     * @return An {@code array} containing the IDs of the provided {@code Collection} of {@code NetworkComponent}s.
     */
    public static long[] getComponentIds(Collection<? extends NetworkComponent> networkComponents) {
        ArrayList<Long> componentIds = new ArrayList<>();

        for (NetworkComponent networkComponent : networkComponents) {
            componentIds.add(networkComponent.getId());
        }

        return Longs.toArray(componentIds);
    }

    /**
     * Returns an {@code ArrayList} containing the {@link Database}s with the provided IDs.
     *
     * @param ids The {@code array} of IDs.
     *
     * @return An {@code ArrayList} containing the {@code Database}s with the provided IDs.
     */
    public static ArrayList<Database> getDatabasesById(long[] ids) {
        ArrayList<Database> databases = new ArrayList<>();

        for (long id : ids) {
            Main.getNetworkComponents().getDatabase(id).ifPresent(databases::add);
        }

        return databases;
    }

    /**
     * Returns an {@code ArrayList} containing the {@link Webserver}s with the provided IDs.
     *
     * @param ids The {@code array} of IDs.
     *
     * @return An {@code ArrayList} containing the {@code Webserver}s with the provided IDs.
     */
    public static ArrayList<Webserver> getWebserversById(long[] ids) {
        ArrayList<Webserver> webservers = new ArrayList<>();

        for (long id : ids) {
            Main.getNetworkComponents().getWebserver(id).ifPresent(webservers::add);
        }

        return webservers;
    }
}
