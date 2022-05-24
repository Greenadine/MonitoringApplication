package com.nerdygadgets.application.app.component;

import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.PanelComponent;
import com.nerdygadgets.application.model.component.NetworkComponent;
import com.nerdygadgets.application.util.Colors;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NetworkConfigurationComponent extends PanelComponent {
    private JLabel componentName;
    private JButton removeBtn;

    public NetworkConfigurationComponent(@NotNull ApplicationPanel parentPanel, @NotNull NetworkComponent networkComponent) {
        super(parentPanel);
        this.setBorder(new EmptyBorder(0,0,10,0));
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BorderLayout());
        wrapper.setPreferredSize(new Dimension(200, 50));
        wrapper.setBorder(new EmptyBorder(0,0,6,0));
        wrapper.setBackground(Colors.MAIN_TABLE_HEADER);


        componentName = new JLabel(networkComponent.getName());
        componentName.setBorder(new EmptyBorder(0,5, 0, 0));
        wrapper.add(componentName, BorderLayout.CENTER);

        JPanel buttonWrapperPanel = new JPanel();
        buttonWrapperPanel.setBackground(Colors.MAIN_TABLE_HEADER);


        removeBtn = new JButton("-");
        removeBtn.setAlignmentX(LEFT_ALIGNMENT);
        buttonWrapperPanel.add(removeBtn);
        wrapper.add(buttonWrapperPanel, BorderLayout.LINE_END);

        this.add(wrapper);
    }

    @Override
    public void onShow() {

    }

    @Override
    public void onHide() {

    }
}
