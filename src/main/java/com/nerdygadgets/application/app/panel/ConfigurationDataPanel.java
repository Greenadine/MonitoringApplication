package com.nerdygadgets.application.app.panel;

import com.nerdygadgets.application.app.model.ApplicationPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.util.Colors;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class ConfigurationDataPanel extends ApplicationPanel {

    private JLabel availabilityValueLabel;
    private JLabel costsValueLabel;

    public ConfigurationDataPanel(@NotNull ApplicationScreen parentScreen) {
        super(parentScreen);

        this.setLayout(new GridLayout(1, 2));

        // Populate panel
        addPercentageContent();
        addCostsContent();
    }

    private void addPercentageContent() {
        JPanel percentagePanel = new JPanel();
        percentagePanel.setBorder(new MatteBorder(0,2,0,2,Colors.MAIN_BACKGROUND_ACCENT));

        percentagePanel.setBackground(Colors.MAIN_BACKGROUND);
        this.add(percentagePanel);

        availabilityValueLabel = new JLabel("Availability with this design: 0%");
        percentagePanel.add(availabilityValueLabel);
    }

    private void addCostsContent() {
        JPanel costsPanel = new JPanel();
        costsPanel.setBackground(Colors.MAIN_BACKGROUND);
        costsPanel.setBorder(new MatteBorder(0,2,0,2,Colors.MAIN_BACKGROUND_ACCENT));


        this.add(costsPanel);

        costsValueLabel = new JLabel("Total costs: \u20AC0,00,-");
        costsPanel.add(costsValueLabel);
    }

    public void setAvailabilityValue(final double availability) {
        if (availability == -1) {
            availabilityValueLabel.setText("Availability with this design: 0%");
        } else {
            availabilityValueLabel.setText("Availability with this design: " + String.format("%.2f%%", availability * 100));
        }
    }

    public void setPriceValue(final double price) {
        if (price == -1) {
            costsValueLabel.setText("Total costs: \u20AC0,00,-");
        } else {
            costsValueLabel.setText("Total costs: " + String.format("\u20AC%.2f", price));
        }
    }


    @Override
    public void onShowImpl() {

    }

    @Override
    public void onHideImpl() {

    }
}
