package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.util.Colors;
import org.jdesktop.swingx.JXGlassBox;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class ConfigurationComponentsList extends ApplicationPanel {

    private final JPanel componentsListWrapper;

    public ConfigurationComponentsList(@NotNull ApplicationScreen parentScreen, @NotNull final String header) {
        super(parentScreen);

        // Configure panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Create and add header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));
        headerPanel.setBorder(new MatteBorder(10,2,2,2, Colors.MAIN_BACKGROUND_ACCENT));
        JLabel componentLabel = new JLabel(header);
        headerPanel.add(componentLabel);
        this.add(headerPanel);

        // Create and add scroll pane
        componentsListWrapper = new JPanel();
        componentsListWrapper.setLayout(new BoxLayout(componentsListWrapper, BoxLayout.Y_AXIS));
        JScrollPane scrollableList = new JScrollPane(componentsListWrapper);
        scrollableList.setPreferredSize(new Dimension(300, 300));
        scrollableList.setBorder(new MatteBorder(2,2,2,2, Colors.MAIN_BACKGROUND_ACCENT));
        scrollableList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollableList);
    }

    public JPanel getComponentsListWrapper() {
        return componentsListWrapper;
    }

    @Override
    public void onShowImpl() {
        // Do nothing
    }

    @Override
    public void onHideImpl() {
        // Do nothing
    }
}

