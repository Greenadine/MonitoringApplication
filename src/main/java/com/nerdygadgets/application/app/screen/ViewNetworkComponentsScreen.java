package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.model.NetworkComponentListTableModel;
import com.nerdygadgets.application.app.panel.ScreenHeaderPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.model.ApplicationWindow;
import com.nerdygadgets.application.util.ApplicationActions;
import org.jetbrains.annotations.NotNull;
import java.sql.*;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;



public class ViewNetworkComponentsScreen extends ApplicationScreen {



    private JPanel componentWrapperPanel;
    private JPanel propertiesWrapperPanel;

    private JButton deleteProperties;
    private JButton editProperties;

    public ViewNetworkComponentsScreen(@NotNull final ApplicationWindow window) {
        super(window);
       // Class.forName("com.mysql.jdbc.Driver");
        // Configure screen
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Populate screen
        this.add(new ScreenHeaderPanel(this, "Network Components", 1250, 50, ApplicationActions::openHome), BorderLayout.PAGE_START);

        componentWrapperPanel = new JPanel();
        componentWrapperPanel.setLayout(new FlowLayout());
        add(componentWrapperPanel, BorderLayout.CENTER);

        createDatabasesTable();
        createWebserversTable();
        createFirewallTable();

//       propertiesWrapperPanel = new JPanel();
//       propertiesWrapperPanel.setLayout(new FlowLayout());
//        add(propertiesWrapperPanel);


        createPropertiesTable();
        databaseConnection();

        setVisible(true);
    }

    private void createDatabasesTable() {


       // databasesTable = new JTable(new NetworkComponentListTableModel());
        //databasesTable = new JTable(new NetworkComponentListTableModel());

        // Create scroll pane as content panel


        String[] columnNames = {"Database"};

        Object[][] data = {
                {"Database 1"},
                {"Database 2"},
                {"Database 3"},
                {"Database 4"},
                {"Database 5"}
        };

        JTable table = new JTable(data, columnNames);


        componentWrapperPanel.add(table.getTableHeader());
        //add(table, BorderLayout.);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        componentWrapperPanel.add(scrollPane);



        // TODO finish
    }

    private void createWebserversTable() {
        //webserversTable = new JTable(new NetworkComponentListTableModel());

        String[] columnNames = {"Webserver"};

        Object[][] data = {
                {"Webserver 1"},
                {"Webserver 2"},
                {"Webserver 3"},
                {"Webserver 4"},
                {"Webserver 5"}
        };

        JTable table = new JTable(data, columnNames);


        componentWrapperPanel.add(table.getTableHeader());
        //add(table, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        componentWrapperPanel.add(scrollPane);
    }

    private void createFirewallTable() {
        //webserversTable = new JTable(new NetworkComponentListTableModel());

        String[] columnNames = {"Firewall"};

        Object[][] data = {
                {"Firewall 1"},
                {"Firewall 2"},
                {"Firewall 3"},
                {"Firewall 4"},
                {"Firewall 5"}
        };

        JTable table = new JTable(data, columnNames);


        componentWrapperPanel.add(table.getTableHeader());
        //add(table, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        componentWrapperPanel.add(scrollPane);
    }
    private void createPropertiesTable() {
        //webserversTable = new JTable(new NetworkComponentListTableModel());

        deleteProperties = new JButton("Delete");
        componentWrapperPanel.add(deleteProperties);
        editProperties = new JButton("Edit");
        componentWrapperPanel.add(editProperties);

        String[] columnNames = {"Properties"};

        Object[][] data = {
                {"Firewall 1"},
                {"Firewall 2"},
                {"Firewall 3"},
                {"Firewall 4"},
                {"Firewall 5"}
        };

        JTable table = new JTable(data, columnNames);




        componentWrapperPanel.add(table.getTableHeader());
        //add(table, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        componentWrapperPanel.add(scrollPane);
    }

    public void databaseConnection (){

        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nerdygadgets", "root","");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM firewall");

            while (resultSet.next()){
                System.out.println(resultSet.getString("name"));
            }
        }

        catch (Exception e){
            e.printStackTrace();
        }

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
