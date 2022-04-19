package com.nerdygadgets.application.model.component;

public class Database extends NetworkComponent {

    private static long nextId = 0;

    public Database(final String name, final double availability, final double price, final String ip, final String subnet) {
        super(getNextId(), name, availability, price, ip, subnet);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Database && id == ((Database) o).id;
    }

    protected static long getNextId() {
        return Database.nextId++;
    }
}
