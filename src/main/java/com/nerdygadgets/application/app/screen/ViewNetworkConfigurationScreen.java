package com.nerdygadgets.application.app.screen;

import com.nerdygadgets.application.app.ApplicationFrame;
import com.nerdygadgets.application.model.NetworkConfiguration;
import org.jetbrains.annotations.NotNull;

public class ViewNetworkConfigurationScreen extends AbstractApplicationScreen {

    private final NetworkConfiguration networkConfiguration;

    public ViewNetworkConfigurationScreen(@NotNull final ApplicationFrame applicationFrame, @NotNull NetworkConfiguration networkConfiguration) {
        super(applicationFrame);
        this.networkConfiguration = networkConfiguration;

        //
    }

    public void preOpen() {
        // Nothing
    }
}
