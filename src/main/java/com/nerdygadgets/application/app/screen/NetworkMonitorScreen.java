package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.ApplicationWindow;
import com.nerdygadgets.application.app.panel.ScreenHeaderPanel;
import com.nerdygadgets.application.app.panel.monitor.SystemMonitorPanel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class NetworkMonitorScreen extends ApplicationScreen {

    public NetworkMonitorScreen(@NotNull final ApplicationWindow window) {
        super(window);

        // Configure screen
        this.setLayout(new BorderLayout());

        // Create and add panels
        this.add(new ScreenHeaderPanel(this, "Network Monitor", 1250, 50), BorderLayout.PAGE_START);

        addMonitorPanels();
    }

    private void addMonitorPanels() {
        // Create content panel
        JPanel monitorPanels = new JPanel();
        monitorPanels.setLayout(new FlowLayout());
        this.add(monitorPanels, BorderLayout.CENTER);

        // Populate panel
        monitorPanels.add(new SystemMonitorPanel(this, "This System"));
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
