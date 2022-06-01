package com.nerdygadgets.application.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.nerdygadgets.application.model.NetworkConfiguration;
import com.nerdygadgets.application.util.Utils;

import java.io.IOException;

public class NetworkConfigurationSerializer extends StdSerializer<NetworkConfiguration> {

    public NetworkConfigurationSerializer() {
        super(NetworkConfiguration.class);
    }

    @Override
    public void serialize(NetworkConfiguration networkConfiguration, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("name", networkConfiguration.getName());
        gen.writeNumberField("firewall", networkConfiguration.getFirewall().getId());
        gen.writeObjectField("databases", Utils.getComponentIds(networkConfiguration.getDatabases().getComponents()));
        gen.writeObjectField("webservers", Utils.getComponentIds(networkConfiguration.getWebservers().getComponents()));

        gen.writeEndObject();
    }
}
