package com.nerdygadgets.application.model.component;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * A {@code Database} is a {@link NetworkComponent} that can be added and/or removed from a {@link com.nerdygadgets.application.model.NetworkConfiguration}.
 *
 * @author Kevin Zuman
 */
public class Database extends NetworkComponent {

    private static long nextId = 0;

    public Database(final long id, @NotNull final String name, final double availability, final double price, @NotNull final String ip, @NotNull final String subnet) throws IOException {
        super(id, name, availability, price, ip, subnet, ImageIO.read(new File("assets\\images\\database.png")));
    }

    public Database(@NotNull final String name, final double availability, final double price, @NotNull final String ip, @NotNull final String subnet) throws IOException {
        this(getNextId(), name, availability, price, ip, subnet);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Database && id == ((Database) o).id;
    }

    protected static long getNextId() {
        return Database.nextId++;
    }
}
