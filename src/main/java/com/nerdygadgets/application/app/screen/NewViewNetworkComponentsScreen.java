package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.util.database.GetDataFromDatabase;
import com.nerdygadgets.application.util.database.PutDataInDatabase;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.model.ApplicationWindow;
import com.nerdygadgets.application.app.panel.networkcomponents.NetworkComponentButtonListPanel;
import com.nerdygadgets.application.app.panel.networkcomponents.NetworkComponentDetailsPanel;
import com.nerdygadgets.application.app.panel.ScreenHeaderPanel;
import com.nerdygadgets.application.model.component.Database;
import com.nerdygadgets.application.model.component.Firewall;
import com.nerdygadgets.application.model.component.Webserver;
import com.nerdygadgets.application.util.ApplicationActions;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class NewViewNetworkComponentsScreen extends ApplicationScreen implements ActionListener
{

    private final NetworkComponentButtonListPanel databaseList;
    private final NetworkComponentButtonListPanel webserverList;
    private final NetworkComponentButtonListPanel firewallList;

    private final NetworkComponentDetailsPanel detailsPanel;

    private JButton dialogAddComponent;

    public NewViewNetworkComponentsScreen(@NotNull ApplicationWindow window)
    {
        super(window);

        // Configure screen
        this.setLayout(new BorderLayout(3, 3));

        // Populate screen
        this.add(new ScreenHeaderPanel(this, "Network Components", 1250, 50, ApplicationActions::openHome), BorderLayout.PAGE_START);
        //Add dialog button
        dialogAddComponent = new JButton("Add component");
        this.add(dialogAddComponent, BorderLayout.PAGE_END);
        dialogAddComponent.addActionListener(this);


        // Create wrapper grid panel for component lists
        JPanel wrapperGridPanel = new JPanel();
        wrapperGridPanel.setLayout(new GridLayout(1, 3));
        this.add(wrapperGridPanel, BorderLayout.CENTER);

        // Add component details panel as right sidebar
        detailsPanel = new NetworkComponentDetailsPanel(this);
        this.add(detailsPanel, BorderLayout.LINE_END);



        // Add component lists to wrapper panel
        databaseList = new NetworkComponentButtonListPanel(this, "Databases", detailsPanel);
        wrapperGridPanel.add(databaseList);
        webserverList = new NetworkComponentButtonListPanel(this, "Webservers", detailsPanel);
        wrapperGridPanel.add(webserverList);
        firewallList = new NetworkComponentButtonListPanel(this, "Firewalls", detailsPanel);
        wrapperGridPanel.add(firewallList);



    }

    public NetworkComponentDetailsPanel getDetailsPanel()
    {
        return detailsPanel;
    }

    @Override
    protected void onOpenImpl()
    {
        databaseList.clear();
        firewallList.clear();
        webserverList.clear();
        // TODO load components from database to lists

        GetDataFromDatabase databaseConnection = new GetDataFromDatabase();
        for (Database database: databaseConnection.getDatabaseFromDatabase()){
            databaseList.addComponent(database);
        }
        for (Firewall firewall: databaseConnection.getFirewallFromDatabase()){
            firewallList.addComponent(firewall);
        }
        for (Webserver webserver: databaseConnection.getWebserverFromDatabase()){
            webserverList.addComponent(webserver);
        }

       // this.repaint();




//this.repaint();



//        try
//        {
//            // Add mock databases
//            for (int i = 0; i < 10; i++)
//            {
//                Database database = new Database("Database " + (i + 1), 90, 4000, "192.168.0." + (1 + i), "255.255.255.0");
//                databaseList.addComponent(database);
//            }
//
//            // Add mock webservers
//            for (int i = 0; i < 15; i++)
//            {
//                Webserver webserver = new Webserver("Webserver " + (i + 1), 90, 4000, "192.168.0." + (1 + i), "255.255.255.0");
//                webserverList.addComponent(webserver);
//            }
//
//            // Add mock firewalls
//            for (int i = 0; i < 3; i++)
//            {
//                Firewall firewall = new Firewall("Firewall " + (i + 1), 90, 4000, "192.168.0." + (1 + i), "255.255.255.0");
//                firewallList.addComponent(firewall);
//            }
//
//
//        } catch (IOException ignored)
//        {
//        }
    }

    @Override
    protected void onCloseImpl()
    {
        databaseList.clear();
        webserverList.clear();
        firewallList.clear();
    }




    @Override
    public void actionPerformed(ActionEvent e)
    {
        //Haalt de ingevoerde gegevens van het dialoog op
        if (e.getSource() == dialogAddComponent){
        AddComponentDialog dialoog = new AddComponentDialog(true);
        try
        {

            if (dialoog.componentList.getSelectedItem() == "database"){
                Database d1 = dialoog.getDatabaseWaarde();
                if (d1 != null){
                    PutDataInDatabase DataIn = new PutDataInDatabase();
                    DataIn.putDatabaseObjectInDatabase(d1.getName(),d1.getAvailability(),d1.getPrice(),d1.getIp(),d1.getSubnetMask());



                }
                else {
                    System.out.println("database object is leeg");
                }
            }
            else if (dialoog.componentList.getSelectedItem() == "webserver"){
                Webserver w1 = dialoog.getWebserverWaarde();
                if (w1 != null){
                    PutDataInDatabase DataIn = new PutDataInDatabase();
                    DataIn.putWebserverObjectInDatabase(w1.getName(),w1.getAvailability(),w1.getPrice(),w1.getIp(),w1.getSubnetMask());

                   //onOpenImpl();

                }
                else {
                    System.out.println("Webserver object is leeg");
                }
            }
            else if (dialoog.componentList.getSelectedItem() == "firewall"){
                Firewall f1 = dialoog.getFirewallWaarde();
                if (f1 != null){
                    PutDataInDatabase DataIn = new PutDataInDatabase();
                    DataIn.putFirewallObjectInDatabase(f1.getName(),f1.getAvailability(),f1.getPrice(),f1.getIp(),f1.getSubnetMask());


                }
                else {
                    System.out.println("Firewall object is leeg");
                }
            }



        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
            onOpenImpl();


    }
    }
}

