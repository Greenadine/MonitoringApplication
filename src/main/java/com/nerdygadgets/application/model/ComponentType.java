package com.nerdygadgets.application.model;

public enum ComponentType {

    DATABASE("database1"),
    WEBSERVER("webserver"),
    FIREWALL("firewall"),
    ;

    final String tableName;

    ComponentType(final String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }
}
