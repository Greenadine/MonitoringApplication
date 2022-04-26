package com.nerdygadgets.application.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.nerdygadgets.application.json.NetworkComponentDeserializer;
import com.nerdygadgets.application.json.NetworkComponentSerializer;
import com.nerdygadgets.application.json.NetworkConfigurationDeserializer;
import com.nerdygadgets.application.json.NetworkConfigurationSerializer;
import com.nerdygadgets.application.model.NetworkConfiguration;
import com.nerdygadgets.application.model.component.Database;
import com.nerdygadgets.application.model.component.Firewall;
import com.nerdygadgets.application.model.component.NetworkComponent;
import com.nerdygadgets.application.model.component.Webserver;
import com.nerdygadgets.application.util.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

/**
 * @author Kevin Zuman
 */
public class NetworkComponents {

    private final String networkComponentsDir = "networkcomponents" + File.separator;

    private final HashMap<Long, Database> databases;
    private final HashMap<Long, Webserver> webservers;
    private Firewall firewall;

    private final ObjectMapper mapper;

    public NetworkComponents() {
        this.databases = new HashMap<>();
        this.webservers = new HashMap<>();

        this.mapper = new ObjectMapper()
                .registerModule(new SimpleModule()
                        .addSerializer(NetworkComponent.class, new NetworkComponentSerializer())
                        .addDeserializer(NetworkComponent.class, new NetworkComponentDeserializer())
                        .addSerializer(NetworkConfiguration.class, new NetworkConfigurationSerializer())
                        .addDeserializer(NetworkConfiguration.class, new NetworkConfigurationDeserializer()));

        load();
    }

    /**
     * Gets the {@link NetworkComponent} with the provided ID.
     *
     * @param id The Id of the {@code NetworkComponent}.
     *
     * @return The {@code NetworkComponent} with the provided ID.
     */
    public Optional<? extends NetworkComponent> getComponent(final long id) {
        if (databases.containsKey(id)) {
            return Optional.of(databases.get(id));
        } else if (webservers.containsKey(id)) {
            return Optional.of(webservers.get(id));
        } else if (firewall.getId() == id) {
            return Optional.of(firewall);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Gets all the loaded {@link Database}s.
     *
     * @return A {@code link} containing all the loaded {@code Database}s.
     */
    public Collection<Database> getDatabases() {
        return databases.values();
    }

    /**
     * Gets the {@link Database} with the given ID.
     *
     * @param id The ID of the {@code Database}.
     *
     * @return The {@code Database} with the given ID.
     */
    public Optional<Database> getDatabase(final long id) {
        return Optional.ofNullable(databases.get(id));
    }

    /**
     * Gets all the loaded {@link Webserver}s.
     *
     * @return A {@code Collection} containing all the loaded {@code Webserver}s.
     */
    public Collection<Webserver> getWebservers() {
        return webservers.values();
    }

    /**
     * Gets the {@link Webserver} with the given ID.
     *
     * @param id The ID of the {@code Webserver}.
     *
     * @return The {@code Webserver} with the given ID.
     */
    public Optional<Webserver> getWebserver(final long id) {
        return Optional.ofNullable(webservers.get(id));
    }

    /**
     * Gets the {@link Firewall}.
     *
     * @return The {@code Firewall}.
     */
    public Optional<Firewall> getFirewall() {
        return Optional.ofNullable(firewall);
    }

    /**
     * Deserializes the provided JSON {@code File} into a new {@link NetworkConfiguration}.
     *
     * @param file The JSON {@code File}.
     *
     * @return The deserialized {@code NetworkConfiguration} from the JSON {@code File}.
     */
    public NetworkConfiguration deserializeConfiguration(@NotNull final File file) {
        // TODO

        return null;
    }

    /**
     * Attempts to load all {@link NetworkComponent}s from JSON files.
     */
    private void load() {
        Logger.info("Loading network components from file...");

        // Retrieve all JSOn files in network components directory
        Iterator<File> it = FileUtils.iterateFiles(new File(networkComponentsDir), new SuffixFileFilter(".json"), null);

        // Iterate through all files in directory
        while (it.hasNext()) {
            try {
                File networkComponentFile = it.next();
                NetworkComponent networkComponent = mapper.readValue(networkComponentFile, NetworkComponent.class);

                if (networkComponent instanceof Database) {
                    databases.put(networkComponent.getId(), (Database) networkComponent);
                } else if (networkComponent instanceof Webserver) {
                    webservers.put(networkComponent.getId(), (Webserver) networkComponent);
                } else if (networkComponent instanceof Firewall && firewall != null) {
                    firewall = (Firewall) networkComponent;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        Logger.info("Loaded %d database components from file.", databases.size());
        Logger.info("Loaded %d webserver components from file.", webservers.size());

        if (firewall != null) {
            Logger.info("Loaded 1 firewall component from file.");
        }
    }
}
