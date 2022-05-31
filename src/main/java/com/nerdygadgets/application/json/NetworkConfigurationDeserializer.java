package com.nerdygadgets.application.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.nerdygadgets.application.model.NetworkComponentList;
import com.nerdygadgets.application.model.NetworkConfiguration;
import com.nerdygadgets.application.model.NetworkComponent;
import com.nerdygadgets.application.util.ApplicationUtils;
import com.nerdygadgets.application.util.Utils;
import com.nerdygadgets.application.util.database.GetDataFromDatabase;

import java.io.IOException;

public class NetworkConfigurationDeserializer extends StdDeserializer<NetworkConfiguration> {

    public NetworkConfigurationDeserializer() {
        super(NetworkConfiguration.class);
    }

    @Override
    public NetworkConfiguration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode node = mapper.readTree(p);

        if (!node.has("name")
                || !node.has("firewall")
                || !node.has("databases")
                || !node.has("webservers")) {
            throw new IOException("This file is not a valid network configuration.");
        }

        // Check if name is valid
        if (!node.get("name").isTextual()) {
            ApplicationUtils.showPopupErrorMessage("Could not load network configuration from file", "The selected JSON-file does not contain a valid network configuration.");
            return null;
        }

        final String name = node.get("name").textValue();

        // Check if firewall is valid
        if (!node.get("firewall").isInt()) {
            ApplicationUtils.showPopupErrorMessage("Could not load network configuration from file", "The selected JSON-file does not contain a valid network configuration.");
            return null;
        }

        // Check if firewall is valid
        NetworkComponent firewall;

        firewall = new GetDataFromDatabase().getFirewallById(node.get("firewall").asLong());
        // TODO first check if the firewall with given ID exist

        // Check if array of database IDs is valid
        // TODO maybe also check if array is an array of longs
        if (!node.get("databases").isArray()) {
            ApplicationUtils.showPopupErrorMessage("Could not load network configuration from file", "The selected JSON-file does not contain a valid network configuration.");
            return null;
        }

        final long[] databaseIds = mapper.readValue(node.get("databases").traverse(), long[].class);
        NetworkComponentList databases = new NetworkComponentList(Utils.getDatabasesById(databaseIds));

        // Check if array of webserver IDs is valid
        if (!node.get("webservers").isArray()) {
            ApplicationUtils.showPopupErrorMessage("Could not load network configuration from file", "The selected JSON-file does not contain a valid network configuration.");
            return null;
        }

        final long[] webserverIds = mapper.readValue(node.get("webservers").traverse(), long[].class);
        NetworkComponentList webservers = new NetworkComponentList(Utils.getWebserversById(webserverIds));

        return new NetworkConfiguration(name, firewall, databases, webservers);
    }
}
