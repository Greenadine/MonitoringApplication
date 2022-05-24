package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.util.Colors;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class ConfigurationDataPanel extends ApplicationPanel {

    private JLabel availabilityValueLabel;

    public ConfigurationDataPanel(@NotNull ApplicationScreen parentScreen) {
        super(parentScreen);

        this.setLayout(new GridLayout(1, 2));

        // Populate panel
        addPercentageContent();
        addCostsContent();
    }

    private void addPercentageContent() {
        JPanel percentagePanel = new JPanel();

        percentagePanel.setBackground(Colors.MAIN_BACKGROUND);
        this.add(percentagePanel);

        availabilityValueLabel = new JLabel("Availability with this design: 0%");
        percentagePanel.add(availabilityValueLabel);
    }

    private void addCostsContent() {
        JPanel costsPanel = new JPanel();
        costsPanel.setBackground(Colors.MAIN_BACKGROUND);

        this.add(costsPanel);

        JLabel costsLabel = new JLabel("Total costs: \u20AC0,00,-");
        costsPanel.add(costsLabel);
    }

    public void setAvailabilityValue(final double availability) {
        availabilityValueLabel.setText(String.format(""));
    }


    @Override
    public void onShowImpl() {

    }

    @Override
    public void onHideImpl() {

    }
}
