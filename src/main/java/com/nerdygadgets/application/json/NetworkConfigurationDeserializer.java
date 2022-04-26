package com.nerdygadgets.application.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.nerdygadgets.application.model.NetworkComponentList;
import com.nerdygadgets.application.model.NetworkConfiguration;
import com.nerdygadgets.application.model.component.Database;
import com.nerdygadgets.application.model.component.Firewall;
import com.nerdygadgets.application.model.component.NetworkComponent;
import com.nerdygadgets.application.model.component.Webserver;
import com.nerdygadgets.application.util.Utils;

import java.io.IOException;

public class NetworkConfigurationDeserializer extends StdDeserializer<NetworkConfiguration> {

    public NetworkConfigurationDeserializer() {
        super(NetworkConfiguration.class);
    }

    @Override
    public NetworkConfiguration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode node = mapper.readTree(p);

        final String name = node.get("name").textValue();
        final String ip = node.get("ip").textValue();
        final String subnet = node.get("subnet").textValue();
        final Firewall firewall = (Firewall) mapper.readValue(node.get("firewall").traverse(mapper), NetworkComponent.class);

        final long[] databaseIds = mapper.readValue(node.get("databases").traverse(), long[].class);
        final NetworkComponentList<Database> databases = new NetworkComponentList<>(Utils.getDatabasesById(databaseIds));
        final long[] webserverIds = mapper.readValue(node.get("webservers").traverse(), long[].class);
        final NetworkComponentList<Webserver> webservers = new NetworkComponentList<>(Utils.getWebserversById(webserverIds));

        return new NetworkConfiguration(name, ip, subnet, firewall, databases, webservers);
    }
}
