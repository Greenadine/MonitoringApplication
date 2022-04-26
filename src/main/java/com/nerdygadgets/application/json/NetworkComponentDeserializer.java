package com.nerdygadgets.application.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.nerdygadgets.application.model.component.Database;
import com.nerdygadgets.application.model.component.Firewall;
import com.nerdygadgets.application.model.component.NetworkComponent;
import com.nerdygadgets.application.model.component.Webserver;

import java.io.IOException;

public class NetworkComponentDeserializer extends StdDeserializer<NetworkComponent> {

    public NetworkComponentDeserializer() {
        super(NetworkComponent.class);
    }

    @Override
    public NetworkComponent deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        String type = node.get("type").textValue();
        long id = node.get("id").longValue();
        String name = node.get("name").textValue();
        double availability = node.get("availability").doubleValue();
        double price = node.get("price").doubleValue();
        String ip = node.get("ip").textValue();
        String subnet = node.get("subnet").textValue();

        switch (type) {
            case "database": {
                return new Database(id, name, availability, price, ip, subnet);
            }
            case "firewall": {
                return new Firewall(id, name, availability, price, ip, subnet);
            }
            case "webserver": {
                return new Webserver(id, name, availability, price, ip, subnet);
            }
            default: throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
