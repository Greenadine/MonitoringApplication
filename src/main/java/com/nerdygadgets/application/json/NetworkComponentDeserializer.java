package com.nerdygadgets.application.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.nerdygadgets.application.model.ComponentType;
import com.nerdygadgets.application.model.NetworkComponent;

import java.io.IOException;

public class NetworkComponentDeserializer extends StdDeserializer<NetworkComponent> {

    public NetworkComponentDeserializer() {
        super(NetworkComponent.class);
    }

    @Override
    public NetworkComponent deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode node = mapper.readTree(p);

        long id = node.get("id").longValue();
        ComponentType type = mapper.readValue(node.get("type").traverse(), ComponentType.class);
        String name = node.get("name").textValue();
        double availability = node.get("availability").doubleValue();
        double price = node.get("price").doubleValue();
        String ip = node.get("ip").textValue();
        String subnet = node.get("subnet").textValue();

        return new NetworkComponent(id, type, name, availability, price, ip, subnet);
    }
}
