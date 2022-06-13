package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.panel.SystemMonitorPanel;
import com.nerdygadgets.application.app.panel.ScreenHeaderPanel;
import com.nerdygadgets.application.app.window.MainWindow;
import com.nerdygadgets.application.util.ApplicationActions;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class NetworkMonitorScreen extends ApplicationScreen {
    private final JPanel monitorPanels = new JPanel();

    public NetworkMonitorScreen(@NotNull final MainWindow window) {
        super(window);
        // Configure screen
        this.setLayout(new BorderLayout(0, 5));

        // Create and add panels
        this.add(new ScreenHeaderPanel(this, "Network Monitor", 1150, 50, ApplicationActions::openHome), BorderLayout.PAGE_START);

        //monitorPanels.setLayout(new FlowLayout(FlowLayout.LEFT));
        monitorPanels.setLayout(new GridLayout(0,3, 10, 15));
        this.add(monitorPanels, BorderLayout.CENTER);
        //monitorPanels.add(new SystemMonitorPanel(this, "This System"));
        addMonitorPanels();
    }

    private void addMonitorPanels() {
        // Add localhost monitoring panel
        SystemMonitorPanel localhostPanel = new SystemMonitorPanel(this, "This System");
        localhostPanel.createSchedulers(null, null, null, 3);
        monitorPanels.add(localhostPanel);

        // Add webservers monitoring panels
        SystemMonitorPanel webserver1Panel = new SystemMonitorPanel(this, "Webserver 1");
        webserver1Panel.createSchedulers("192.168.2.6", "administrator", "WS21m1s2", 1);
        monitorPanels.add(webserver1Panel);

        SystemMonitorPanel webserver2Panel = new SystemMonitorPanel(this, "Webserver 2");
        webserver2Panel.createSchedulers("192.168.2.5", "administrator", "WS21m1s1", 1);
        monitorPanels.add(webserver2Panel);

        // Add database monitoring panels
        SystemMonitorPanel database1Panel = new SystemMonitorPanel(this, "Database 1");
        database1Panel.createSchedulers("192.168.1.2", null, null, 2);
        monitorPanels.add(database1Panel);

        SystemMonitorPanel database2Panel = new SystemMonitorPanel(this, "Database 2");
        database2Panel.createSchedulers("192.168.1.3", null, null, 2);
        monitorPanels.add(database2Panel);

        SystemMonitorPanel pfSensePanel = new SystemMonitorPanel(this, "pfSense");
        pfSensePanel.createSchedulers(null, null, null, 0);
        monitorPanels.add(pfSensePanel);
    }

    @Override
    protected void onOpenImpl() {
        // Do nothing
    }

    @Override
    protected void onCloseImpl() {
        // Do nothing
    }
}
