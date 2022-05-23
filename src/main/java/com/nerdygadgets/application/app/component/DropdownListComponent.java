package com.nerdygadgets.application.app.component;

import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.PanelComponent;
import com.nerdygadgets.application.util.SwingUtils;
import org.jdesktop.swingx.JXCollapsiblePane;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;

public class DropdownListComponent extends PanelComponent {

    private final JButton listToggleButton;
    private final JXCollapsiblePane listPane;

    public DropdownListComponent(@NotNull ApplicationPanel parentPanel, @NotNull final String header) {
        super(parentPanel);

        // Configure component
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Create list toggle button
        listToggleButton = SwingUtils.createButton(header, new ImageIcon(getClass().getResource("/assets/icons/arrow-up.png")), 250, 40, this::actionToggleWebserversList);
        listToggleButton.setHorizontalAlignment(SwingConstants.LEFT);

        // Create collapsible panel
        listPane = new JXCollapsiblePane();
        listPane.setLayout(new BoxLayout(listPane.getContentPane(), BoxLayout.Y_AXIS));
        listPane.setBorder(new EmptyBorder(5, 10, 5, 10));

        // Add components
        this.add(listToggleButton);
        this.add(listPane);
    }

    public void addComponent(JComponent component) {
        listPane.add(component);
    }

    /**
     * The {@link Action} that is performed when the list is expanded or collapsed.
     *
     * @param event The {@link ActionEvent}.
     */
    private void actionToggleWebserversList(ActionEvent event) {
        listPane.setCollapsed(!listPane.isCollapsed()); // Toggle collapsed

        // Change icon to signify whether the list is expanded or collapsed
        if (listPane.isCollapsed()) {
            listToggleButton.setIcon(new ImageIcon(getClass().getResource("/assets/icons/arrow-down.png")));
        } else {
            listToggleButton.setIcon(new ImageIcon(getClass().getResource("/assets/icons/arrow-up.png")));
        }
    }

    @Override
    public void onShow() {
        // Temporarily disable animations to avoid having to wait for the animation to finish
        listPane.setAnimated(false);
        listPane.setCollapsed(false);
        listPane.setAnimated(true);
    }

    @Override
    public void onHide() {
        // Do nothing
    }
}
