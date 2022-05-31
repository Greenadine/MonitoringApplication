package com.nerdygadgets.application.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.nerdygadgets.application.json.NetworkConfigurationDeserializer;
import com.nerdygadgets.application.json.NetworkConfigurationSerializer;
import com.nerdygadgets.application.model.NetworkConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public final class NetworkConfigurationUtils {

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper().registerModule(new SimpleModule()
                .addSerializer(NetworkConfiguration.class, new NetworkConfigurationSerializer())
                .addDeserializer(NetworkConfiguration.class, new NetworkConfigurationDeserializer()));
    }

    /**
     * Serializes the given {@link NetworkConfiguration} into a JSON {@code File}.
     *
     * @param configuration The {@code NetworkConfiguration}.
     */
    public static void serialize(@NotNull final NetworkConfiguration configuration, @NotNull final File file) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, configuration);
        } catch (IOException ex) {
            Logger.error(ex, "Failed to serialize and save network configuration to '%s'.", file.getAbsolutePath());
        }
    }

    /**
     * Deserializes the given {@code File} into a {@link NetworkConfiguration}.
     *
     * @param file The {@code File} to deserialize.
     *
     * @return The deserialized {@code NetworkConfiguration}.
     */
    public static NetworkConfiguration deserialize(@NotNull final File file) {
        NetworkConfiguration configuration = null;

        try {
            configuration = mapper.readValue(file, NetworkConfiguration.class);
        } catch (IOException ex) {
            Logger.error(ex, "Failed to deserialize file '%s' into network configuration.", file.getAbsolutePath());
        }

        return configuration;
    }
}
