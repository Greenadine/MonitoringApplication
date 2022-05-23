package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.util.Colors;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class ConfigurationComponentsList extends ApplicationPanel {
    public ConfigurationComponentsList(@NotNull ApplicationScreen parentScreen, @NotNull final String header) {
        super(parentScreen);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        JPanel headerPanel = new JPanel();
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        headerPanel.setBorder(new MatteBorder(2,2,2,2, Color.red));
        JLabel componentLabel = new JLabel(header);
        headerPanel.add(componentLabel);
        add(headerPanel);

        JPanel listOfComponentsWrapper = new JPanel();
        JScrollPane scrollableList = new JScrollPane(listOfComponentsWrapper);
        scrollableList.setBorder(new MatteBorder(2,2,2,2, Color.orange));
        scrollableList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollableList);




    }


    @Override
    public void onShowImpl() {

    }

    @Override
    public void onHideImpl() {

    }
}
