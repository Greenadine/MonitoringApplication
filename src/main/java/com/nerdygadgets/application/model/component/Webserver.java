package com.nerdygadgets.application.model.component;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * A {@code Webserver} is a {@link NetworkComponent} that can be added and/or removed from a {@link com.nerdygadgets.application.model.NetworkConfiguration}.
 *
 * @author Kevin Zuman
 */
public class Webserver extends NetworkComponent {

    private static long nextId = 0;

    public Webserver(final long id, @NotNull final String name, final double availability, final double price, @NotNull final String ip, @NotNull final String subnet) throws IOException {
        super(id, name, availability, price, ip, subnet, ImageIO.read(new File("assets\\images\\webserver.png")));
    }

    public Webserver(@NotNull final String name, final double availability, final double price, @NotNull final String ip, @NotNull final String subnet) throws IOException {
        this(getNextId(), name, availability, price, ip, subnet);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Webserver && id == ((Webserver) o).id;
    }

    protected static long getNextId() {
        return Webserver.nextId++;
    }
}
