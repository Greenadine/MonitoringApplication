package com.nerdygadgets.application.model.component;

public class Firewall extends NetworkComponent {

    private static long nextId = 0;

    public Firewall(final String name, final double availability, final double price, final String ip, final String subnet) {
        super(getNextId(), name, availability, price, ip, subnet);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Firewall && id == ((Firewall) o).id;
    }

    protected static long getNextId() {
        return Firewall.nextId++;
    }
}
