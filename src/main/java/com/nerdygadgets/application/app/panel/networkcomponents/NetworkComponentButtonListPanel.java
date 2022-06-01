package com.nerdygadgets.application.app.panel.networkcomponents;

import com.nerdygadgets.application.app.component.NetworkComponentsEntry;
import com.nerdygadgets.application.app.component.WrappedJLabel;
import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.model.NetworkComponent;
import com.nerdygadgets.application.util.Colors;
import com.nerdygadgets.application.util.Fonts;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class NetworkComponentButtonListPanel extends ApplicationPanel {

    private final JPanel listPanel;

    private final NetworkComponentDetailsPanel detailsPanel;

    public NetworkComponentButtonListPanel(@NotNull ApplicationScreen parentScreen, @NotNull final String header, @NotNull final NetworkComponentDetailsPanel detailsPanel) {
        super(parentScreen);

        this.detailsPanel = detailsPanel;
        this.setLayout(new BorderLayout());

        // Create header
        WrappedJLabel headerPanel = new WrappedJLabel(header, SwingConstants.CENTER);
        headerPanel.setBackground(Colors.MAIN_BACKGROUND);
        headerPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
        headerPanel.setBorder(new MatteBorder(2, 2, 2, 2, Colors.MAIN_BACKGROUND_ACCENT));
        headerPanel.getLabel().setFont(Fonts.MAIN_SIDEBAR_HEADER);
        this.add(headerPanel, BorderLayout.PAGE_START);

        // Create scrollable list panel
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setPreferredSize(new Dimension(200, 300));
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        scrollPane.setBorder(new MatteBorder(0, 2, 2, 2, Colors.MAIN_BACKGROUND_ACCENT));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Always display vertical scroll bar
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void addComponent(NetworkComponent component) {
        NetworkComponentsEntry componentButton = new NetworkComponentsEntry(this, component, detailsPanel);
        listPanel.add(componentButton);
        listPanel.revalidate();
        listPanel.repaint();
    }

    public void clear() {
        listPanel.removeAll();
    }

    @Override
    public void onShowImpl() {
        // Do nothing
    }

    @Override
    public void onHideImpl() {
        listPanel.removeAll();
    }
}
