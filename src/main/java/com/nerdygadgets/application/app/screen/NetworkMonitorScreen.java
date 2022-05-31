package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.panel.NewSystemMonitorPanel;
import com.nerdygadgets.application.app.panel.ScreenHeaderPanel;
import com.nerdygadgets.application.app.panel.SystemMonitorPanel;
import com.nerdygadgets.application.app.window.MainWindow;
import com.nerdygadgets.application.util.ApplicationActions;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class NetworkMonitorScreen extends ApplicationScreen {

    public NetworkMonitorScreen(@NotNull final MainWindow window) {
        super(window);

        // Configure screen
        this.setLayout(new BorderLayout());

        // Create and add panels
        this.add(new ScreenHeaderPanel(this, "Network Monitor", 1150, 50, ApplicationActions::openHome), BorderLayout.PAGE_START);

        addMonitorPanels();
    }

    private void addMonitorPanels() {
        // Create content panel
        JPanel monitorPanels = new JPanel();
        monitorPanels.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(monitorPanels, BorderLayout.CENTER);

        monitorPanels.add(new SystemMonitorPanel(this, "This System"));
        monitorPanels.add(new NewSystemMonitorPanel(this, "Test"));
    }

    public void startMonitoringSystems() {
        for (ApplicationPanel panel : panels) {
            if (panel instanceof SystemMonitorPanel) {
                ((SystemMonitorPanel) panel).startMonitoringSystemStatus();
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

    @Override
    protected void onOpenImpl() {
        // Do nothing
    }

    @Override
    protected void onCloseImpl() {
        // Do nothing
    }
}
