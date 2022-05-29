package com.nerdygadgets.application.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.nerdygadgets.application.model.component.NetworkComponent;

import java.io.IOException;

public class NetworkComponentSerializer extends StdSerializer<NetworkComponent> {

    public NetworkComponentSerializer() {
        super(NetworkComponent.class);
    }

    @Override
    public void serialize(NetworkComponent networkComponent, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        gen.writeStringField("type", networkComponent.getType().name());
        gen.writeNumberField("id", networkComponent.getId());
        gen.writeStringField("name", networkComponent.getName());
        gen.writeNumberField("availability", networkComponent.getAvailability());
        gen.writeNumberField("price", networkComponent.getPrice());
        gen.writeStringField("ip", networkComponent.getIp());
        gen.writeStringField("subnet", networkComponent.getSubnetMask());

        gen.writeEndObject();
    }
}
