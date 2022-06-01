package com.nerdygadgets.application.app.panel.networkcomponents;

import com.nerdygadgets.application.app.component.WrappedJButton;
import com.nerdygadgets.application.app.component.WrappedJLabel;
import com.nerdygadgets.application.app.dialog.EditNetworkComponentDialog;
import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.model.NetworkComponent;
import com.nerdygadgets.application.util.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Locale;

public class NetworkComponentDetailsPanel extends ApplicationPanel implements ActionListener {

    private NetworkComponent component;

    private final JLabel componentName;
    private final JLabel componentId;
    private final JLabel componentAvailability;
    private final JLabel componentPrice;
    private final JLabel componentIp;
    private final JLabel componentSubnetMask;

    private final WrappedJButton deleteComponentButton;
    private final WrappedJButton editComponentButton;

    public NetworkComponentDetailsPanel(@NotNull ApplicationScreen parentScreen) {
        super(parentScreen);

        // Configure panel
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(300, 175));
        this.setBorder(new MatteBorder(5, 5, 5, 5, Colors.MAIN_BACKGROUND_ACCENT)); // Create spacing within the panel

        // Create and add component name header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Colors.MAIN_BACKGROUND);
        headerPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        this.add(headerPanel, BorderLayout.PAGE_START);

        JPanel componentNamePanel = new JPanel();
        componentNamePanel.setLayout(new BorderLayout());
        componentNamePanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        componentNamePanel.setBackground(Colors.MAIN_BACKGROUND);
        headerPanel.add(componentNamePanel);

        this.componentName = new JLabel("(No component selected)", SwingConstants.CENTER);
        componentName.setFont(Fonts.MAIN_SIDEBAR_HEADER);
        componentNamePanel.add(this.componentName, BorderLayout.CENTER);

        // Add ID panel under header
        JPanel idPanel = new JPanel();
        idPanel.setLayout(new BorderLayout());
        idPanel.setBackground(Colors.MAIN_BACKGROUND_ACCENT);
        headerPanel.add(idPanel);

        componentId = new JLabel("ID: -", SwingConstants.CENTER);
        componentId.setBorder(new EmptyBorder(2, 0, 2, 0));
        idPanel.add(componentId, BorderLayout.CENTER);

        // Create details grid
        JPanel detailsPanel = new JPanel();
        detailsPanel.setBorder(new MatteBorder(2, 2, 2, 2, Colors.MAIN_BACKGROUND));
        detailsPanel.setLayout(new GridLayout(4, 2));
        this.add(detailsPanel, BorderLayout.CENTER);

        // Add availability
        WrappedJLabel availabilityLabel = new WrappedJLabel("Availability", SwingConstants.CENTER);
        availabilityLabel.getLabel().setFont(Fonts.MAIN_SIDEBAR_PARAGRAPH);
        availabilityLabel.setBackground(Colors.MAIN_BACKGROUND_ACCENT);
        availabilityLabel.setBorder(new MatteBorder(0, 0, 2, 2, Colors.MAIN_BACKGROUND));
        detailsPanel.add(availabilityLabel);

        componentAvailability = new JLabel("", SwingConstants.CENTER);
        componentAvailability.setFont(Fonts.MAIN_SIDEBAR_PARAGRAPH);
        componentAvailability.setBorder(new MatteBorder(0, 0, 2, 0, Colors.MAIN_BACKGROUND));
        detailsPanel.add(componentAvailability);

        // Add price
        WrappedJLabel priceLabel = new WrappedJLabel("Price", SwingConstants.CENTER);
        priceLabel.getLabel().setFont(Fonts.MAIN_SIDEBAR_PARAGRAPH);
        priceLabel.setBackground(Colors.MAIN_BACKGROUND_ACCENT);
        priceLabel.setBorder(new MatteBorder(0, 0, 2, 2, Colors.MAIN_BACKGROUND));
        detailsPanel.add(priceLabel);

        componentPrice = new JLabel("", SwingConstants.CENTER);
        componentPrice.setFont(Fonts.MAIN_SIDEBAR_PARAGRAPH);
        componentPrice.setBorder(new MatteBorder(0, 0, 2, 0, Colors.MAIN_BACKGROUND));
        detailsPanel.add(componentPrice);

        // Add IP
        WrappedJLabel ipLabel = new WrappedJLabel("IP", SwingConstants.CENTER);
        ipLabel.getLabel().setFont(Fonts.MAIN_SIDEBAR_PARAGRAPH);
        ipLabel.setBackground(Colors.MAIN_BACKGROUND_ACCENT);
        ipLabel.setBorder(new MatteBorder(0, 0, 2, 2, Colors.MAIN_BACKGROUND));
        detailsPanel.add(ipLabel);

