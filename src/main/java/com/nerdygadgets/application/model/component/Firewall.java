package com.nerdygadgets.application.model.component;

import com.nerdygadgets.application.model.NetworkConfiguration;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * A {@code Firewall} is a {@link NetworkComponent} that can be added and/or removed from a {@link NetworkConfiguration}.
 *
 * @author Kevin Zuman
 */
public class Firewall extends NetworkComponent {

    private static long nextId = 0;

    public Firewall(final long id, @NotNull final String name, final double availability, final double price, @NotNull final String ip, @NotNull final String subnet) throws IOException {
        super(id, ComponentType.FIREWALL, name, availability, price, ip, subnet, ImageIO.read(new File("assets\\images\\firewall.png")));
    }

    public Firewall(@NotNull final String name, final double availability, final double price, @NotNull final String ip, @NotNull final String subnet) throws IOException {
        this(getNextId(), name, availability, price, ip, subnet);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Firewall && id == ((Firewall) o).id;
    }

    protected static long getNextId() {
        return Firewall.nextId++;
    }
}
