package com.nerdygadgets.application.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.nerdygadgets.application.model.NetworkComponent;

import java.io.IOException;

public class NetworkComponentSerializer extends StdSerializer<NetworkComponent> {

    public NetworkComponentSerializer() {
        super(NetworkComponent.class);
    }

    @Override
    public void serialize(NetworkComponent component, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        gen.writeNumberField("id", component.getId());
        gen.writeObjectField("type", component.getType());
        gen.writeStringField("name", component.getName());
        gen.writeNumberField("availability", component.getAvailability());
        gen.writeNumberField("price", component.getPrice());
        gen.writeStringField("ip", component.getIp());
        gen.writeStringField("subnet", component.getSubnetMask());

        gen.writeEndObject();
    }
}
