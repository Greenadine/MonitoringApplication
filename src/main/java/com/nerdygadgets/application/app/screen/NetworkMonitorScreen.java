package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.panel.NewSystemMonitorPanel;
import com.nerdygadgets.application.app.panel.ScreenHeaderPanel;
import com.nerdygadgets.application.app.panel.SystemMonitorPanel;
import com.nerdygadgets.application.app.window.MainWindow;
import com.nerdygadgets.application.util.ApplicationActions;
import com.nerdygadgets.application.util.NewSystemMonitor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class NetworkMonitorScreen extends ApplicationScreen {
    JPanel monitorPanels = new JPanel();

    public NetworkMonitorScreen(@NotNull final MainWindow window) {
        super(window);
        // Configure screen
        this.setLayout(new BorderLayout());

        // Create and add panels
        this.add(new ScreenHeaderPanel(this, "Network Monitor", 1150, 50, ApplicationActions::openHome), BorderLayout.PAGE_START);

        monitorPanels.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(monitorPanels, BorderLayout.CENTER);
        monitorPanels.add(new SystemMonitorPanel(this, "This System"));
        addMonitorPanels();
    }

    private void addMonitorPanels() {
        // Create content panel
        NewSystemMonitorPanel testMonitorPanel = new NewSystemMonitorPanel(this, "Webserver 1");
        NewSystemMonitor.monitorWebserverUptime(testMonitorPanel, "", "", "");
        NewSystemMonitor.monitorWebserverCpuUsage(testMonitorPanel,"", "", "");
        NewSystemMonitor.monitorWebserverDisks(testMonitorPanel, "","","");

        NewSystemMonitorPanel Database1 = new NewSystemMonitorPanel(this, "Database 1");
        NewSystemMonitor.monitorDatabaseUptime(Database1, "", "", "");
        NewSystemMonitor.monitorDatabaseCpuUsage(Database1,"", "", "");
        NewSystemMonitor.monitorDatabaseDisks(Database1, "","","");

        monitorPanels.add(testMonitorPanel);
        monitorPanels.add(Database1);
    }


/*
    public void startMonitoringSystems() {
        System.out.println("praarararara");
        for (ApplicationPanel panel : panels) {
            if (panel instanceof NewSystemMonitorPanel) {
                if(((NewSystemMonitorPanel) panel).getSystemName().contains("database")){
                    System.out.println("praarararara");
                }else{

                }
            }


        }
    }

    public void stopMonitoringSystems() {
        for (ApplicationPanel panel : panels) {
            if (panel instanceof SystemMonitorPanel) {
                ((SystemMonitorPanel) panel).stopMonitoringSystemStatus();
            }
        }
    }
*/

    @Override
    protected void onOpenImpl() {
        // Do nothing
    }


    @Override
    protected void onCloseImpl() {
        // Do nothing
    }
    /*private class DiskUpdater implements Runnable{

        @Override
        public void run() {
            final ArrayList<SystemMonitor.DiskResult> sshDisks = getLocalDisks();
        }
    }*/
}
