package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.model.NetworkComponentListTableModel;
import com.nerdygadgets.application.app.panel.ScreenHeaderPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.model.ApplicationWindow;
import com.nerdygadgets.application.util.ApplicationActions;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ViewNetworkComponentsScreen extends ApplicationScreen {

    private JTable databasesTable;
    private JTable webserversTable;

    public ViewNetworkComponentsScreen(@NotNull final ApplicationWindow window) {
        super(window);

        // Configure screen
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Populate screen
        this.add(new ScreenHeaderPanel(this, "Network Components", 1250, 50, ApplicationActions::openHome), BorderLayout.PAGE_START);

        createDatabasesTable();
        createWebserversTable();
    }

    private void createDatabasesTable() {
        databasesTable = new JTable(new NetworkComponentListTableModel());

        // Create scroll pane as content panel
        JScrollPane scrollPane = new JScrollPane(databasesTable);
        databasesTable.setFillsViewportHeight(true);

        // TODO finish
    }

    private void createWebserversTable() {
        webserversTable = new JTable(new NetworkComponentListTableModel());
    }

    @Override
    protected void onOpenImpl() {
        // TODO
    }

    @Override
    protected void onCloseImpl() {
        // TODO
    }
}
