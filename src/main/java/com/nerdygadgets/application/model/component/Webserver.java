package com.nerdygadgets.application.model.component;

public class Webserver extends NetworkComponent {

    private static long nextId = 0;

    public Webserver(final String name, final double availability, final double price, final String ip, final String subnet) {
        super(getNextId(), name, availability, price, ip, subnet);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Webserver && id == ((Webserver) o).id;
    }

    protected static long getNextId() {
        return Webserver.nextId++;
    }
}