        componentIp = new JLabel("", SwingConstants.CENTER);
        componentIp.setFont(Fonts.MAIN_SIDEBAR_PARAGRAPH);
        componentIp.setBorder(new MatteBorder(0, 0, 2, 0, Colors.MAIN_BACKGROUND));
        detailsPanel.add(componentIp);

        // Add subnet mask
        WrappedJLabel subnetMaskLabel = new WrappedJLabel("Subnet Mask", SwingConstants.CENTER);
        subnetMaskLabel.getLabel().setFont(Fonts.MAIN_SIDEBAR_PARAGRAPH);
        subnetMaskLabel.setBackground(Colors.MAIN_BACKGROUND_ACCENT);
        subnetMaskLabel.setBorder(new MatteBorder(0, 0, 0, 2, Colors.MAIN_BACKGROUND));
        detailsPanel.add(subnetMaskLabel);

        componentSubnetMask = new JLabel("", SwingConstants.CENTER);
        componentSubnetMask.setFont(Fonts.MAIN_SIDEBAR_PARAGRAPH);
        componentSubnetMask.setBorder(new MatteBorder(0, 0, 0, 0, Colors.MAIN_BACKGROUND));
        detailsPanel.add(componentSubnetMask);

        /* Add delete and edit buttons */
        JPanel buttonsWrapperPanel = new JPanel();
        buttonsWrapperPanel.setLayout(new BoxLayout(buttonsWrapperPanel, BoxLayout.X_AXIS));
        this.add(buttonsWrapperPanel, BorderLayout.PAGE_END);

        // Add delete component button
        deleteComponentButton = new WrappedJButton("Delete", SwingUtils.getIconFromResource("delete.png"));
        deleteComponentButton.getButton().addActionListener(this::actionDeleteComponent);
        deleteComponentButton.getButton().setEnabled(false);
        deleteComponentButton.getButton().setBorder(new EmptyBorder(10, 20, 10, 20));
        deleteComponentButton.setBorder(new MatteBorder(2, 2, 2, 2, Colors.MAIN_BACKGROUND_ACCENT));
        buttonsWrapperPanel.add(deleteComponentButton);

        // Add edit component button
        editComponentButton = new WrappedJButton("Edit", SwingUtils.getIconFromResource("edit.png"));
        editComponentButton.getButton().addActionListener(this::actionEditComponent);
        editComponentButton.getButton().setBorder(new EmptyBorder(10, 20, 10, 25));
        editComponentButton.getButton().setEnabled(false);
        editComponentButton.setBorder(new MatteBorder(2, 2, 2, 2, Colors.MAIN_BACKGROUND_ACCENT));
        buttonsWrapperPanel.add(editComponentButton);
    }

    public void displayComponent(@NotNull final NetworkComponent component) {
        this.component = component;

        // Set values to display component details
        componentName.setText(component.getName());
        componentId.setText("ID: " + component.getId());
        componentAvailability.setText(String.format(Locale.UK, "%.2f%%", component.getAvailability()));
        componentPrice.setText(String.format(Locale.UK, "\u20AC%.2f", component.getPrice()));
        componentIp.setText(component.getIp());
        componentSubnetMask.setText(component.getSubnetMask());

        // Enable delete and edit buttons
        deleteComponentButton.getButton().setEnabled(true);
        editComponentButton.getButton().setEnabled(true);
    }

    /* Button actions */

    private void actionDeleteComponent(ActionEvent event) {
        DatabaseUtils.deleteComponent(component);
        parentScreen.onClose();
        parentScreen.onOpen();
    }

    private void actionEditComponent(ActionEvent event) {
        EditNetworkComponentDialog editNetworkComponentDialog = new EditNetworkComponentDialog(true, component.getName(), component.getPrice(), component.getAvailability(), component.getIp(), component.getSubnetMask());
        NetworkComponent dialogComponent = editNetworkComponentDialog.getComponent();

        if (dialogComponent != null) {
            DatabaseUtils.updateComponent(dialogComponent);

            parentScreen.onClose();
            parentScreen.onOpen();
        }
    }

    @Override
    public void onShowImpl() {
        // Do nothing
    }

    @Override
    public void onHideImpl() {
        // Reset values
        componentName.setText("(No component selected)");
        componentId.setText("ID: -");
        componentAvailability.setText("");
        componentPrice.setText("");
        componentIp.setText("");
        componentSubnetMask.setText("");

        // Disable delete and edit buttons
        deleteComponentButton.getButton().setEnabled(false);
        editComponentButton.getButton().setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
