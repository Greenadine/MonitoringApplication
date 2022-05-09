package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.component.WrappedJLabel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.model.ApplicationWindow;
import com.nerdygadgets.application.app.panel.SettingsPanel;
import com.nerdygadgets.application.util.Fonts;
import com.nerdygadgets.application.util.SwingUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SettingsScreen extends ApplicationScreen {

    public SettingsScreen(@NotNull ApplicationWindow window) {
        super(window);

        // Configure screen
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(10, 25, 25, 25));

        // Populate screen
        final WrappedJLabel titleLabel = new WrappedJLabel("Settings");
        titleLabel.setFont(Fonts.MAIN_TITLE);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.add(titleLabel.getWrapperPanel());
        SwingUtils.addVerticalSpacer(this, 15);

        this.add(new SettingsPanel(this)); // Add settings panel
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
