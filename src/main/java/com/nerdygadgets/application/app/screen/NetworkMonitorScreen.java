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
    JPanel monitorPanels1 = new JPanel();
    JPanel monitorPanels2 = new JPanel();


    public NetworkMonitorScreen(@NotNull final MainWindow window) {
        super(window);
        // Configure screen
        this.setLayout(new BorderLayout());

        // Create and add panels
        this.add(new ScreenHeaderPanel(this, "Network Monitor", 1150, 50, ApplicationActions::openHome), BorderLayout.PAGE_START);

        //monitorPanels.setLayout(new FlowLayout(FlowLayout.LEFT));
        monitorPanels.setLayout(new GridLayout(0,3));
        this.add(monitorPanels, BorderLayout.CENTER);
        //monitorPanels.add(new SystemMonitorPanel(this, "This System"));
        addMonitorPanels();
    }

    private void addMonitorPanels() {
        // Create content panel
        NewSystemMonitorPanel localHost = new NewSystemMonitorPanel(this, "This system");
        NewSystemMonitor.monitorLocalhostUptime(localHost);
        NewSystemMonitor.monitorLocalhostCpuUsage(localHost);
        NewSystemMonitor.monitorLocalhostDisks(localHost);

        final String webserver1Address = "192.168.2.6";
        final String webserver1User = "administrator";
        final String webserver1Password = "WS21m1s2";

        final String webserver2Address = "192.168.2.5";
        final String webserver2User = "administrator";
        final String webserver2Password = "WS21m1s1";

        NewSystemMonitorPanel webserver1Panel = new NewSystemMonitorPanel(this, "Webserver 1");
        NewSystemMonitor.monitorWebserverUptime(webserver1Panel, webserver1Address, webserver1User, webserver1Password);
        NewSystemMonitor.monitorWebserverCpuUsage(webserver1Panel, webserver1Address, webserver1User, webserver1Password);
        NewSystemMonitor.monitorWebserverDisks(webserver1Panel, webserver1Address, webserver1User, webserver1Password);

        NewSystemMonitorPanel webserver2Panel = new NewSystemMonitorPanel(this, "Webserver 2");
        NewSystemMonitor.monitorWebserverUptime(webserver2Panel, webserver2Address, webserver2User, webserver2Password);
        NewSystemMonitor.monitorWebserverCpuUsage(webserver2Panel, webserver2Address, webserver2User, webserver2Password);
        NewSystemMonitor.monitorWebserverDisks(webserver2Panel, webserver2Address, webserver2User, webserver2Password);

        NewSystemMonitorPanel Database1 = new NewSystemMonitorPanel(this, "Database 1");
        NewSystemMonitor.monitorDatabaseUptime(Database1, "192.168.1.2", "", "");
        NewSystemMonitor.monitorDatabaseCpuUsage(Database1,"192.168.1.2", "", "");
        NewSystemMonitor.monitorDatabaseDisks(Database1, "192.168.1.2","","");

        NewSystemMonitorPanel Database2 = new NewSystemMonitorPanel(this, "Database 2");
        NewSystemMonitor.monitorDatabaseUptime(Database2, "192.168.1.3", "", "");
        NewSystemMonitor.monitorDatabaseCpuUsage(Database2,"192.168.1.3", "", "");
        NewSystemMonitor.monitorDatabaseDisks(Database2, "192.168.1.3","","");

        NewSystemMonitorPanel pfsense = new NewSystemMonitorPanel(this, "Pfsense");
        NewSystemMonitor.monitorPfsenseUptime(pfsense, "", "", "");
        NewSystemMonitor.monitorPfsenseCpuUsage(pfsense,"", "", "");
        NewSystemMonitor.monitorPfsenseDisks(pfsense, "","","");

        monitorPanels.add(localHost);
        monitorPanels.add(webserver1Panel);
        monitorPanels.add(webserver2Panel);
        monitorPanels.add(Database1);
        monitorPanels.add(Database2);
        monitorPanels.add(pfsense);

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
