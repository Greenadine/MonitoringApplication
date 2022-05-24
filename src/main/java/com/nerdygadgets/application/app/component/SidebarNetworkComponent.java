package com.nerdygadgets.application.app.component;

import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.PanelComponent;
import com.nerdygadgets.application.util.Colors;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class SidebarNetworkComponent extends PanelComponent {
    private JLabel componentName;
    private JButton addBtn;

    public SidebarNetworkComponent(@NotNull ApplicationPanel parentPanel) {
        super(parentPanel);

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        wrapper.setBackground(Color.red);

        componentName = new JLabel("test");
        wrapper.add(componentName);

        addBtn = new JButton("+");
        addBtn.setBounds(20, 20, 20, 20);
        wrapper.add(addBtn);

        this.add(wrapper);
    }

    @Override
    public void onShow() {

    }

    @Override
    public void onHide() {

    }
}
