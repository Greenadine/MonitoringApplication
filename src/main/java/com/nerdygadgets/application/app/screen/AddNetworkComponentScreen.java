package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.panel.ScreenHeaderPanel;
import com.nerdygadgets.application.app.model.ApplicationScreen;
import com.nerdygadgets.application.app.window.MainWindow;
import com.nerdygadgets.application.util.ApplicationActions;
import org.jetbrains.annotations.NotNull;

import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddNetworkComponentScreen extends ApplicationScreen {

    public AddNetworkComponentScreen(@NotNull final MainWindow window) {
        super(window);

        // Configure screen
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Add panels
        this.add(new ScreenHeaderPanel(this, "Add New Component", 500, 50, ApplicationActions::openHome), BorderLayout.PAGE_START);

        // TODO
    }

    @Override
    protected void onOpenImpl() {
        // Do nothing
    }

    @Override
    protected void onCloseImpl() {
        // Do nothing
    }
}
